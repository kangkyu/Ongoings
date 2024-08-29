package com.lininglink.ongoings.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.lininglink.ongoings.api.LoadingState
import com.lininglink.ongoings.api.Task
import com.lininglink.ongoings.viewmodel.TasksViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.material3.pulltorefresh.PullToRefreshBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserTasksView(
    goToLogin: () -> Unit
) {
    val viewModel: TasksViewModel = koinViewModel()
    val uiState by viewModel.tasksUIState.collectAsStateWithLifecycle()

    when (uiState.loadingState) {
        LoadingState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        LoadingState.Success -> {
            PullToRefreshBox(
                modifier = Modifier.fillMaxSize(),
                isRefreshing = viewModel.isRefreshing,
                onRefresh = { viewModel.refresh() },
            ) {
                TasksGrid(
                    tasks = uiState.tasks,
                    clickTitleFunc = {},
                    onClear = { task -> viewModel.clearTask(task.id) },
                    onLogout = {
                        viewModel.logout()
                        goToLogin()
                    }
                )
            }
        }

        LoadingState.Failure -> {
            Text("Failure: ${uiState.error}")
        }

        LoadingState.Error -> {
            Text("Error: ${uiState.error}")
            // TODO: button to go to login page, or any ideas?
        }

        else -> {
            Text("Initial")
        }
    }
}

@Composable
fun TasksGrid(
    tasks: List<Task>,
    clickTitleFunc: (Task) -> Unit,
    onClear: (Task) -> Unit,
    onLogout: () -> Unit
) {
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
                TaskItem(task, clickTitleFunc, onClear)
            }
        )
        item {
            LogoutItem(onLogout = onLogout)
        }
    }
}

@Composable
fun LogoutItem(onLogout: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(14.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = {
                    onLogout()
                },
                modifier = Modifier.size(19.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null
                )
            }
            Text(" Logout")
        }
    }
}

@Composable
fun TaskItem(task: Task, clickTitleFunc: (Task) -> Unit, onClear: (Task) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(14.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Row(
            modifier = Modifier.wrapContentHeight()
//            modifier = Modifier.defaultMinSize(minHeight = 46.dp)
        ) {
            Column(
                modifier = Modifier.weight(0.8f),
            ) {
                Row(
                    modifier = Modifier
                        .clickable {
                            clickTitleFunc(task)
                        },
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 16.sp
                                )
                            ) {
                                append(task.name)
                            }
                            append(" ")
                            withStyle(
                                style = SpanStyle(
                                    fontFamily = FontFamily.Default,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight(700.0.toInt()),
                                    color = MaterialTheme.colorScheme.tertiary,
                                    background = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.2f),
                                )
                            ) {
                                append(task.doneWhen())
                            }
                        },
                    )
                }
                if (task.comment.isNotEmpty()) {
                    Text(
                        text = task.comment,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
            }
            Row(
                modifier = Modifier.weight(0.2f),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    onClick = {
                        onClear(task)
                    },
                    modifier = Modifier.size(19.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null
                    )
                }
            }
        }
        LinearDeterminateIndicator(task)
    }
}

@Composable
fun LinearDeterminateIndicator(task: Task) {
    task.progress()?.let { currentProgress ->
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            LinearProgressIndicator(
                progress = { currentProgress },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.primaryContainer,
            )
        }
    }
}
