package com.example.ongoings.di

import com.example.ongoings.utils.TokenManager
import com.example.ongoings.viewmodel.LoginViewModel
import com.example.ongoings.viewmodel.TasksViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModules = module {
    viewModel { LoginViewModel() }
    viewModel { TasksViewModel() }
    single { TokenManager(androidContext()) }
}
