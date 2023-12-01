package com.example.zadanie.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zadanie.api.DataRepository
import com.example.zadanie.model.User
import kotlinx.coroutines.launch

class AuthViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _registrationResult = MutableLiveData<String>()
    val registrationResult: LiveData<String> get() = _registrationResult

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String> get() = _loginResult

    private val _userResult = MutableLiveData<User?>()
    val userResult: LiveData<User?> get() = _userResult

    val username = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val repeat_password = MutableLiveData<String>()

    fun registerUser() {
        viewModelScope.launch {
            val result = dataRepository.apiRegisterUser(
                username.value ?: "",
                email.value ?: "",
                password.value ?: ""
            )
            _registrationResult.postValue(result.first ?: "")
            _userResult.postValue(result.second)
        }
    }

    fun loginUser() {
        viewModelScope.launch {
            val result = dataRepository.apiLoginUser(username.value ?: "", password.value ?: "")
            _loginResult.postValue(result.first ?: "")
            _userResult.postValue(result.second)
        }
    }

    fun logoutUser() {
        _userResult.postValue(null)
        password.postValue("")
        username.postValue("")
    }

    private val _changePasswordResult = MutableLiveData<String>()
    val changePasswordResult: LiveData<String> get() = _changePasswordResult

    private val _changePasswordResultMessage = MutableLiveData<String?>()
    val changePasswordResultMessage: LiveData<String?> get() = _changePasswordResultMessage

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch {
            val result = dataRepository.apiChangePassword(oldPassword, newPassword)
            _changePasswordResult.postValue(result.first.toString())
            _changePasswordResultMessage.postValue(result.second)
        }
    }
}