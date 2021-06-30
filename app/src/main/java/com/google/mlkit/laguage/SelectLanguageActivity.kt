package com.google.mlkit.laguage

import android.app.Activity
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.mlkit.home.HomeActivity
import com.google.mlkit.login.LoginActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivitySelectLanguageBinding
import org.koin.android.ext.android.bind

class SelectLanguageActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectLanguageBinding
    var isFromSplash = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        val engLanguage = LanguageModel("en", "English")
        val frenchLanguage = LanguageModel("fr", "French")
        if (intent != null && intent.hasExtra("isFromSplash")) {
            isFromSplash = intent.getBooleanExtra("isFromSplash", true)
        }
        if (isFromSplash)
            title = getString(R.string.select_language
            )
        else
            title = getString(R.string.change_language)
        binding.btnEnglisgh.setOnClickListener {
            changeLanguage(engLanguage)
        }
        binding.btnFrench.setOnClickListener {
            changeLanguage(frenchLanguage)
        }

    }

    private fun changeLanguage(languageModel: LanguageModel) {
        CommonMethods.changeLanguage(this, languageModel)
        CommonMethods.saveLanguageInPref(this, languageModel)
        if (isFromSplash) {
            startActivity(Intent(this, LoginActivity::class.java))
        } else
            startActivity(Intent(this, HomeActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP))
        finish()
    }
}