package com.takehomeevaluation.urlshortener.injection

import org.koin.core.module.Module

object UrlShortenerModule {
    fun getModules(): Array<Module> = arrayOf(
        ViewModelModule.module,
        UseCaseModule.module,
        DataModule.module,
        GeneralModule.module
    )
}