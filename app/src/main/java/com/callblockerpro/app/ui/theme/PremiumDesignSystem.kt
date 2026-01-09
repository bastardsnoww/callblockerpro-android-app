package com.callblockerpro.app.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * The "God-Tier" Design System for CallBlockerPro.
 * Single source of truth for all physics, colors, and layout tokens.
 */
object CrystalDesign {
    
    // --- 1. The Palette (Neon Crystal) ---
    object Colors {
        val BackgroundDeep = Color(0xFF050505) // Pure Void
        val BackgroundSurface = Color(0xFF0F0F11) // Card Surface
        
        // Primary Accents (Electric)
        val NeonGold = Color(0xFFFFD700)
        val NeonPurple = Color(0xFF7C3AED)
        val NeonBlue = Color(0xFF3B82F6)
        val NeonRed = Color(0xFFEF4444)
        val NeonGreen = Color(0xFF10B981)

        // Text
        val TextPrimary = Color(0xFFFFFFFF)
        val TextSecondary = Color(0xB3FFFFFF) // 70% White
        val TextTertiary = Color(0x66FFFFFF) // 40% White
        
        // Gradients
        val GradientPrimary = Brush.linearGradient(
            listOf(NeonPurple, NeonBlue)
        )
        val GradientGold = Brush.linearGradient(
            listOf(Color(0xFFFFD700), Color(0xFFFDB931))
        )
        val GradientGlass = Brush.verticalGradient(
            listOf(
                Color.White.copy(alpha = 0.08f),
                Color.White.copy(alpha = 0.02f)
            )
        )
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
    
    // --- 4. The Glass Physics ---
    object Glass {
        val BorderThin = 0.5.dp
        val BorderStandard = 1.dp
        val CornerRadius = 24.dp
        val CornerRadiusSmall = 12.dp
    }
}
