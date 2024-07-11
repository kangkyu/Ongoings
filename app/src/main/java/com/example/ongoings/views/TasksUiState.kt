package com.example.ongoings.views

import com.example.ongoings.api.LoadingState
import com.example.ongoings.api.Task

data class TasksUiState(
    val loadingState: LoadingState = LoadingState.Loading,
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)
