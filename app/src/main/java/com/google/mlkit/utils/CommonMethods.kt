package com.google.mlkit.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.application.MyApplication
import com.google.mlkit.dialog.CustomProgressDialog
import com.google.mlkit.vision.demo.R
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException
import java.net.SocketTimeoutException as SocketTimeoutException1

class CommonMethods {
    companion object{
        var customProgressDialog = CustomProgressDialog()
        fun showToast(applicationContext: Context, msg: String) {
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
        }

        fun isNetworkAvailable(context: Context): Boolean {
            var isConnected = false
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager.activeNetwork ?: return false
                val activeNetwork =
                    connectivityManager.getNetworkCapabilities(networkCapabilities)
                        ?: return false
                isConnected = when {
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } else {
                connectivityManager.run {
                    activeNetworkInfo?.run {
                        isConnected = when (type) {
                            ConnectivityManager.TYPE_WIFI -> true
                            ConnectivityManager.TYPE_MOBILE -> true
                            ConnectivityManager.TYPE_ETHERNET -> true
                            else -> false
                        }
                    }
                }
            }
            return isConnected
        }

        fun showLoader(context: Context) {
            customProgressDialog.show(context)
        }

        fun hideLoader() {
            customProgressDialog.dismiss()
        }

        fun errorHandler(e: Exception, result: Result, myResponse: MutableLiveData<Result>) {
            result.status = ResultStatus.ERROR.ordinal
            result.data = null
            when(e){
                is java.net.SocketTimeoutException ->{
                   result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }
                is SocketException ->{
                    result.msg = MyApplication.applicationContext().getString(R.string.check_interent)
                }
                is HttpException ->{
                    result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }
                is UnknownHostException ->{
                    result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }

            }

            myResponse.value = result

        }

        fun showLog(tag:String,message:String){
            Log.e(tag,message)
        }


    }
}