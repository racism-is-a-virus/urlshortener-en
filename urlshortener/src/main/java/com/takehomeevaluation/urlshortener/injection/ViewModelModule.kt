package com.takehomeevaluation.urlshortener.injection

import com.takehomeevaluation.urlshortener.ui.shortenerlist.UrlShortenerListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {
    val module = module {
        viewModel { UrlShortenerListViewModel(registerUrlUseCase = get()) }
    }
}