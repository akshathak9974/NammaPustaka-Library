package com.example.nammapustaka_library

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import com.example.nammapustaka_library.ui.screens.LoginScreen
import com.example.nammapustaka_library.ui.screens.SplashScreen
import com.example.nammapustaka_library.ui.theme.NammaPustakaLibraryTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            NammaPustakaLibraryTheme {

                var showSplash by remember {
                    mutableStateOf(true)
                }

                if (showSplash) {

                    SplashScreen(
                        onSplashFinished = {
                            showSplash = false
                        }
                    )

                } else {

                    LoginScreen()
                }
            }
        }
    }
}