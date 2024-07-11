package com.lininglink.ongoings.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lininglink.ongoings.api.LoadingState
import com.lininglink.ongoings.api.SessionDoUnauthorizedException
import com.lininglink.ongoings.api.SessionDoAPI
import com.lininglink.ongoings.utils.TokenManager
import com.lininglink.ongoings.views.TasksUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TasksViewModel: ViewModel(), KoinComponent {
    private val tokenManager: TokenManager by inject()

    private val _isTokenValid = MutableStateFlow(true)
    val isTokenValid = _isTokenValid.asStateFlow()

    private val _tasksUIState = MutableStateFlow(TasksUiState())
    val tasksUIState = _tasksUIState.asStateFlow()

    init {
        getTasks()
    }

    private fun getTasks() {
        _tasksUIState.value = TasksUiState(loadingState = LoadingState.Loading)

        viewModelScope.launch {
            try {
                val token = tokenManager.getToken()
                if (token == null) {
                    _isTokenValid.value = false
                    _tasksUIState.update {
                        it.copy(loadingState = LoadingState.Error, error = "No token available")
                    }
                    return@launch
                }

                SessionDoAPI.shared.getTasks(token).onSuccess { value ->
                    _tasksUIState.update {
                        it.copy(loadingState = LoadingState.Success, tasks = value)
                    }
                }.onFailure { result ->
                    if (result is SessionDoUnauthorizedException) {
                        _isTokenValid.value = false
                        _tasksUIState.update {
                            it.copy(loadingState = LoadingState.Failure, error = "Session expired. Please log in again.")
                        }
                    } else {
                        _tasksUIState.update {
                            it.copy(loadingState = LoadingState.Failure, error = result.toString())
                        }
                    }
                }
            } catch (e: Exception) {
                _isTokenValid.value = false
                _tasksUIState.update {
                    it.copy(loadingState = LoadingState.Error, error = e.message)
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            tokenManager.clearToken()
            _isTokenValid.value = false
            _tasksUIState.update {
                it.copy(loadingState = LoadingState.Initial, tasks = emptyList())
            }
        }
    }

    fun clearTask(taskId: Long) {
        viewModelScope.launch {
            val token = tokenManager.getToken()
            if (token == null) {
                _isTokenValid.value = false
                _tasksUIState.update {
                    it.copy(loadingState = LoadingState.Error, error = "No token available")
                }
                return@launch
            }
            SessionDoAPI.shared.clearTask(taskId, token).onSuccess { success ->
                if (success) {
                    getTasks()
                } else {
                    _tasksUIState.update {
                        it.copy(loadingState = LoadingState.Error, error = "Failed to clear task")
                    }
                }
            }
        }
    }
}
