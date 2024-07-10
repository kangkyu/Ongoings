package com.example.ongoings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ongoings.api.SessionDoAPI
import kotlinx.coroutines.launch

class LoginViewModel: ViewModel() {
    fun login(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                SessionDoAPI.shared.login().onSuccess { result ->
                    result?.let {
                        saveAuthToken("token")
                        onSuccess()
                    }
                }.onFailure {
                    onError("Login failed: message")
                }
            } catch (e: Exception) {
                onError("An error occurred: ${e.message}")
            }
        }
    }

    private fun saveAuthToken(token: String) {
        // Implement token saving logic here
    }
}