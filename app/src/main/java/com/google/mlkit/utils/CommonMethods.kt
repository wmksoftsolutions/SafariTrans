package com.google.mlkit.utils

import android.content.Context
import android.widget.Toast

class CommonMethods {
    companion object{
        fun showToast(applicationContext: Context, msg: String) {
        Toast.makeText(applicationContext,msg,Toast.LENGTH_SHORT).show()
        }

    }
}