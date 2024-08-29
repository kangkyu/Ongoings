package com.lininglink.ongoings.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lininglink.ongoings.viewmodel.LoginViewModel
import com.lininglink.ongoings.viewmodel.TasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppNavigation(navHostController: NavHostController = rememberNavController()) {
    val loginViewModel: LoginViewModel = koinViewModel()
    val tasksViewModel: TasksViewModel = koinViewModel()

    val startDestination = remember(loginViewModel) {
        if (loginViewModel.isLoggedIn()) Screens.UserTasksScreen.route else Screens.LoginScreen.route
    }

    val isTokenValid by tasksViewModel.isTokenValid.collectAsState()

    LaunchedEffect(isTokenValid) {
        if (!isTokenValid) {
            navHostController.navigate(Screens.LoginScreen.route) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination
    ) {
        composable(Screens.LoginScreen.route) {
            LoginView(
                onLoginSuccess = {
                    // popUpTo() prevents the user from navigating back to the login screen
                    // by pressing the back button after they've successfully logged in.
                    navHostController.navigate(Screens.UserTasksScreen.route) {
                        popUpTo(Screens.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screens.UserTasksScreen.route) {
            UserTasksView(
                goToLogin = {
                    navHostController.navigate(Screens.LoginScreen.route) {
                        popUpTo(Screens.UserTasksScreen.route) { inclusive = true }
                    }
                }
            )
        }
    }
}

sealed class Screens(val route: String) {
    object LoginScreen : Screens("login")
    object UserTasksScreen : Screens("tasks")
}
