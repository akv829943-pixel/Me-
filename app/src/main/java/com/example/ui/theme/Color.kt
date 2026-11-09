package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Instagram Signature Gradient Colors
val InstaPurple = Color(0xFF833AB4)
val InstaMagenta = Color(0xFFC13584)
val InstaPink = Color(0xFFE1306C)
val InstaRedOrange = Color(0xFFFD1D1D)
val InstaOrange = Color(0xFFF77737)
val InstaGold = Color(0xFFFCAF45)

// InstaBoost Brand Palette
val BrandDeepMidnight = Color(0xFF0F0721)
val BrandSurfaceDark = Color(0xFF190C36)
val BrandCardDark = Color(0xFF231349)
val BrandCardBorder = Color(0xFF3E237E)

val BrandPurplePrimary = Color(0xFF8B2FC9)
val BrandPinkAccent = Color(0xFFFF2E93)
val BrandNeonViolet = Color(0xFFA855F7)
val BrandSoftPink = Color(0xFFFCE7F3)

// Light Theme Neutrals
val BrandBgLight = Color(0xFFFAF7FF)
val BrandSurfaceLight = Color(0xFFFFFFFF)
val BrandCardLight = Color(0xFFFFFFFF)
val BrandTextPrimaryLight = Color(0xFF1E1035)
val BrandTextSecondaryLight = Color(0xFF6B5E87)
val BrandDividerLight = Color(0xFFEADBFF)

// Status Colors
val StatusSuccess = Color(0xFF10B981)
val StatusWarning = Color(0xFFF59E0B)
val StatusInfo = Color(0xFF06B6D4)
val StatusPurple = Color(0xFFA855F7)
val StatusCancelled = Color(0xFFEF4444)

// Gradient Brushes
object InstaGradients {
  val Primary = Brush.horizontalGradient(
    colors = listOf(InstaPurple, InstaMagenta, InstaPink, InstaOrange)
  )

  val VioletPink = Brush.linearGradient(
    colors = listOf(Color(0xFF8B2FC9), Color(0xFFFF2E93))
  )

  val HeroGlow = Brush.verticalGradient(
    colors = listOf(Color(0xFF261054), Color(0xFF0F0721))
  )

  val GoldSunset = Brush.horizontalGradient(
    colors = listOf(InstaOrange, InstaGold)
  )

  val CardBorderGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFFF2E93).copy(alpha = 0.5f), Color(0xFF833AB4).copy(alpha = 0.2f))
  )
}
