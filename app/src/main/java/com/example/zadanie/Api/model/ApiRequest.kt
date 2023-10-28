package com.example.zadanie.Api.model

data class UserRegistration(val name: String, val email: String, val password: String)
data class UserLogin(val name: String, val password: String)
