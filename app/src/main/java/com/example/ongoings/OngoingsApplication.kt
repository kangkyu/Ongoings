package com.example.ongoings

import android.app.Application
import com.example.ongoings.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OngoingsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OngoingsApplication)
            modules(appModules)
        }
    }
}
