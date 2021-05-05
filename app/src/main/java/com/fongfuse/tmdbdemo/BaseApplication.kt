package com.fongfuse.tmdbdemo

import android.app.Application
import com.fongfuse.tmdbdemo.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            modules(getModules())
        }
    }

    private fun getModules() =
        listOf(
                networkModule,
                serviceModule,
                databaseModule,
                repositoryModule,
                viewModelModule
        )
}