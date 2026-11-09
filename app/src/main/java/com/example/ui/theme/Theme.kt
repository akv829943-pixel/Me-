package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = BrandPinkAccent,
  onPrimary = Color.White,
  primaryContainer = BrandCardDark,
  onPrimaryContainer = Color.White,
  secondary = BrandNeonViolet,
  onSecondary = Color.White,
  tertiary = InstaOrange,
  onTertiary = Color.White,
  background = BrandDeepMidnight,
  onBackground = Color.White,
  surface = BrandSurfaceDark,
  onSurface = Color.White,
  surfaceVariant = BrandCardDark,
  onSurfaceVariant = Color(0xFFD1C4E9),
  outline = BrandCardBorder
)

private val LightColorScheme = lightColorScheme(
  primary = BrandPurplePrimary,
  onPrimary = Color.White,
  primaryContainer = BrandSoftPink,
  onPrimaryContainer = BrandTextPrimaryLight,
  secondary = BrandPinkAccent,
  onSecondary = Color.White,
  tertiary = InstaOrange,
  onTertiary = Color.White,
  background = BrandBgLight,
  onBackground = BrandTextPrimaryLight,
  surface = BrandSurfaceLight,
  onSurface = BrandTextPrimaryLight,
  surfaceVariant = Color(0xFFF3EDFD),
  onSurfaceVariant = BrandTextSecondaryLight,
  outline = BrandDividerLight
)

@Composable
fun InstaBoostTheme(
  darkTheme: Boolean = false, // Default to a gorgeous bright, clean, premium gradient light theme with deep dark cards
  content: @Composable () -> Unit
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
