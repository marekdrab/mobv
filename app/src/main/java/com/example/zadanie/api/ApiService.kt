package com.example.zadanie.api

import android.content.Context
import com.example.zadanie.api.model.GeofenceListResponse
import com.example.zadanie.api.model.GeofenceUpdateRequest
import com.example.zadanie.api.model.GeofenceUpdateResponse
import com.example.zadanie.api.model.LoginResponse
import com.example.zadanie.api.model.PasswordChangeRequest
import com.example.zadanie.api.model.PasswordChangeResponse
import com.example.zadanie.api.model.PasswordResetRequest
import com.example.zadanie.api.model.PasswordResetResponse
import com.example.zadanie.api.model.RefreshTokenRequest
import com.example.zadanie.api.model.RefreshTokenResponse
import com.example.zadanie.api.model.RegistrationResponse
import com.example.zadanie.api.model.UserLogin
import com.example.zadanie.api.model.UserRegistration
import com.example.zadanie.api.model.UserResponse
import com.example.zadanie.config.Config
import com.example.zadanie.helpers.AuthInterceptor
import com.example.zadanie.helpers.TokenAuthenticator
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("user/create.php")
    suspend fun registerUser(@Body userInfo: UserRegistration): Response<RegistrationResponse>

    @POST("user/login.php")
    suspend fun loginUser(@Body userInfo: UserLogin): Response<LoginResponse>

    @GET("user/get.php")
    suspend fun getUser(
        @Query("id") id: String
    ): Response<UserResponse>

    @POST("user/reset.php")
    suspend fun resetPassword(
        @Body passwordResetRequest: PasswordResetRequest
    ): Response<PasswordResetResponse>

    @POST("user/password.php")
    suspend fun changePassword(
        @Body passwordResetRequest: PasswordChangeRequest
    ): Response<PasswordChangeResponse>

    @POST("user/refresh.php")
    suspend fun refreshToken(
        @Body refreshInfo: RefreshTokenRequest
    ): Response<RefreshTokenResponse>

    @POST("user/refresh.php")
    fun refreshTokenBlocking(
        @Body refreshInfo: RefreshTokenRequest
    ): Call<RefreshTokenResponse>
    @GET("geofence/list.php")
    suspend fun listGeofence(): Response<GeofenceListResponse>

    @POST("geofence/update.php")
    suspend fun updateGeofence(@Body body: GeofenceUpdateRequest): Response<GeofenceUpdateResponse>

    @DELETE("geofence/update.php")
    suspend fun deleteGeofence(): Response<GeofenceUpdateResponse>


    companion object{
        fun create(context: Context): ApiService {

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor(context))
                .authenticator(TokenAuthenticator(context))
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://zadanie.mpage.sk/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
