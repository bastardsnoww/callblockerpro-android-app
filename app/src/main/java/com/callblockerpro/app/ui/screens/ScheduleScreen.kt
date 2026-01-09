package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.ui.text.TextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

@Composable
fun ScheduleScreen(currentRoute: String, onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate) }
    ) { paddingValues ->
        PremiumBackground {
        Box(Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = CrystalDesign.Spacing.l),
                verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.l)
            ) {
             // Header Spacer
             item { Spacer(Modifier.height(80.dp)) }

             // Status Card
             item {
                 ScheduleStatusCard(
                     mode = "Normal Mode",
                     time = "Until 10:00 PM"
                 )
             }

             // Config Panel
             item {
                 Text("NEW CONFIGURATION", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                 Spacer(Modifier.height(CrystalDesign.Spacing.m))
                 GlassPanel(Modifier.fillMaxWidth()) {
                     Column(Modifier.padding(CrystalDesign.Spacing.l)) {
                         Text("SCHEDULE NAME", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightBold)
                         Spacer(Modifier.height(CrystalDesign.Spacing.s))
                         // Custom Crystal Input
                         BasicTextField(
                            value = "",
                            onValueChange = {},
                            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.White.copy(0.05f), RoundedCornerShape(12.dp))
                                .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp))
                                .padding(16.dp),
                            decorationBox = { innerTextField ->
                                Box {
                                    if (true) Text("e.g. Deep Work", color = Color.Gray)
                                    innerTextField()
                                }
                            }
                         )
                         
                         Spacer(Modifier.height(24.dp))
                         Row(horizontalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.m)) {
                             // Start Time
                             Column(Modifier.weight(1f).background(Color.White.copy(0.03f), RoundedCornerShape(16.dp)).border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(16.dp)).padding(CrystalDesign.Spacing.m)) {
                                 Text("START", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = CrystalDesign.Typography.WeightBold)
                                 Text("09:00 AM", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBold)
                             }
                             // End Time
                             Column(Modifier.weight(1f).background(Color.White.copy(0.03f), RoundedCornerShape(16.dp)).border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(16.dp)).padding(CrystalDesign.Spacing.m)) {
                                 Text("END", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = CrystalDesign.Typography.WeightBold)
                                 Text("05:00 PM", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBold)
                             }
                         }
                         
                         Spacer(Modifier.height(CrystalDesign.Spacing.l))
                         Text("REPEATS ON", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightBold)
                         Spacer(Modifier.height(CrystalDesign.Spacing.s))
                         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                             val hapticDay = androidx.compose.ui.platform.LocalHapticFeedback.current
                             listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                                 val active = index < 5
                                 Box(
                                     modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(if (active) Primary else Color.White.copy(0.05f))
                                        .border(1.dp, if(active) PrimaryLight else Color.Transparent, CircleShape)
                                        .clickable { 
                                            hapticDay.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                                        },
                                     contentAlignment = Alignment.Center
                                 ) {
                                     Text(day, style = MaterialTheme.typography.labelSmall, color = if (active) Color.White else Color.Gray, fontWeight = CrystalDesign.Typography.WeightBold)
                                 }
                             }
                         }
                         
                         Spacer(Modifier.height(24.dp))
                         NeonButton(
                             text = "CREATE SCHEDULE",
                             onClick = {},
                             modifier = Modifier.fillMaxWidth(),
                             icon = Icons.Default.Add
                         )
                     }
                 }
             }

             // Active Schedules
             item {
                 Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Text("ACTIVE SCHEDULES", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                     Text("2 Enabled", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = CrystalDesign.Typography.WeightBold)
                 }
                 Spacer(Modifier.height(CrystalDesign.Spacing.m))
                 PremiumListItem(
                     title = "Night Shift",
                     subtitle = "10:00 PM - 07:00 AM • Daily",
                     icon = Icons.Default.Nightlight,
                     iconColor = Color(0xFFA855F7),
                     tag = "active", // lowercase tag handling check
                     tagColor = Emerald,
                     onClick = {}
                 )
                 Spacer(Modifier.height(12.dp))
                 PremiumListItem(
                     title = "Focus Time",
                     subtitle = "09:00 AM - 12:00 PM • Mon-Fri",
                     icon = Icons.Default.Work,
                     iconColor = Color(0xFF3B82F6),
                     tag = "active",
                     tagColor = Emerald,
                     onClick = {}
                 )
             }
             item { Spacer(Modifier.height(80.dp)) }
        }
        
        // Floating Crystal Header
        PremiumHeader(
            title = "Auto-Schedule",
            onBack = null,
            modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp, start = 16.dp, end = 16.dp),
            actionIcon = Icons.Default.Settings,
            onAction = { /* Settings */ }
        )
        }
    }
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
                        Color(0xFF1E1B4B), // Deep Indigo
                        Color(0xFF030014)  // Space Black
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
                .size(150.dp)
                .offset(x = 30.dp, y = (-30).dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(Primary.copy(alpha = 0.15f), Color.Transparent)
                    )
                )
        )
        
        Row(
            modifier = Modifier.padding(24.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                     Box(modifier = Modifier.size(8.dp).background(Primary, CircleShape))
                     Text("CURRENT STATUS", color = PrimaryLight, style = MaterialTheme.typography.labelSmall, fontWeight = CrystalDesign.Typography.WeightBold)
                }
                Spacer(modifier = Modifier.height(CrystalDesign.Spacing.s))
                Text(mode, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBlack)
                Text(time, style = MaterialTheme.typography.bodyMedium, color = CrystalDesign.Colors.TextSecondary)
            }
            
            // 3D Icon Container
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

fun Modifier.scale(scale: Float): Modifier = this.then(Modifier) // Placeholder for simplicity, ideally use graphicsLayer
