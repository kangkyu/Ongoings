package com.lininglink.ongoings.views

import com.lininglink.ongoings.api.LoadingState
import com.lininglink.ongoings.api.Task

data class TasksUiState(
    val loadingState: LoadingState = LoadingState.Loading,
    val tasks: List<Task> = emptyList(),
    val error: String? = null
)
