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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        // 1. Background Layer (frosted glass effect)
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val blurEffect = RenderEffect.createBlurEffect(
                            15f,
                            15f,
                            Shader.TileMode.MIRROR
                        )
                        renderEffect = blurEffect.asComposeRenderEffect()
                    }
                    alpha = 0.9f 
                }
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.linearGradient(
                        colors = GlassGradientColors,
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
        )

        // 2. Frosted Noise Overlay
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0.03f)
                .clip(RoundedCornerShape(cornerRadius))
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White, Color.Transparent),
                        center = Offset(0.5f, 0.5f),
                        radius = 2000f
                    )
                )
        )

        // 3. Shimmer Border
        Box(
            modifier = Modifier
                .matchParentSize()
                .border(
                    width = 1.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.05f),
                            Color.White.copy(alpha = 0.2f),
                            Color.White.copy(alpha = 0.05f)
                        ),
                        start = Offset(shimmerOffset, shimmerOffset),
                        end = Offset(shimmerOffset + 200f, shimmerOffset + 200f)
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
        )

        // 4. Content Layer (sharp, non-blurred)
        Box(
            modifier = Modifier.fillMaxWidth().padding(1.dp), 
            content = content
        )
    }
}

private const val FLOAT_INF = Float.POSITIVE_INFINITY
