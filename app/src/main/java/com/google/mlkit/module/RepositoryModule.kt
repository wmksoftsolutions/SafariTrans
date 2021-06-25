package com.google.mlkit.module

import com.google.mlkit.home.HomeRepository
import com.google.mlkit.login.LoginRepository
import com.google.mlkit.showstatus.UpdateStatusRepository
import org.koin.dsl.module

val repositoryModule = module {

    single {
        LoginRepository(get())
    }

    single {
        HomeRepository(get())
    }
    single {
        UpdateStatusRepository(get())
    }
}