package com.example.nammapustaka_library.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(

    primary = PurplePrimary,

    secondary = PurpleSecondary,

    background = LightPurpleBackground,

    surface = CardColor,

    onPrimary = White,

    onBackground = TextDark,

    onSurface = TextDark
)

private val DarkColors = darkColorScheme(

    primary = PurpleSecondary
)

@Composable
fun NammaPustakaLibraryTheme(

    darkTheme: Boolean =
        isSystemInDarkTheme(),

    content: @Composable () -> Unit
) {

    val colors = if (darkTheme) {
        DarkColors
    } else {
        LightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}