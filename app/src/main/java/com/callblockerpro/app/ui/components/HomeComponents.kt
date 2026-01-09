package com.callblockerpro.app.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.sp
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.PrimaryLight
import com.callblockerpro.app.ui.theme.Red

@Composable
fun HomeStatusCard(
    blockedCount: Int = 0,
    threatCount: Int = 0,
    isSystemActive: Boolean = true,
    modifier: Modifier = Modifier
) {
    val statusColor = if (isSystemActive) Emerald else Red
    val statusText = if (isSystemActive) "SECURE & ACTIVE" else "VULNERABLE / ACTION REQUIRED"
    val headlineText = if (isSystemActive) "System\nShielded" else "Shield\nInactive"
    val statusIcon = if (isSystemActive) Icons.Default.VerifiedUser else Icons.Default.Warning

    val infiniteTransition = rememberInfiniteTransition(label = "PulsingGlow")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseScale"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = if (isSystemActive) 0.3f else 0.5f,
        targetValue = if (isSystemActive) 0.6f else 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(if (isSystemActive) 2000 else 1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "PulseAlpha"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        CrystalDesign.Colors.BackgroundSurface,
                        CrystalDesign.Colors.BackgroundDeep
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.3f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(32.dp)
            )
    ) {
        // Decor background glow
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(200.dp)
                .offset(x = 40.dp, y = (-40).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(statusColor.copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )

        Column(modifier = Modifier.padding(28.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(statusColor, CircleShape)
                                .graphicsLayer {
                                    val scale = pulseScale
                                    val alphaVal = pulseAlpha
                                    scaleX = scale
                                    scaleY = scale
                                    alpha = alphaVal
                                }
                        )
                        Text(
                            text = statusText,
                            color = statusColor,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 1.5.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = headlineText,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White,
                        fontWeight = FontWeight.Black,
                        lineHeight = 36.sp
                    )
                }

                // 3D Pulsing Shield Icon - Integrated Warning
                Box(
                    modifier = Modifier.size(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Outer Glow
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .graphicsLayer {
                                val scale = pulseScale
                                val alphaVal = pulseAlpha
                                scaleX = scale
                                scaleY = scale
                                alpha = alphaVal * 0.4f
                            }
                            .background(statusColor.copy(alpha = 0.3f), CircleShape)
                    )
                    
                    // Glass Shield Container
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        statusColor.copy(alpha = 0.15f),
                                        statusColor.copy(alpha = 0.4f)
                                    )
                                )
                            )
                            .border(1.dp, statusColor.copy(alpha = 0.4f), RoundedCornerShape(24.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = statusIcon,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                HomeStatBadge(
                    label = "BLOCKED TODAY",
                    value = blockedCount.toString(),
                    icon = Icons.Default.Block,
                    valueColor = Red
                )
                HomeStatBadge(
                    label = "TOTAL PROTECTED",
                    value = String.format("%,d", threatCount),
                    icon = Icons.Default.Shield,
                    valueColor = PrimaryLight
                )
            }
        }
    }
}

@Composable
fun RowScope.HomeStatBadge(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    valueColor: Color
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White.copy(alpha = 0.03f))
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.2f),
                        Color.White.copy(alpha = 0.05f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = valueColor.copy(alpha = 0.5f),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
            fontWeight = FontWeight.Black
        )
    }
}
