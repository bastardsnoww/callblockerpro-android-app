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

@Composable
fun WeeklyActivityBarChart(
    data: List<Float> = listOf(0.3f, 0.45f, 0.25f, 0.65f, 0.5f, 0.7f, 0.4f),
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val barWidth = size.width / (data.size * 2f)
        val spacing = size.width / data.size
        
        data.forEachIndexed { index, value ->
            val barHeight = size.height * value
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
    }
}
