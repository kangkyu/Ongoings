package com.example.ongoings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ongoings.api.LoadingState
import com.example.ongoings.api.Task
import com.example.ongoings.viewmodel.TasksViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserTasksView() {
    val viewModel: TasksViewModel = koinViewModel()
    val uiState by viewModel.tasksUIState.collectAsState()

    when (uiState.loadingState) {
        LoadingState.Loading -> {
//            Text("Loading")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LoadingState.Success -> {
            TasksGrid(
                tasks = uiState.tasks,
                clickTitleFunc = {}
            )
        }

        LoadingState.Failure -> {
            Text("Failure")
        }

        LoadingState.Error -> {
            Text("Error: ${uiState.error}")
            // button to go to login page, or any ideas?
        }

        else -> {
            Text("Initial")
        }
    }
}

@Composable
fun TasksGrid(tasks: List<Task>, clickTitleFunc: (Task) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxHeight()
            .padding(horizontal = 6.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(
            items = tasks,
            itemContent = { task ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    Row(
                        modifier = Modifier
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(14.dp)
                            .clickable {
                                // start VideoActivity and pass the video details
                                clickTitleFunc(task)
                            },
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(
                            text = task.name,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }
                }
            }
        )
    }
}
