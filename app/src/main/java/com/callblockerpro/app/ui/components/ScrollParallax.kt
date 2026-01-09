package com.callblockerpro.app.ui.components

import androidx.compose.foundation.ScrollState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.util.lerp

/**
 * Adds a premium 3D parallax tilt effect based on simple offset progress.
 * @param scrollProgress 0f (top) to 1f (fully scrolled away)
 */
fun Modifier.scrollParallax(scrollProgress: Float): Modifier = this.then(
    Modifier.graphicsLayer {
        val clampedProgress = scrollProgress.coerceIn(0f, 1f)
        
        // 3D Rotation (Tilt backward)
        rotationX = lerp(0f, 10f, clampedProgress)
        
        // Fade out
        alpha = lerp(1f, 0.8f, clampedProgress)
        
        // Scale down into the void
        scaleX = lerp(1f, 0.92f, clampedProgress)
        scaleY = lerp(1f, 0.92f, clampedProgress)
        
        // Parallax translation (Use pure value, let caller handle scale)
        translationY = (clampedProgress * 100f) // Move down slightly as we scroll (lag effect)
    }
)
