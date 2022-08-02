package com.takehomeevaluation.urlshortener.injection

import com.takehomeevaluation.urlshortener.usecase.RegisterUrlUseCase
import org.koin.dsl.module

object UseCaseModule {
   val module = module {
        factory { RegisterUrlUseCase(repo = get()) }
    }
}