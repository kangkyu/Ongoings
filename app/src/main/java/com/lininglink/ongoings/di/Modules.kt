package com.lininglink.ongoings.di

import com.lininglink.ongoings.api.SessionDoAPI
import com.lininglink.ongoings.utils.TokenManager
import com.lininglink.ongoings.viewmodel.LoginViewModel
import com.lininglink.ongoings.viewmodel.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { LoginViewModel() }
    viewModel { TasksViewModel() }
    single { TokenManager(androidContext()) }
}

val networkModule = module {
    single { SessionDoAPI() } // Provides a single instance of SessionDoAPI
}
