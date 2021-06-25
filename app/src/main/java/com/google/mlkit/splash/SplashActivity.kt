package com.google.mlkit.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.mlkit.home.HomeActivity
import com.google.mlkit.login.LoginActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.vision.demo.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Handler(Looper.getMainLooper()).postDelayed({
            if (CommonMethods.checkUserLogin(this@SplashActivity))
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
            else
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 3000)
    }
}