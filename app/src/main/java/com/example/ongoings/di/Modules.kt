package com.example.ongoings.di

import com.example.ongoings.viewmodel.LoginViewModel
import org.koin.dsl.module

val appModules = module {
    single { LoginViewModel() }
}
