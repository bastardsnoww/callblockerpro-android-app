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

@Composable
fun InsightGraph(
    modifier: Modifier = Modifier,
    lineColor: Color = Primary
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Mock data points normalized 0..1
        val points = listOf(0.7f, 0.4f, 0.6f, 0.2f, 0.5f, 0.2f, 0.5f)
        
        if (points.isEmpty()) return@Canvas

        val path = Path()
        val stepX = width / (points.size - 1)
        
        points.forEachIndexed { index, value ->
            val x = index * stepX
            val y = height - (value * height) // Invert Y
            if (index == 0) {
                path.moveTo(x, y)
            } else {
                // Simple cubic bezier smoothing could be added here, 
                // but for now simple lineTo or quadraticBezier for smoothness
                val prevX = (index - 1) * stepX
                val prevY = height - (points[index - 1] * height)
                
                val midX = (prevX + x) / 2
                path.cubicTo(midX, prevY, midX, y, x, y)
            }
        }

        // Draw the gradient fill
        val fillPath = Path().apply {
            addPath(path)
            lineTo(width, height)
            lineTo(0f, height)
            close()
        }
        
        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    lineColor.copy(alpha = 0.5f),
                    lineColor.copy(alpha = 0.0f)
                )
            )
        )

        // Draw the line
        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
        
        // Draw Grid lines (dashed effect simulated by low alpha lines)
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
    }
}
