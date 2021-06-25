package com.google.mlkit.login

data class LoginResponse(
    val data: List<Any>?,
    val message: String,
    val status: Boolean
)