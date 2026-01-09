package com.callblockerpro.app.ui.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.callblockerpro.app.ui.theme.GlassBorderColors
import com.callblockerpro.app.ui.theme.GlassGradientColors

@Composable
fun GlassPanel(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 24.dp,
    borderAlpha: Float = 0.1f, // Added parameter
    content: @Composable BoxScope.() -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "ShimmerTransition")
    val shimmerOffset by infiniteTransition.animateFloat(
        initialValue = -1000f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing)
        ),
        label = "ShimmerOffset"
    )

    Box(modifier = modifier) {
        // 1. Crystal Background Layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        val blurEffect = RenderEffect.createBlurEffect(
//                            10f,
//                            10f,
//                            Shader.TileMode.MIRROR
//                        )
//                        renderEffect = blurEffect.asComposeRenderEffect()
//                    }
                    alpha = 0.6f // More transparent
                }
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(500f, 500f)
                    )
                )
        )

        // 2. Crystal Highlight Layer (Replacing Noise)
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White.copy(alpha = 0.05f), Color.Transparent),
                        center = Offset(0f, 0f),
                        radius = 400f
                    )
                )
        )

        // 3. Sharp Crystal Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = (borderAlpha * 3).coerceAtMost(0.5f)),
                            Color.White.copy(alpha = borderAlpha * 0.5f)
                        )
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
        )

        // 4. Content Layer
        Box(
            modifier = Modifier.fillMaxWidth().padding(1.dp), 
            content = content
        )
    }
}

private const val FLOAT_INF = Float.POSITIVE_INFINITY
