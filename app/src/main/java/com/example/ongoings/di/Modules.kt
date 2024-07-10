package com.example.ongoings.di

import com.example.ongoings.utils.TokenManager
import com.example.ongoings.viewmodel.LoginViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModules = module {
    single { LoginViewModel() }
    single { TokenManager(androidContext()) }
}
