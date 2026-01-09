package com.callblockerpro.app.ui.components

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
    Box(
        modifier = modifier
            .graphicsLayer {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    val blurEffect = RenderEffect.createBlurEffect(
                        30f,
                        30f,
                        Shader.TileMode.MIRROR
                    )
                    renderEffect = blurEffect.asComposeRenderEffect()
                }
                alpha = 0.95f // Slight transparency even with blur
            }
            .clip(RoundedCornerShape(cornerRadius))
            .background(
                brush = Brush.linearGradient(
                    colors = GlassGradientColors, // Uses our new gradient
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(FLOAT_INF, FLOAT_INF)
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    colors = GlassBorderColors
                ),
                shape = RoundedCornerShape(cornerRadius)
            ),
        content = content
    )
}

private const val FLOAT_INF = Float.POSITIVE_INFINITY
