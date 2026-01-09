package com.callblockerpro.app.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.PrimaryLight
import com.callblockerpro.app.ui.theme.CrystalDesign

@Composable
fun BottomNavBar(
    currentRoute: String,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
) {
    // Glass Navigation Bar
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        GlassPanel(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 32.dp
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
            NavItem(
                icon = Icons.Default.Dashboard,
                label = "Home",
                isSelected = currentRoute == "home",
                onClick = { onNavigate("home") },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                icon = Icons.Default.ListAlt,
                label = "Lists",
                isSelected = currentRoute == "lists",
                onClick = { onNavigate("lists") },
                modifier = Modifier.weight(1f)
            )

            // Central FAB - Elite Upgrade
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                val infiniteTransition = rememberInfiniteTransition(label = "PulsingFAB")
                val pulseScale by infiniteTransition.animateFloat(
                    initialValue = 1f,
                    targetValue = 1.1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(2000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "PulseScale"
                )
                
                val haptic = LocalHapticFeedback.current
                var fabPressed by remember { mutableStateOf(false) }
                val fabScale by animateFloatAsState(
                    targetValue = if (fabPressed) 0.9f else 1f,
                    animationSpec = tween(100),
                    label = "FABScale"
                )

                // Outer Glow
                Box(
                    modifier = Modifier
                        .offset(y = (-24).dp)
                        .size(72.dp)
                        .graphicsLayer {
                            scaleX = pulseScale
                            scaleY = pulseScale
                            alpha = 0.3f
                        }
                        .background(
                            brush = Brush.radialGradient(
                                colors = listOf(Primary, Color.Transparent)
                            ),
                            shape = CircleShape
                        )
                )

                // Main FAB Button
                Box(
                    modifier = Modifier
                        .offset(y = (-24).dp)
                        .size(64.dp)
                        .graphicsLayer {
                            scaleX = fabScale
                            scaleY = fabScale
                        }
                        .shadow(elevation = 15.dp, shape = CircleShape, spotColor = Primary)
                        .clip(CircleShape)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(Primary, PrimaryLight)
                            )
                        )
                        .border(1.dp, Color.White.copy(0.2f), CircleShape)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    fabPressed = true
                                    tryAwaitRelease()
                                    fabPressed = false
                                },
                                onTap = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    onNavigate("add")
                                }
                            )
                        },
                    contentAlignment = Alignment.Center
                ) {
                    // Glassy Overlay for extra depth
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(Color.White.copy(alpha = 0.2f), Color.Transparent)
                                )
                            )
                    )
                    
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            NavItem(
                icon = Icons.Default.Phone,
                label = "Logs",
                isSelected = currentRoute == "logs",
                onClick = { onNavigate("logs") },
                modifier = Modifier.weight(1f)
            )
            NavItem(
                icon = Icons.Default.Settings,
                label = "Settings",
                isSelected = currentRoute == "settings",
                onClick = { onNavigate("settings") },
                modifier = Modifier.weight(1f)
            )
        }
        }
    }
}

@Composable
fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val iconColor = if (isSelected) Primary else Color.White.copy(alpha = 0.4f)
    val textColor = if (isSelected) Color.White else Color.White.copy(alpha = 0.4f)
    
    val hapticNav = androidx.compose.ui.platform.LocalHapticFeedback.current
    Column(
        modifier = modifier
            .clickable { 
                hapticNav.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                onClick() 
            }
            .padding(CrystalDesign.Spacing.xs),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (isSelected) {
                // Subtle glow behind icon
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .background(Primary.copy(alpha = 0.2f), CircleShape)
                )
            }
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            fontWeight = if (isSelected) CrystalDesign.Typography.WeightBlack else CrystalDesign.Typography.WeightMedium,
            color = textColor,
            letterSpacing = if (isSelected) 0.5.sp else 0.sp
        )
    }
}
