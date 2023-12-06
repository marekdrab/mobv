package com.example.zadanie.api.model

data class UserRegistration(val name: String, val email: String, val password: String)
data class UserLogin(val name: String, val password: String)
data class PasswordChangeRequest(val old_password: String, val new_password: String)
data class PasswordResetRequest(val email: String)
data class RefreshTokenRequest(val refresh: String)
data class GeofenceUpdateRequest(val lat: Double, val lon: Double, val radius: Double)