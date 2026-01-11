package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight
import com.callblockerpro.app.ui.components.PremiumHeader
import com.callblockerpro.app.ui.components.PremiumActionCard

@Composable
fun ScheduleScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CrystalDesign.Colors.BackgroundDark)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 96.dp, start = 16.dp, end = 16.dp, bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                ScheduleStatusCard(
                    mode = "WHITELIST MODE",
                    time = "Active until 08:00 AM"
                )
            }

            item {
                Text(
                    "DAILY AUTOMATION",
                    style = MaterialTheme.typography.labelSmall,
                    color = CrystalDesign.Colors.TextTertiary,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }

            item {
                PremiumActionCard(
                    title = "Work Hours",
                    subtitle = "09:00 AM - 05:00 PM",
                    icon = Icons.Default.Schedule,
                    iconColor = Color(0xFF3B82F6),
                    tag = "active",
                    tagColor = Emerald,
                    onClick = {}
                )
            }
            item { Spacer(Modifier.height(80.dp)) }
        }

        PremiumHeader(
            title = "Auto-Schedule",
            onBack = null,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp, start = 16.dp, end = 16.dp),
            actionIcon = Icons.Default.Settings,
            onAction = { /* Settings */ }
        )
    }
}

@Composable
fun ScheduleStatusCard(
    mode: String,
    time: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF1E1B4B),
                        Color(0xFF030014)
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
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(150.dp)
                .offset(x = 30.dp, y = (-30).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Primary.copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(modifier = Modifier.size(8.dp).background(Primary, CircleShape))
                    Text(
                        "CURRENT STATUS",
                        color = PrimaryLight,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = CrystalDesign.Typography.WeightBold
                    )
                }
                Spacer(modifier = Modifier.height(CrystalDesign.Spacing.s))
                Text(
                    mode,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontWeight = CrystalDesign.Typography.WeightBlack
                )
                Text(
                    time,
                    style = MaterialTheme.typography.bodyMedium,
                    color = CrystalDesign.Colors.TextSecondary
                )
            }

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Primary.copy(alpha = 0.2f),
                                Primary.copy(alpha = 0.1f)
                            )
                        )
                    )
                    .border(1.dp, Primary.copy(alpha = 0.3f), RoundedCornerShape(20.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Schedule, null, tint = Color.White, modifier = Modifier.size(32.dp))
            }
        }
    }
}

fun Modifier.scale(scale: Float): Modifier = this.then(Modifier)
