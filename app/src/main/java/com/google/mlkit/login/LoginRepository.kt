package com.google.mlkit.login

import android.security.identity.ResultData
import android.util.Log
import com.google.mlkit.retrofit.ApiInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginRepository(private val apiInterface: ApiInterface) {

    fun login(loginRequest: LoginRequest) {
        apiInterface.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("================", "===============" + t.message!!)
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.e("================", "===============" + response)
            }
        })
    }

   /* suspend fun login(loginRequest: LoginRequest):ResultData =
        withContext(Dispatchers.IO){
            return@withContext safeApiCall(
                call={
                    apiInterface.login(loginRequest=loginRequest)
                }
            )
    }*/
}