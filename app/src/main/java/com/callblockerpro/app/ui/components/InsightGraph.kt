package com.callblockerpro.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.CrystalDesign

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.PathMeasure

@Composable
fun InsightGraph(
    modifier: Modifier = Modifier,
    lineColor: Color = Primary
) {
    val animationProgress = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1500)
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Mock data points normalized 0..1
        val points = listOf(0.7f, 0.4f, 0.6f, 0.2f, 0.5f, 0.2f, 0.5f)
        
        if (points.isEmpty()) return@Canvas

        val fullPath = Path()
        val stepX = width / (points.size - 1)
        
        points.forEachIndexed { index, value ->
            val x = index * stepX
            val y = height - (value * height)
            if (index == 0) {
                fullPath.moveTo(x, y)
            } else {
                val prevX = (index - 1) * stepX
                val prevY = height - (points[index - 1] * height)
                val midX = (prevX + x) / 2
                fullPath.cubicTo(midX, prevY, midX, y, x, y)
            }
        }

        // Animate the path drawing
        val pathMeasure = PathMeasure()
        pathMeasure.setPath(fullPath, false)
        val animatedPath = Path()
        pathMeasure.getSegment(0f, pathMeasure.length * animationProgress.value, animatedPath, true)

        // Draw the gradient fill (fades in slightly as the line draws)
        val fillPath = Path().apply {
            addPath(animatedPath)
            if (animationProgress.value > 0f) {
                // Approximate completion of fill path
                val lastPointX = width * animationProgress.value
                // For simplicity, we just use the animated path and close it to the bottom
                // but a perfect fill would follow the line exactly.
                lineTo(lastPointX, height)
                lineTo(0f, height)
                close()
            }
        }
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lineColor.copy(alpha = 0.3f * animationProgress.value),
                    lineColor.copy(alpha = 0.0f)
                )
            )
        )

        // Draw the line
        drawPath(
            path = animatedPath,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        
        // Draw Grid lines
        val gridStepY = height / 3
        for (i in 1..3) {
            val y = i * gridStepY
            drawLine(
                color = Color.White.copy(alpha = 0.05f),
                start = Offset(0f, y),
                end = Offset(width, y),
                strokeWidth = 1.dp.toPx()
            )
        }

        // PEAK Line (at 0.2 height relative to top, i.e. 80% value)
        val peakY = height * 0.2f
        drawLine(
            color = CrystalDesign.Colors.NeonRed.copy(alpha = 0.5f),
            start = Offset(0f, peakY),
            end = Offset(width, peakY),
            strokeWidth = 1.dp.toPx(),
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        
        // AVERAGE Line (at 0.6 height relative to top, i.e. 40% activity)
        val avgY = height * 0.6f
        drawLine(
            color = CrystalDesign.Colors.NeonGreen.copy(alpha = 0.5f),
            start = Offset(0f, avgY),
            end = Offset(width, avgY),
            strokeWidth = 1.dp.toPx(),
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }
}
