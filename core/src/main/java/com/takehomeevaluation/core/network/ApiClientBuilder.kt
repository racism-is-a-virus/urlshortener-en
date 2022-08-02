package com.takehomeevaluation.core.network

import com.takehomeevaluation.core.network.interceptors.LoggerInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientBuilder {

    private val baseClient = OkHttpClient.Builder().build()

    fun <T> createServiceApi(
        serviceClass: Class<T>,
        baseUrl: String,
        vararg interceptors: Interceptor
    ): T {
        val clientBuilder = baseClient.newBuilder()

        clientBuilder.addInterceptor(LoggerInterceptor())

        interceptors.forEach { clientBuilder.addInterceptor(it) }

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}