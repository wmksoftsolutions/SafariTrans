package com.google.mlkit.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.Result
import com.google.mlkit.utils.ResultStatus
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val myResponse: MutableLiveData<Result> = MutableLiveData()
    fun login(loginRequest: LoginRequest) {

        val result = Result(ResultStatus.LOADING.ordinal, null, "")
        myResponse.value = result
        viewModelScope.launch {
            try {
                val response = loginRepository.login(loginRequest)
                result.status =
                    if (response.status) ResultStatus.SUCCESS.ordinal else ResultStatus.ERROR.ordinal
                result.data = response
                result.msg = response.message
                myResponse.value = result

            } catch (e: Exception) {
                CommonMethods.errorHandler(e,result,myResponse)
            }
        }
    }
}