package com.callblockerpro.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.Emerald

@Composable
fun ScanningHorizon(
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Scanning")
    
    val scannerOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "ScannerOffset"
    )

    val glowAlpha by infiniteTransition.animateFloat(
        initialValue = 0.2f,
        targetValue = 0.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "GlowAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp)
            .clip(MaterialTheme.shapes.extraLarge)
            .background(Color.White.copy(alpha = 0.02f))
            .drawBehind {
                val width = size.width
                val height = size.height
                
                // 1. Horizon Line (Draw Phase)
                drawRect(
                    brush = Brush.horizontalGradient(
                        listOf(Color.Transparent, Emerald.copy(alpha = 0.5f), Color.Transparent)
                    ),
                    topLeft = androidx.compose.ui.geometry.Offset(0f, height / 2),
                    size = androidx.compose.ui.geometry.Size(width, 1.dp.toPx())
                )

                // 2. Central Radar Pulse (Draw Phase) - Using glowAlpha
                drawCircle(
                    brush = Brush.radialGradient(
                        listOf(Emerald.copy(alpha = 0.2f * glowAlpha), Color.Transparent)
                    ),
                    radius = 60.dp.toPx(),
                    center = center
                )

                // 3. Moving Scanner Beam (Draw Phase) - Using scannerOffset
                val beamY = scannerOffset * height
                drawRect(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Emerald.copy(alpha = 0.1f * (1f - scannerOffset)),
                            Emerald.copy(alpha = 0.3f),
                            Color.Transparent
                        )
                    ),
                    topLeft = androidx.compose.ui.geometry.Offset(0f, beamY - 50.dp.toPx()),
                    size = androidx.compose.ui.geometry.Size(width, 100.dp.toPx())
                )
            },
        contentAlignment = Alignment.Center
    ) {

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "SCANNING HORIZON",
                style = MaterialTheme.typography.labelMedium,
                color = Emerald,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Secure Atmosphere Active",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray.copy(alpha = 0.8f)
            )
        }
    }
}
