package com.joeybasile.knowledgemanagement.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color



private val DarkColorPalette = darkColors(
    primary = Color(0xFF121212),          // Almost black (used for primary elements like app bar)
    //primaryVariant = Color(0xFF1E1E1E),   // Dark gray (for elements requiring slightly different shades)
    secondary = Color(0xFF888888),        // Mid-gray for secondary elements
    background = Color(0xFF000000),       // Pure black background
    surface = Color(0xFF1C1C1C),          // Dark gray surface for cards, etc.
    onSecondary = Color(0xFFFFFFFF),      // White text on secondary (gray)
    onBackground = Color(0xFFFFFFFF),     // White text on black background
    onSurface = Color(0xFFFFFFFF),
    primaryVariant = Color(0xFF404040),
    onPrimary = Color(0xFFFFFFFF)         // Black text on bright white (selected) for contrast
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFFFFFFF),          // Pure white for primary elements (app bar, etc.)
    //primaryVariant = Color(0xFFF5F5F5),   // Slightly off-white for variety
    secondary = Color(0xFF000000),        // Pure black for secondary elements
    background = Color(0xFFFFFFFF),       // White background
    surface = Color(0xFFF0F0F0),          // Light gray surface for cards, etc.
    //onPrimary = Color(0xFF000000),        // Black text on white primary
    onSecondary = Color(0xFFFFFFFF),      // White text on black secondary elements
    onBackground = Color(0xFF000000),     // Black text on white background
    onSurface = Color(0xFF000000),         // Black text on gray surface
    primaryVariant = Color(0xFF404040),   // Dark gray for selected elements (like buttons)
    onPrimary = Color(0xFF000000)         // Black text on selected dark gray elements

)



private val LocalThemeState = compositionLocalOf { mutableStateOf(false) }

//perhaps this should observe the db or something via a flow... but the splash/(eventual initializer)
//should be feeding the information in.
object ThemeManager {
    var isDarkTheme by mutableStateOf(false)

    fun toggleTheme(): Boolean {
        isDarkTheme = !isDarkTheme
        return isDarkTheme
    }
}
@Composable
fun AppTheme(
    darkTheme: Boolean = ThemeManager.isDarkTheme,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    if (darkTheme){
        println("Switched to dark mode. LOCATION: THEME.KT")
    } else {
        println("Switched to light mode. LOCATION: THEME.KT")
    }
    CompositionLocalProvider(LocalThemeState provides mutableStateOf(darkTheme)) {
        MaterialTheme(
            colors = colors,
            content = content
        )
    }
}

@Composable
fun useTheme(): State<Boolean> = LocalThemeState.current
