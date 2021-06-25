package com.google.mlkit.login

import com.google.mlkit.retrofit.ApiInterface

class LoginRepository(private val apiInterface: ApiInterface) {

    suspend fun login(loginRequest: LoginRequest): LoginResponse = apiInterface.login(loginRequest)

}