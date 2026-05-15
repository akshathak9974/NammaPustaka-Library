package com.example.nammapustaka_library.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {

    LaunchedEffect(Unit) {

        delay(2000)

        onSplashFinished()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),

        horizontalAlignment =
            Alignment.CenterHorizontally,

        verticalArrangement =
            Arrangement.Center
    ) {

        Text(
            text = "📚",
            style =
                MaterialTheme.typography.displayLarge
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "Namma Pustaka",
            style =
                MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "Smart Library Assistant"
        )
    }
}