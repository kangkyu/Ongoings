package com.example.ongoings.views

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(navHostController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navHostController,
        startDestination = Screens.LoginScreen.route
    ) {
        composable(Screens.LoginScreen.route) {
            LoginView(
                onLoginSuccess = {
                    navHostController.navigate(Screens.UserTasksScreen.route)
                }
            )
        }
        composable(Screens.UserTasksScreen.route) {
            UserTasksView()
        }
    }
}

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login")
    object UserTasksScreen : Screens("tasks")
}
