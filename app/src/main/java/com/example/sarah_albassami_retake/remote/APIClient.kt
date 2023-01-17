package com.example.sarah_albassami_retake.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    private var retrofit: Retrofit? = null

    fun getClient(): Retrofit?{

        // New Code
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("https://www.freetogame.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient) // New Code
            .build()
        return retrofit
    }
}