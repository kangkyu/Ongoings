package com.example.ongoings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ongoings.api.LoginRequest
import com.example.ongoings.api.SessionDoAPI
import com.example.ongoings.utils.TokenManager
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginViewModel: ViewModel(), KoinComponent {
    private val tokenManager: TokenManager by inject()

    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                SessionDoAPI.shared.login(LoginRequest(email, password)).onSuccess { user ->
                    tokenManager.saveToken(user.token)
                    onSuccess()
                }.onFailure { failed ->
                    onError("Login failed: ${failed.message}")
                }
            } catch (e: Exception) {
                onError("An error occurred: ${e.message}")
            }
        }
    }

    fun logout() {
        tokenManager.clearToken()
    }
}
