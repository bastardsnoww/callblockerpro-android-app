package com.callblockerpro.app.ui.theme

import androidx.compose.ui.graphics.Color

// Brand Colors
val Primary = Color(0xFF5211d4)
val PrimaryLight = Color(0xFF7a3ee6)
val OnPrimary = Color.White
val PrimaryContainer = Color(0xFFEADDFF)
val OnPrimaryContainer = Color(0xFF21005D)

// Backgrounds
val BackgroundLight = Color(0xFFf6f6f8)
val BackgroundDark = Color(0xFF161022)
val SurfaceDark = Color(0xFF1f1c27)
val SurfaceGlass = Color(0xB31F1C27) // rgba(31, 28, 39, 0.7)

// Status
val Emerald = Color(0xFF34D399) // Success/Allowed
val Amber = Color(0xFFFBBF24)   // Warning/Spam
val Red = Color(0xFFEF4444)     // Error/Blocked

// Text
val TextPrimaryDark = Color.White
val TextSecondaryDark = Color(0xFF94A3B8) // Slate 400

val TextPrimaryLight = Color(0xFF0F172A) // Slate 900
val TextSecondaryLight = Color(0xFF64748B) // Slate 500

// Luxury Gradients
val GlassGradientColors = listOf(
    Color(0xFF2d2a42).copy(alpha = 0.7f),
    Color(0xFF1f1c27).copy(alpha = 0.4f)
)

val GlassBorderColors = listOf(
    Color.White.copy(alpha = 0.15f),
    Color.White.copy(alpha = 0.02f)
)

val MetallicGradientColors = listOf(
    Color(0xFFE0E0E0), // Highlight
    Color(0xFFBDBDBD), // Mid
    Color(0xFF9E9E9E), // Shadow
    Color(0xFFBDBDBD), // Reflection
    Color(0xFFE0E0E0)  // Highlight
)

val MetallicTrackColors = listOf(
    Color(0xFF151515),
    Color(0xFF252525)
)
