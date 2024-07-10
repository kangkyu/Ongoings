package com.example.ongoings.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AppTopBar(
    onAddClicked: () -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background
    ) {
        NavigationBarItem(
            selected = false,
            onClick = {
                onAddClicked()
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Sermons Icon"
                )
            },
            label = {
                Text("", textAlign = TextAlign.Center)
            }
        )
    }
}
