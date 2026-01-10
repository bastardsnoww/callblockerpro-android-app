package com.callblockerpro.app.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun WeeklyActivityBarChart(
    data: List<Float> = listOf(0.3f, 0.45f, 0.25f, 0.65f, 0.5f, 0.7f, 0.4f),
    modifier: Modifier = Modifier
) {
    var animationStarted by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        animationStarted = true
    }

    // MAP animated values here (outside Canvas)
    val animatedValues = data.mapIndexed { index, value ->
        animateFloatAsState(
            targetValue = if (animationStarted) value else 0f,
            animationSpec = tween(
                durationMillis = 1000,
                delayMillis = index * 50
            ),
            label = "BarAnimation_$index"
        )
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        val barWidth = size.width / (data.size * 2f)
        val spacing = size.width / data.size
        
        data.forEachIndexed { index, value ->
            val animatedValue = animatedValues[index].value
            val barHeight = size.height * animatedValue
            val x = (index * spacing) + (spacing - barWidth) / 2
            val y = size.height - barHeight
            
            // Draw background track
            drawRoundRect(
                color = Color.White.copy(alpha = 0.05f),
                topLeft = Offset(x, 0f),
                size = Size(barWidth, size.height),
                cornerRadius = CornerRadius(4.dp.toPx())
            )

            // Draw active bar
            val isMax = value == data.maxOrNull()
            val gradient = if (isMax) {
                Brush.verticalGradient(
                    colors = listOf(Primary, PrimaryLight)
                )
            } else {
                Brush.verticalGradient(
                    colors = listOf(Color.White.copy(alpha = 0.1f), Color.White.copy(alpha = 0.05f))
                )
            }
            
            drawRoundRect(
                brush = gradient,
                topLeft = Offset(x, y),
                size = Size(barWidth, barHeight),
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }

        // PEAK Line (Max value)
        val maxVal = data.maxOrNull() ?: 1f
        // We assume the data values are 0..1 relative to height, or if they are raw, we normalize. 
        // Logic in code implies values are 0..1 (barHeight = size.height * animatedValue).
        // If data is normalized 0..1:
        val peakY = size.height - (size.height * maxVal)
        if (data.isNotEmpty()) {
             drawLine(
                color = com.callblockerpro.app.ui.theme.Red.copy(alpha = 0.5f),
                start = Offset(0f, peakY),
                end = Offset(size.width, peakY),
                strokeWidth = 1.dp.toPx(),
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }

        // AVERAGE Line
        val avgVal = if (data.isNotEmpty()) data.average().toFloat() else 0f
        val avgY = size.height - (size.height * avgVal)
         if (data.isNotEmpty()) {
            drawLine(
                color = com.callblockerpro.app.ui.theme.Emerald.copy(alpha = 0.5f),
                start = Offset(0f, avgY),
                end = Offset(size.width, avgY),
                strokeWidth = 1.dp.toPx(),
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
        }
    }
}
