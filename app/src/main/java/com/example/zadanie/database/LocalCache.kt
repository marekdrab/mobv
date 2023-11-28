package com.example.zadanie.database

import androidx.lifecycle.LiveData
import com.example.zadanie.database.entities.GeofenceEntity
import com.example.zadanie.database.entities.UserEntity

class LocalCache(private val dao: DbDao) {

    suspend fun logoutUser() {
        deleteUserItems()
    }

    suspend fun insertUserItems(items: List<UserEntity>) {
        dao.deleteUserItems()
        if (items.isNotEmpty()) {
            dao.insertUserItems(items)
        }
    }

    fun getUserItem(uid: String): LiveData<UserEntity?> {
        return dao.getUserItem(uid)
    }

    fun getUsers(): LiveData<List<UserEntity>?> = dao.getUsers()

    suspend fun deleteUserItems() {
        dao.deleteUserItems()
    }

    suspend fun insertGeofence(item: GeofenceEntity) {
        dao.insertGeofence(item)
    }
}
