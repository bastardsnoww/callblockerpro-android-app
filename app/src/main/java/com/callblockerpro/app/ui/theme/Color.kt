package com.callblockerpro.app.ui.theme

import androidx.compose.ui.graphics.Color

// Brand Colors
// Brand Colors - Electric Neon
// Tailwind Brand Colors
val Primary = Color(0xFF5211D4)      // tailwind: primary
val PrimaryLight = Color(0xFF7A3EE6) // tailwind: primary-light
val OnPrimary = Color.White
val PrimaryContainer = Color(0xFF2E1065)
val OnPrimaryContainer = Color(0xFFDDD6FE)

// Backgrounds - Tailwind Dark
val BackgroundLight = Color(0xFFF8FAFC)
val BackgroundDark = Color(0xFF0F0E17) // tailwind: background-dark
val SurfaceDark = Color(0xFF1E1B2E)    // tailwind: surface
val SurfaceLighter = Color(0xFF2D2A42) // tailwind: surface-lighter
val SurfaceGlass = Color(0xCC1E1B2E)   // High opacity for solid feel matches snapshot

// Status - High Contrast Neon
val Emerald = Color(0xFF10B981) // Neon Emerald
val Amber = Color(0xFFF59E0B)   // Vivid Amber
val Red = Color(0xFFF43F5E)     // Neon Rose/Red

// Text - Crystal White
val TextPrimaryDark = Color(0xFFF8FAFC)
val TextSecondaryDark = Color.White.copy(alpha = 0.8f) // Better contrast

val TextPrimaryLight = Color(0xFF0F172A)
val TextSecondaryLight = Color.Black.copy(alpha = 0.6f)

// Luxury Gradients - Updated for Neon Crystal
val GlassGradientColors = listOf(
    Color(0xFF1E1B4B).copy(alpha = 0.6f), // Deep indigo
    Color(0xFF0F172A).copy(alpha = 0.4f)
)

val GlassBorderColors = listOf(
    Color.White.copy(alpha = 0.4f), // Brighter crystal edge
    Color.White.copy(alpha = 0.1f)
)

// Crypto-Liquid Selection
val MetallicGradientColors = listOf(
    Color(0xFFFFFFFF),
    Color(0xFFDDD6FE),
    Color(0xFFC4B5FD),
    Color(0xFFDDD6FE),
    Color(0xFFFFFFFF)
)

val MetallicTrackColors = listOf(
    Color(0xFF030014),
    Color(0xFF1E1B4B)
)
