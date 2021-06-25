package com.google.mlkit.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.google.mlkit.module.apiModule
import com.google.mlkit.module.repositoryModule
import com.google.mlkit.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level


class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MyApplication)
            modules(listOf(apiModule, repositoryModule,viewModelModule))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}