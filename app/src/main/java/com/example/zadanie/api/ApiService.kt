package com.example.zadanie.api

import com.example.zadanie.api.model.LoginResponse
import com.example.zadanie.api.model.RegistrationResponse
import com.example.zadanie.api.model.UserLogin
import com.example.zadanie.api.model.UserRegistration
import com.example.zadanie.config.Config
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiService {
    @Headers("x-apikey: ${Config.API_KEY}")
    @POST("user/create.php")
    suspend fun registerUser(@Body userInfo: UserRegistration): Response<RegistrationResponse>

    @Headers("x-apikey: ${Config.API_KEY}")
    @POST("user/login.php")
    suspend fun loginUser(@Body userInfo: UserLogin): Response<LoginResponse>

    companion object{
        fun create(): ApiService {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://zadanie.mpage.sk/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}
