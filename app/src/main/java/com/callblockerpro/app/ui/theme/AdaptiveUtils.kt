package com.callblockerpro.app.ui.theme

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import android.provider.Settings
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Adaptive UI utilities for responsive design across all screen sizes.
 */

/**
 * Screen size classification based on width.
 */
enum class ScreenSize {
    COMPACT,   // Phones in portrait
    MEDIUM,    // Phones in landscape, small tablets
    EXPANDED   // Tablets, foldables
}

/**
 * Get current screen size classification.
 */
@Composable
fun rememberScreenSize(): ScreenSize {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp
    
    return when {
        screenWidthDp < 600 -> ScreenSize.COMPACT
        screenWidthDp < 840 -> ScreenSize.MEDIUM
        else -> ScreenSize.EXPANDED
    }
}

/**
 * Helper to check if current screen is compact (phone).
 */
@Composable
fun isCompactScreen(): Boolean = rememberScreenSize() == ScreenSize.COMPACT

/**
 * Helper to check if current screen is medium (landscape phone/small tablet).
 */
@Composable
fun isMediumScreen(): Boolean = rememberScreenSize() == ScreenSize.MEDIUM

/**
 * Helper to check if current screen is expanded (tablet/foldable).
 */
@Composable
fun isExpandedScreen(): Boolean = rememberScreenSize() == ScreenSize.EXPANDED

/**
 * Adaptive spacing values based on screen size.
 */
object AdaptiveSpacing {
    @Composable
    fun small(): Dp = when (rememberScreenSize()) {
        ScreenSize.COMPACT -> 8.dp
        ScreenSize.MEDIUM -> 12.dp
        ScreenSize.EXPANDED -> 16.dp
    }
    
    @Composable
    fun medium(): Dp = when (rememberScreenSize()) {
        ScreenSize.COMPACT -> 16.dp
        ScreenSize.MEDIUM -> 20.dp
        ScreenSize.EXPANDED -> 24.dp
    }
    
    @Composable
    fun large(): Dp = when (rememberScreenSize()) {
        ScreenSize.COMPACT -> 24.dp
        ScreenSize.MEDIUM -> 32.dp
        ScreenSize.EXPANDED -> 40.dp
    }
    
    @Composable
    fun extraLarge(): Dp = when (rememberScreenSize()) {
        ScreenSize.COMPACT -> 32.dp
        ScreenSize.MEDIUM -> 48.dp
        ScreenSize.EXPANDED -> 64.dp
    }
}

/**
 * Adaptive content padding based on screen size.
 */
@Composable
fun adaptiveContentPadding(): Dp = when (rememberScreenSize()) {
    ScreenSize.COMPACT -> 16.dp
    ScreenSize.MEDIUM -> 24.dp
    ScreenSize.EXPANDED -> 32.dp
}

/**
 * Adaptive card corner radius.
 */
@Composable
fun adaptiveCardRadius(): Dp = when (rememberScreenSize()) {
    ScreenSize.COMPACT -> 20.dp
    ScreenSize.MEDIUM -> 24.dp
    ScreenSize.EXPANDED -> 28.dp
}

/**
 * Adaptive header height.
 */
@Composable
fun adaptiveHeaderHeight(): Dp = when (rememberScreenSize()) {
    ScreenSize.COMPACT -> 80.dp
    ScreenSize.MEDIUM -> 104.dp
    ScreenSize.EXPANDED -> 112.dp
}

/**
 * Maximum content width for large screens (prevents overstretching).
 */
@Composable
fun maxContentWidth(): Dp = when (rememberScreenSize()) {
    ScreenSize.COMPACT -> Dp.Unspecified
    ScreenSize.MEDIUM -> 720.dp
    ScreenSize.EXPANDED -> 960.dp
}

/**
 * Number of columns for grid layouts.
 */
@Composable
fun adaptiveGridColumns(): Int = when (rememberScreenSize()) {
    ScreenSize.COMPACT -> 1
    ScreenSize.MEDIUM -> 2
    ScreenSize.EXPANDED -> 3
}

/**
 * Helper to check if system Reduced Motion (Animator Duration 0x) is enabled.
 */
@Composable
fun isReducedMotionEnabled(): Boolean {
    val context = LocalContext.current
    val scale = Settings.Global.getFloat(
        context.contentResolver,
        Settings.Global.ANIMATOR_DURATION_SCALE,
        1f
    )
    return scale == 0f
}
