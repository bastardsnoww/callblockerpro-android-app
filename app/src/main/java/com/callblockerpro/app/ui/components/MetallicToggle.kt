package com.callblockerpro.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.callblockerpro.app.ui.theme.MetallicGradientColors
import com.callblockerpro.app.ui.theme.MetallicTrackColors
import com.callblockerpro.app.ui.theme.CrystalDesign

@Composable
fun MetallicToggle(
    options: List<String>,
    selectedIndex: Int,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    
    BoxWithConstraints(
        modifier = modifier
            .height(56.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(MetallicTrackColors),
                shape = RoundedCornerShape(CrystalDesign.Glass.CornerRadiusSmall)
            )
            .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(CrystalDesign.Glass.CornerRadiusSmall))
            .padding(CrystalDesign.Spacing.xxs)
    ) {
        val maxWidth = maxWidth
        val itemWidth = maxWidth / options.size
        
        val indicatorOffset by animateDpAsState(
            targetValue = itemWidth * selectedIndex,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            label = "IndicatorOffset"
        )

        // Shimmer Animation for Refracting Light
        val shimmerTransition = rememberInfiniteTransition(label = "Shimmer")
        val shimmerAlpha by shimmerTransition.animateFloat(
            initialValue = 0f,
            targetValue = 0.4f,
            animationSpec = infiniteRepeatable(
                animation = tween(2000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "ShimmerAlpha"
        )

        // Sliding Indicator
        Box(
            modifier = Modifier
                .width(itemWidth)
                .fillMaxHeight()
                .padding(2.dp)
                .graphicsLayer {
                    translationX = indicatorOffset.toPx()
                }
                .shadow(elevation = 6.dp, shape = RoundedCornerShape(8.dp))
                .background(
                    brush = Brush.verticalGradient(MetallicGradientColors),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            // Refracting Light Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0f),
                                Color.White.copy(alpha = shimmerAlpha),
                                Color.White.copy(alpha = 0f)
                            )
                        )
                    )
            )
        }

        Row(modifier = Modifier.fillMaxSize()) {
            options.forEachIndexed { index, option ->
                val isSelected = index == selectedIndex
                val textScale by animateFloatAsState(
                    targetValue = if (isSelected) 1.1f else 1f,
                    animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
                    label = "TextScale"
                )
                
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(8.dp))
                        .clickable { 
                            if (!isSelected) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress) // Mechanical bump
                                onOptionSelected(index)
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = option,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = CrystalDesign.Typography.WeightBold,
                        color = if (isSelected) Color(0xFF1a1a1a) else Color.Gray,
                        modifier = Modifier.graphicsLayer {
                            scaleX = textScale
                            scaleY = textScale
                        }
                    )
                }
            }
        }
    }
}
