package com.google.mlkit.home

import com.google.mlkit.retrofit.ApiInterface

class HomeRepository(private val apiInterface: ApiInterface) {
    suspend fun trackStatus(trackStatus: HomeRequest) : TrackStatusResponse = apiInterface.getTrackStatus(trackStatus)
}