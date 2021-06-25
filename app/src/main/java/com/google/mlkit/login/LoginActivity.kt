package com.google.mlkit.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.google.mlkit.home.HomeActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.google.mlkit.login.LoginRequest as LoginRequest

class LoginActivity : AppCompatActivity() {
    val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener({
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            if (username.isEmpty())
                CommonMethods.showToast(applicationContext, getString(R.string.username_cant_empty))
            else if (password.isEmpty())
                CommonMethods.showToast(applicationContext, getString(R.string.password_cant_empty))
            else if (password.length < 6)
                CommonMethods.showToast(
                    applicationContext,
                    getString(R.string.password_musthave_min)
                )
            else {
//                val loginRequest = LoginRequest(username, password)
//                loginViewModel.login(loginRequest)
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }

            /*     CoroutineScope(Dispatchers.IO)
                     .launch {
                         loginViewModel.login(loginRequest)
                     }*/
        })

    }
}