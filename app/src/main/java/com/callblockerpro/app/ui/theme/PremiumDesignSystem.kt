package com.callblockerpro.app.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The "God-Tier" Design System for CallBlockerPro.
 * Single source of truth for all physics, colors, and layout tokens.
 */
object CrystalDesign {
    
    // --- 1. The Palette (Tailwind Match) ---
    object Colors {
        val BackgroundDeep = com.callblockerpro.app.ui.theme.BackgroundDark // #0F0E17
        val BackgroundSurface = com.callblockerpro.app.ui.theme.SurfaceDark // #1E1B2E
        
        // Primary Accents
        val NeonGold = Color(0xFFFFD700)
        val NeonPurple = com.callblockerpro.app.ui.theme.Primary // #5211D4
        val NeonBlue = Color(0xFF3B82F6)
        val NeonRed = Color(0xFFDC2626)
        val NeonGreen = com.callblockerpro.app.ui.theme.Emerald // #10B981

        // Core Brand Colors
        val Primary = com.callblockerpro.app.ui.theme.Primary
        val PrimaryLight = com.callblockerpro.app.ui.theme.PrimaryLight
        val PrimaryContainer = com.callblockerpro.app.ui.theme.PrimaryContainer
        
        // Backgrounds
        val BackgroundLight = com.callblockerpro.app.ui.theme.BackgroundLight
        val BackgroundDark = com.callblockerpro.app.ui.theme.BackgroundDark
        val SurfaceGlass = com.callblockerpro.app.ui.theme.SurfaceGlass

        // Text
        val TextPrimary = Color(0xFFFFFFFF)
        val TextSecondary = Color(0xFF94A3B8) // Slate-400 equivalent
        val TextTertiary = Color(0xFF64748B) // Slate-500
        
        // Shadows (Tailwind Translations)
        val ShadowGlow = Color(0x805211D4) // rgba(82, 17, 212, 0.5)
        val ShadowGlowGreen = Color(0x6610B981) // rgba(16, 185, 129, 0.4)
    }

    // --- 2. The Spacing (8pt Grid) ---
    object Spacing {
        val xxs = 4.dp
        val xs = 8.dp
        val s = 12.dp
        val m = 16.dp
        val l = 24.dp
        val xl = 32.dp
        val xxl = 48.dp
        val xxxl = 64.dp
    }
    
    // --- 3. The Physics (Animation Specs) ---
    object Animations {
        val SpringFast = spring<Float>(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        )
        
        val SpringBouncy = spring<Float>(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    }
    
    // --- 4. The Typography (Manrope Standard) ---
    object Typography {
        val WeightMedium = FontWeight.Medium
        val WeightBold = FontWeight.Bold
        val WeightBlack = FontWeight.Black
        
        // Custom sizes removed in favor of MaterialTheme.typography
    }
    
    // --- 5. The Glass Physics ---
    object Glass {
        val BorderThin = 0.5.dp
        val BorderStandard = 1.dp
        val CornerRadius = 24.dp
        val CornerRadiusSmall = 12.dp
    }

    // --- 6. The Tactile Symphony (Haptics) ---
    object Haptics {
        // Semantic haptic roles
        const val Selection = 0 // HapticFeedbackType.TextHandleMove (light tap)
        const val Action = 1    // HapticFeedbackType.LongPress (medium bump)
        const val Error = 2     // Vibration (simulated or heavy)
    }
}
