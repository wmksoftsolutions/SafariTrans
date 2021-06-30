package com.google.mlkit.utils

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.mlkit.application.MyApplication
import com.google.mlkit.dialog.CustomProgressDialog
import com.google.mlkit.laguage.LanguageModel
import com.google.mlkit.vision.demo.R
import retrofit2.HttpException
import java.net.SocketException
import java.net.UnknownHostException
import java.util.*
import java.net.SocketTimeoutException as SocketTimeoutException1

class CommonMethods {
    companion object {
        var customProgressDialog = CustomProgressDialog()
        fun showToast(applicationContext: Context, msg: String) {
            var toast: Toast? = null
            if (toast != null)
                toast.cancel()
            toast = Toast.makeText(applicationContext, msg, Toast.LENGTH_SHORT)
            toast.show()
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
            when (e) {
                is java.net.SocketTimeoutException -> {
                    result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }
                is SocketException -> {
                    result.msg =
                        MyApplication.applicationContext().getString(R.string.check_interent)
                }
                is HttpException -> {
                    result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }
                is UnknownHostException -> {
                    result.msg = MyApplication.applicationContext().getString(R.string.server_error)
                }

            }

            myResponse.value = result

        }

        fun showLog(tag: String, message: String) {
            Log.e(tag, message)
        }

        fun saveUserLogin(context: Context, b: Boolean) {
            val pref: SharedPreferences = getSharedPref(context)
            pref.edit().putBoolean(Constants.isLogin, true).apply()
        }

        fun checkUserLogin(context: Context): Boolean {
            return getSharedPref(context).getBoolean(Constants.isLogin, false)
        }

        private fun getSharedPref(context: Context): SharedPreferences {
            return context.getSharedPreferences(Constants.PREFRENCE_NAME, Context.MODE_PRIVATE)
        }


        fun changeLanguage(context: Context, language: LanguageModel) {
            val locale = Locale(language.language)
            Locale.setDefault(locale)
            val resources: Resources = context.getResources()
            val config = resources.configuration
            config.locale = locale
            resources.updateConfiguration(config, resources.displayMetrics)
        }


        /*
      * this method is used to save the language set by user
      * */
        fun saveLanguageInPref(context: Context, language: LanguageModel) {
            val editor = getSharedPref(context).edit()
            editor.putString(Constants.LANGUAGE, language.language)
            editor.putString(Constants.LANGUAGE_NAME, language.languageName)
            editor.apply()
        }

        /*
        * this method is used to get  language set by user
        * */
        fun getLanguageFromPref(context: Context): String? {
            return getSharedPref(context).getString(Constants.LANGUAGE, "")
        }

        /*
        * this method is used to get  language set by user
        * */
        fun getLanguageNameFromPref(context: Context): String? {
            return getSharedPref(context).getString(Constants.LANGUAGE_NAME, "")
        }

        fun clearSharedPref(context: Context) {
            getSharedPref(context).edit().clear().apply()
        }


    }
}