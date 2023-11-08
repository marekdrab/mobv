package com.example.zadanie.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.zadanie.api.DataRepository
import com.example.zadanie.model.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val dataRepository: DataRepository) : ViewModel() {
    private val _profileResult = MutableLiveData<String>()
    val profileResult: LiveData<String> get() = _profileResult

    private val _userResult = MutableLiveData<User?>()
    val userResult: LiveData<User?> get() = _userResult

    fun loadUser(uid: String, my_uid: String, access: String, refresh: String) {
        viewModelScope.launch {
            val result = dataRepository.apiGetUser(
                uid, my_uid, access, refresh
            )
            _profileResult.postValue(result.first ?: "")
            _userResult.postValue(result.second)
        }
    }
}