package com.takehomeevaluation.urlshortener.injection

import com.takehomeevaluation.urlshortener.navigation.UrlShortenerNavigation
import com.takehomeevaluation.urlshortener.navigation.UrlShortenerNavigationImpl
import org.koin.dsl.module

object GeneralModule {
    val module = module {
        factory<UrlShortenerNavigation> { UrlShortenerNavigationImpl(get()) }
    }
}