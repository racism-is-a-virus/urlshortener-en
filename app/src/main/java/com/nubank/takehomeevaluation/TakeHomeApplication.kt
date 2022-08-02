package com.nubank.takehomeevaluation

import androidx.multidex.MultiDexApplication
import com.nubank.takehomeevaluation.injection.AppModule
import org.koin.android.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class TakeHomeApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        setupKoin()
        setupTimber()
    }

    private fun setupKoin() {
        startKoin {
            androidContext(this@TakeHomeApplication)
            modules(AppModule.getModules())
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}