package com.example.todolist.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Accent,
    onPrimary = Color.White,
    primaryContainer = AccentTint,
    onPrimaryContainer = AccentDark,
    secondary = AccentDark,
    onSecondary = Color.White,
    background = Background,
    onBackground = TextPrimary,
    surface = Card,
    onSurface = TextPrimary,
    surfaceVariant = AccentTint,
    onSurfaceVariant = TextSecondary,
    outline = Border,
    outlineVariant = BorderSubtle,
    tertiary = AccentDark,
)

@Composable
fun TodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // 设计稿为暖奶油底浅色方案；此处固定浅色，保证视觉一致。
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}
