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
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
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
import androidx.compose.ui.res.stringResource
import com.callblockerpro.app.R

@Composable
fun BottomNavBar(
    currentRoute: String,
    modifier: Modifier = Modifier,
    onNavigate: (String) -> Unit = {}
) {
    // Fixed / Full-Width Navigation Bar (Tailwind Match)
    // HTML: fixed bottom-0 left-0 right-0 z-50 bg-[#0f0e17]/90 backdrop-blur-xl border-t border-white/5
    
    // [FIX] Use a Box to allow FAB to float ABOVE the Surface without clipping to bounds
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.BottomCenter
    ) {
        // 1. The Bar Background and Icons
        androidx.compose.material3.Surface(
            modifier = Modifier.fillMaxWidth(),
            color = CrystalDesign.Colors.BackgroundDeep.copy(alpha = 0.9f), // #0f0e17 / 90%
            tonalElevation = 0.dp,
            shadowElevation = 10.dp,
            border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.05f))
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .navigationBarsPadding(), // Handle system insets
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                // Left Group
                NavIconItem(
                    icon = Icons.Default.Dashboard,
                    label = stringResource(R.string.nav_home),
                    isSelected = currentRoute == "home",
                    onClick = { onNavigate("home") }
                )
                
                NavIconItem(
                    icon = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ListAlt,
                    label = stringResource(R.string.nav_logs),
                    isSelected = currentRoute == "logs",
                    onClick = { onNavigate("logs") }
                )

                // Center Spacer (Where FAB floats above)
                Spacer(modifier = Modifier.size(64.dp))

                // Right Group
                NavIconItem(
                    icon = androidx.compose.material.icons.Icons.Default.VerifiedUser, // Whitelist placeholder
                    label = stringResource(R.string.nav_protection),
                    isSelected = currentRoute == "lists", // Mapping Lists to Whitelist for now
                    onClick = { onNavigate("lists") }
                )
                
                NavIconItem(
                    icon = Icons.Default.Settings,
                    label = stringResource(R.string.nav_settings),
                    isSelected = currentRoute == "settings",
                    onClick = { onNavigate("settings") }
                )
            }
        }

        // 2. The FAB (Floating Above)
        // HTML: h-16 w-16 ... rounded-full bg-gradient ... shadow ring-8 ring-[#0f0e17]
        Box(
            modifier = Modifier
                .padding(bottom = 28.dp) // Lift it up slightly above the bar baseline
                .navigationBarsPadding(), // Respect insets
            contentAlignment = Alignment.Center
        ) {
             val haptic = LocalHapticFeedback.current
             Box(
                modifier = Modifier
                    .size(64.dp)
                    .shadow(
                        elevation = 10.dp, 
                        shape = CircleShape,
                        spotColor = CrystalDesign.Colors.Primary.copy(alpha = 0.6f)
                    )
                    .background(CrystalDesign.Colors.BackgroundDeep, CircleShape) // Ring effect
                    .padding(8.dp) // ring-8
                    .clip(CircleShape)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(CrystalDesign.Colors.Primary, CrystalDesign.Colors.PrimaryLight),
                            start = androidx.compose.ui.geometry.Offset(0f, 0f),
                            end = androidx.compose.ui.geometry.Offset.Infinite
                        )
                    )
                    .clickable(
                        onClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            onNavigate("add") 
                        },
                        role = androidx.compose.ui.semantics.Role.Button
                    ),
                contentAlignment = Alignment.Center
             ) {
                 Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.protection_empty_action), // Reusing "Add Number" or just "Add"
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
             }
        }
    }
}

@Composable
fun NavIconItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val color = if (isSelected) CrystalDesign.Colors.Primary else CrystalDesign.Colors.TextTertiary
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clickable(
                onClick = onClick,
                role = androidx.compose.ui.semantics.Role.Tab
            )
            .padding(8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(26.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
            color = if(isSelected) Color.White else CrystalDesign.Colors.TextTertiary,
            fontWeight = FontWeight.Bold
        )
    }
}
