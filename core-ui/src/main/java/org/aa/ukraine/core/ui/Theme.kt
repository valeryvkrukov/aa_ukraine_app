package org.aa.ukraine.core.ui

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AAPrimaryBlueDark,
    onPrimary = Color(0xFF121212), // Black text on a light blue background
    secondary = AASecondaryBlueDark,
    onSecondary = Color.White,
    tertiary = AAAmberAccentDark,
    onTertiary = Color(0xFF121212),
    background = AABackgroundDark,
    onBackground = AAOnSurfaceTextDark,
    surface = AASurfaceDark,
    onSurface = AAOnSurfaceTextDark,
    onSurfaceVariant = AAMutedTextDark
)

private val LightColorScheme = lightColorScheme(
    primary = AAPrimaryBlue,
    primaryContainer = AASiteHeaderBackground,
    onPrimaryContainer = AALogoBackground,
    onPrimary = Color.White,
    secondary = AASecondaryBlue,
    onSecondary = Color.White,
    tertiary = AAAmberAccent,
    onTertiary = Color.White,
    background = AABackground,
    surface = AASurface,
    onSurface = AAOnSurfaceText,
    onSurfaceVariant = AAMutedText
)

@Composable
fun AAUkraineTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Disable dynamic color for on Android 12+ (prevent overriding of theme colors)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AAUkraineTypography,
        content = content
    )
}

