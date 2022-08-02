package com.takehomeevaluation.urlshortener.injection

import com.takehomeevaluation.core.network.ApiClientBuilder
import com.takehomeevaluation.urlshortener.BuildConfig
import com.takehomeevaluation.urlshortener.data.UrlShortenerRepository
import com.takehomeevaluation.urlshortener.data.remote.UrlShortenerRemoteRepository
import com.takehomeevaluation.urlshortener.data.remote.UrlShortenerService
import org.koin.dsl.bind
import org.koin.dsl.module

object DataModule {
    val module = module {
        single {
            ApiClientBuilder.createServiceApi(
                serviceClass = UrlShortenerService::class.java,
                baseUrl = BuildConfig.BASE_URL
            )
        }
        factory { UrlShortenerRemoteRepository(urlShortenerService = get()) } bind UrlShortenerRepository::class
    }
}