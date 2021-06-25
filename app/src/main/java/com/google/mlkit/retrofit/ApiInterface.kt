package com.google.mlkit.retrofit

import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.login.LoginRequest
import com.google.mlkit.login.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {

    @POST("ajax.php?action=login_api")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("ajax.php?action=get_track_status")
    fun getTrackStatus(@Body shipment_id: Int): Call<TrackStatusResponse>

}