package com.hearoad.hearoad.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hearoad.hearoad.data.model.response.AuthResponse
import com.hearoad.hearoad.data.network.NetworkResult
import com.hearoad.hearoad.data.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _loginResult = MutableLiveData<NetworkResult<AuthResponse>>()
    val loginResult: LiveData<NetworkResult<AuthResponse>> = _loginResult

    fun loginWithKakao(authorizationCode: String) {
        viewModelScope.launch {
            _loginResult.value = NetworkResult.Loading()
            val result = authRepository.loginWithKakao(authorizationCode)
            _loginResult.value = result
        }
    }
}