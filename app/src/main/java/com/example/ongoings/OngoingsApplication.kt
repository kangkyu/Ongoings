package com.example.ongoings

import android.app.Application
import com.example.ongoings.di.appModules
import org.koin.core.context.startKoin

class OngoingsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(appModules)
        }
    }
}
