package com.google.mlkit.updatestatus

import com.google.mlkit.home.TrackStatusResponse
import com.google.mlkit.retrofit.ApiInterface
import javax.inject.Inject

class UpdateStatusRepository @Inject constructor(val apiInterface: ApiInterface){

    suspend fun updateStatus(updateStatusRequest: UpdateStatusRequest) : TrackStatusResponse = apiInterface.updateTrackStatus(updateStatusRequest)

    suspend fun updateWholeContainerStatus(updateWholeStatusRequest: UpdateWholeStatusRequest) : TrackStatusResponse = apiInterface.updateWholeContainerStatus(updateWholeStatusRequest)
}