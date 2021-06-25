package com.google.mlkit.showstatus

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Result
import com.google.mlkit.utils.ResultStatus
import kotlinx.coroutines.launch
import javax.inject.Inject

class UpdateStatusViewModel @Inject constructor(private val updateStatusViewModelRepository: UpdateStatusRepository) :
    ViewModel() {
    var statusResponse: MutableLiveData<Result> = MutableLiveData()

    fun updateStatus(updateStatusRequest: UpdateStatusRequest) {
        val result = Result(ResultStatus.LOADING.ordinal, null, "")
        statusResponse.value = result
        viewModelScope.launch {
            try {
                val response = updateStatusViewModelRepository.updateStatus(updateStatusRequest)
                result.status =
                    if (response.status) ResultStatus.SUCCESS.ordinal else ResultStatus.ERROR.ordinal
                result.data = response
                result.msg = response.message
                statusResponse.value = result
            } catch (e: Exception) {
                CommonMethods.errorHandler(e, result, statusResponse)
            }
        }
    }
}