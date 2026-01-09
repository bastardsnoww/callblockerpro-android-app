package com.callblockerpro.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun PremiumBackground(
    content: @Composable () -> Unit
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.toFloat()
    val screenHeight = configuration.screenHeightDp.toFloat()

    val infiniteTransition = rememberInfiniteTransition(label = "BackgroundTransition")
    
    // Animate first orb
    val orb1Offset by infiniteTransition.animateValue(
        initialValue = Offset(0f, 0f),
        targetValue = Offset(screenWidth, screenHeight),
        typeConverter = Offset.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(15000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb1"
    )

    // Animate second orb
    val orb2Offset by infiniteTransition.animateValue(
        initialValue = Offset(screenWidth, 0f),
        targetValue = Offset(0f, screenHeight),
        typeConverter = Offset.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(20000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "Orb2"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0A1A)) // Deep base color
    ) {
        // Orb 1
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF5211d4).copy(alpha = 0.15f),
                            Color.Transparent
                        ),
                        center = orb1Offset,
                        radius = 800f
                    )
                )
        )
        
        // Orb 2
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xFF34D399).copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        center = orb2Offset,
                        radius = 600f
                    )
                )
        )

        content()
    }
}
