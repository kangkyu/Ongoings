package com.lininglink.ongoings

import android.app.Application
import com.lininglink.ongoings.di.appModules
import com.lininglink.ongoings.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OngoingsApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OngoingsApplication)
            modules(appModules, networkModule)
        }
    }
}
