package com.google.mlkit.retrofit

import com.google.mlkit.home.HomeRequest
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.login.LoginRequest
import com.google.mlkit.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("ajax.php?action=login_api")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("ajax.php?action=get_track_status")
    suspend fun getTrackStatus(@Body homeRequest: HomeRequest): TrackStatusResponse

}