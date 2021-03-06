package com.google.mlkit.retrofit

import com.google.mlkit.home.HomeRequest
import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.login.LoginRequest
import com.google.mlkit.login.LoginResponse
import com.google.mlkit.updatestatus.UpdateStatusRequest
import com.google.mlkit.updatestatus.UpdateWholeStatusRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @POST("ajax.php?action=login_api")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("ajax.php?action=get_track_status")
    suspend fun getTrackStatus(@Body homeRequest: HomeRequest): TrackStatusResponse

    @POST("ajax.php?action=update_track_status")
    suspend fun updateTrackStatus(@Body updateStatusRequest: UpdateStatusRequest): TrackStatusResponse


    @POST("ajax.php?action=update_multiple_track_status")
    suspend fun updateWholeContainerStatus(@Body updateStatusRequest: UpdateWholeStatusRequest): TrackStatusResponse

}