package com.callblockerpro.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun PremiumBackground(
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF2D1B69).copy(alpha = 0.3f), // Ambient Purple Glow
                        Color.Transparent
                    ),
                    center = androidx.compose.ui.geometry.Offset(0f, 0f),
                    radius = 1000f
                )
            )
    ) {
        content()
    }
}
