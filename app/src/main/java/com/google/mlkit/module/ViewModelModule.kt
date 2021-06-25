package com.google.mlkit.module

import com.google.mlkit.home.HomeViewModel
import com.google.mlkit.login.LoginViewModel
import org.koin.dsl.module

var viewModelModule = module {

    single {
        LoginViewModel(get())
    }
    single { HomeViewModel(get()) }
}