package com.google.mlkit.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Result
import com.google.mlkit.utils.ResultStatus
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(private val homeRepository: HomeRepository) : ViewModel() {
     val trackStatusResponse:MutableLiveData<Result> = MutableLiveData()

    fun trackStatus(trackStatus: HomeRequest){
        val result = Result(ResultStatus.LOADING.ordinal,null,"")
        trackStatusResponse.value = result

        viewModelScope.launch {
            try {
                val response = homeRepository.trackStatus(trackStatus)
                result.status =
                    if (response.status) ResultStatus.SUCCESS.ordinal else ResultStatus.ERROR.ordinal
                result.data = response
                result.msg = response.message
                trackStatusResponse.value = result
            }catch (e: Exception){
                CommonMethods.errorHandler(e,result,trackStatusResponse)
            }
        }

    }
}