package com.lininglink.ongoings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.lininglink.ongoings.ui.theme.OngoingsTheme
import com.lininglink.ongoings.views.AppNavigation

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OngoingsTheme {
                AppNavigation()
            }
        }
    }
}
