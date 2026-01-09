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
    
    // --- 1. The Palette (Neon Crystal) ---
    object Colors {
        val BackgroundDeep = Color(0xFF050505) // Pure Void
        val BackgroundSurface = Color(0xFF0F0F11) // Card Surface
        
        // Primary Accents (Electric)
        val NeonGold = Color(0xFFFFD700)
        val NeonPurple = Color(0xFF7C3AED)
        val NeonBlue = Color(0xFF3B82F6)
        val NeonRed = Color(0xFFDC2626) // Darker for accessible contrast (>4.5:1)
        val NeonGreen = Color(0xFF10B981)

        // Core Brand Colors (Unified)
        val Primary = Color(0xFF8B5CF6)      // Electric Violet
        val PrimaryLight = Color(0xFFA78BFA) // Neon Purple
        val PrimaryContainer = Color(0xFF2E1065)
        
        // Backgrounds
        val BackgroundLight = Color(0xFFF8FAFC)
        val BackgroundDark = Color(0xFF030014) // Pure space black-purple
        val SurfaceGlass = Color(0x990F172A)

        // Text
        val TextPrimary = Color(0xFFFFFFFF)
        val TextSecondary = Color(0xD9FFFFFF) // 85% White (Improved from 70%)
        val TextTertiary = Color(0x99FFFFFF) // 60% White (Improved from 40%)
        
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
