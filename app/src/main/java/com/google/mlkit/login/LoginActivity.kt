package com.google.mlkit.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.mlkit.home.HomeActivity
import com.google.mlkit.utils.CommonMethods
import com.google.mlkit.utils.ResultStatus
import com.google.mlkit.vision.demo.R
import com.google.mlkit.vision.demo.databinding.ActivityLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val loginViewModel: LoginViewModel by viewModel<LoginViewModel>()
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            val username = binding.etUsername.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            when {
                username.isEmpty() -> CommonMethods.showToast(
                    applicationContext,
                    getString(R.string.username_cant_empty)
                )
                password.isEmpty() -> CommonMethods.showToast(
                    applicationContext,
                    getString(R.string.password_cant_empty)
                )
                password.length < 6 -> CommonMethods.showToast(
                    applicationContext,
                    getString(R.string.password_musthave_min)
                )
                else -> {
                    val loginRequest = LoginRequest(username, password)
                    if (CommonMethods.isNetworkAvailable(this)) {
                        loginViewModel.login(loginRequest)
                        loginViewModel.myResponse.observe(this, {
                            when (it.status) {
                                ResultStatus.LOADING.ordinal -> {
                                    CommonMethods.showLoader(this)
                                }
                                ResultStatus.ERROR.ordinal -> {
                                    CommonMethods.hideLoader()
                                    CommonMethods.showToast(applicationContext, it.msg)
                                }
                                ResultStatus.SUCCESS.ordinal -> {
                                    CommonMethods.hideLoader()
                                    CommonMethods.showToast(applicationContext, it.msg)
                                    startActivity(Intent(this, HomeActivity::class.java))
                                    finish()
                                }
                            }


                        })
                    } else
                        CommonMethods.showToast(
                            applicationContext,
                            getString(R.string.check_interent)
                        )
                }
            }

        }

    }
}