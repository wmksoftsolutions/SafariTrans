package com.google.mlkit.module

import com.google.mlkit.home.HomeRepository
import com.google.mlkit.login.LoginRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        LoginRepository(get())
    }

    single {
        HomeRepository(get())
    }
}