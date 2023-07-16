package com.czech.incidents

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.czech.incidents.theme.IncidentsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IncidentsApp()
        }
    }
}

@Composable
fun IncidentsApp() {
    IncidentsTheme {
        val navController = rememberNavController()
        Box(
            modifier = Modifier.fillMaxSize()
        )
    }
}