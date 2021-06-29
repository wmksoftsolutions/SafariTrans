package com.google.mlkit.laguage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.mlkit.login.LoginActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivitySelectLanguageBinding
import org.koin.android.ext.android.bind

class SelectLanguageActivity : AppCompatActivity() {
    lateinit var binding: ActivitySelectLanguageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_language)
        val engLanguage = LanguageModel("en", "English")
        val frenchLanguage = LanguageModel("fr", "French")
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
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}