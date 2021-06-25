package com.google.mlkit.login

data class LoginRequest(var email: String, var password: String) {
    constructor() : this("", "")
}