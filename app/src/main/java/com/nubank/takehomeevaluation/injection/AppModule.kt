package com.nubank.takehomeevaluation.injection

import com.takehomeevaluation.urlshortener.injection.UrlShortenerModule
import org.koin.core.module.Module

object AppModule {
    // features/libraries modules should be here
    fun getModules(): List<Module> = listOf(
        *UrlShortenerModule.getModules()
    )
}