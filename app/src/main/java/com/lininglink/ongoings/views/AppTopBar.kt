package com.lininglink.ongoings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.lininglink.ongoings.ui.theme.AppTypography

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    onAddClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier.padding(horizontal = 12.dp),
        title = {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                modifier = modifier.clickable {
                    onAddClicked()
                }
            )
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopBar() {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 12.dp),
        title = {
            Text("TopBar: default")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginTopBar(
    onSignupClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 12.dp),
        title = {
            Text(
                text = "Sign up",
                style = AppTypography.titleSmall,
                modifier = Modifier
                    .alpha(0.6f)
                    .clickable {
                        onSignupClick()
                    }
            )
        }
    )
}
