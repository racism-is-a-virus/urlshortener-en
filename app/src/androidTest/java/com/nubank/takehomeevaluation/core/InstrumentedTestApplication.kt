package com.nubank.takehomeevaluation.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class InstrumentedTestApplication : Application() {

    internal fun startDependencyInjection(modules: List<Module>) {
        startKoin {
            androidContext(this@InstrumentedTestApplication)
            modules(modules)
        }
    }

    internal fun stopDependencyInjection() {
        stopKoin()
    }
}