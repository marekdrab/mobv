package com.example.zadanie.api

import android.content.Context
import android.util.Log
import com.example.zadanie.api.model.UserLogin
import com.example.zadanie.api.model.UserRegistration
import com.example.zadanie.database.AppRoomDatabase
import com.example.zadanie.database.LocalCache
import com.example.zadanie.database.entities.UserEntity
import com.example.zadanie.database.entities.GeofenceEntity
import com.example.zadanie.model.User
import com.example.zadanie.api.model.GeofenceUpdateRequest
import com.example.zadanie.api.model.PasswordChangeRequest
import com.example.zadanie.api.model.PasswordResetRequest
import java.io.IOException
import java.io.StringReader

class DataRepository private constructor(
    private val service: ApiService,
    private val cache: LocalCache
) {
    companion object {
        const val TAG = "DataRepository"

        @Volatile
        private var INSTANCE: DataRepository? = null
        private val lock = Any()

        fun getInstance(context: Context): DataRepository =
            INSTANCE ?: synchronized(lock) {
                INSTANCE
                    ?: DataRepository(
                        ApiService.create(context),
                        LocalCache(AppRoomDatabase.getInstance(context).appDao())
                    ).also { INSTANCE = it }
            }
    }
    suspend fun apiListGeofence(): String {
        try {
            val response = service.listGeofence()

            if (response.isSuccessful) {
                response.body()?.list?.let { it ->
                    val users = it.map {
                        UserEntity(
                            it.uid, it.name, it.updated,
                            0.0, 0.0, it.radius, it.photo
                        )
                    }
                    cache.insertUserItems(users)
                    return ""
                }
            }

            return "Failed to load users"
        } catch (ex: IOException) {
            ex.printStackTrace()
            return "Check internet connection. Failed to load users."
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return "Fatal error. Failed to load users."
    }

    fun getUsers() = cache.getUsers()

    suspend fun getUsersList() = cache.getUsersList()

    suspend fun insertGeofence(item: GeofenceEntity) {
        cache.insertGeofence(item)
        try {
            val response =
                service.updateGeofence(GeofenceUpdateRequest(item.lat, item.lon, item.radius))

            if (response.isSuccessful) {
                response.body()?.let {

                    item.uploaded = true
                    cache.insertGeofence(item)
                    return
                }
            }

            return
        } catch (ex: IOException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun removeGeofence() {
        try {
            val response = service.deleteGeofence()

            if (response.isSuccessful) {
                response.body()?.let {
                    return
                }
            }

            return
        } catch (ex: IOException) {
            ex.printStackTrace()
            return
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    suspend fun apiRegisterUser(username: String, email: String, password: String) : Pair<String,User?>{
        if (username.isEmpty()){
            return Pair("Username can not be empty", null)
        }
        if (email.isEmpty()){
            return Pair("Email can not be empty", null)
        }
        if (password.isEmpty()){
            return Pair("Password can not be empty", null)
        }
        try {
            val response = service.registerUser(UserRegistration(username, email, password))
            if (response.isSuccessful) {
                response.body()?.let { json_response ->
                    return Pair("", User(username,email,json_response.uid, json_response.access, json_response.refresh))
                }
            }
            return Pair("Failed to create user", null)
        }catch (ex: IOException) {
            ex.printStackTrace()
            return Pair("Check internet connection. Failed to create user.", null)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair("Fatal error. Failed to create user.", null)
    }

    suspend fun apiLoginUser(username: String, password: String) : Pair<String,User?>{
        if (username.isEmpty()){
            return Pair("Username can not be empty", null)
        }
        if (password.isEmpty()){
            return Pair("Password can not be empty", null)
        }
        try {
            val response = service.loginUser(UserLogin(username, password))
            if (response.isSuccessful) {
                response.body()?.let { json_response ->
                    if (json_response.uid == "-1") {
                        return Pair("Wrong password or username.", null)
                    }
                    return Pair(
                        "",
                        User(
                            username,
                            "",
                            json_response.uid,
                            json_response.access,
                            json_response.refresh
                        )
                    )
                }
            }
            return Pair("Failed to login user", null)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Pair("Check internet connection. Failed to login user.", null)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair("Fatal error. Failed to login user.", null)
    }

    suspend fun apiGetUser(
        uid: String
    ): Pair<String, User?> {
        try {
            val response = service.getUser(uid)

            if (response.isSuccessful) {
                response.body()?.let {
                    return Pair("", User(it.name, "", it.id, "", "", it.photo))
                }
            }

            return Pair("Failed to load user", null)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Pair("Check internet connection. Failed to load user.", null)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair("Fatal error. Failed to load user.", null)
    }

    suspend fun apiChangePassword(
        oldPassword: String,
        newPassword: String
    ): Pair<Boolean, String?> {
        try {
            val response = service.changePassword(PasswordChangeRequest(oldPassword, newPassword))

            if (response.isSuccessful) {
                response.body()?.let {
                    return Pair(true, null)
                }
            }

            return Pair(false, "Failed to change password.")
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Pair(false, "Check internet connection. Failed to change password.")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair(false, "Fatal error. Failed to load user.")
    }

    suspend fun apiResetPassword(email: String): Pair<Boolean, String?> {
        try {
            val response = service.resetPassword(PasswordResetRequest(email))

            if (response.isSuccessful) {
                response.body()?.let {
                    return Pair(true, null)
                }
            }
            return Pair(false, "E-mail not found or password reset was in less than 5 minutes.")
        } catch (ex: IOException) {
            ex.printStackTrace()
            return Pair(false, "Check internet connection. Failed to change password.")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return Pair(false, "Fatal error. Failed to load user.")
    }
}

