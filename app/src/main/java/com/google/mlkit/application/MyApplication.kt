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

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}