package com.google.mlkit.module

import com.google.mlkit.retrofit.ApiInterface
import com.google.mlkit.utils.Constants
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun provideApiInterface(retrofit: Retrofit): ApiInterface {
        return retrofit.create<ApiInterface>(ApiInterface::class.java)
    }
    single { provideOkHttp() }
    single { provideRetrofit(get()) }
    single { provideApiInterface(get()) }
}

