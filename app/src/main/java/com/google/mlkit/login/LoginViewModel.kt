package com.google.mlkit.login

import androidx.lifecycle.ViewModel

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    fun login(loginRequest: LoginRequest) {
        loginRepository.login(loginRequest)
    }
}