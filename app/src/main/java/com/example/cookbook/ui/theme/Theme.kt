package com.example.cookbook.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Define dark color scheme with specific colors
private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

// Define light color scheme with specific colors
private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

// Composable function to apply the Cookbook theme
@Composable
fun CookBookTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Whether the theme is dark or light based on system setting
    dynamicColor: Boolean = true, // Whether dynamic color is enabled (available on Android 12+)
    content: @Composable () -> Unit
) {
    // Determine color scheme based on theme mode and dynamic color setting
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Apply color scheme to status bar and insets controller
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb() // Set status bar color
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme // Set light or dark status bar icons
        }
    }

    // Apply MaterialTheme with determined color scheme and typography
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
