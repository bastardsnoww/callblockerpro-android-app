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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleScreen(
    viewModel: com.callblockerpro.app.ui.viewmodel.ScheduleViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val workHoursEnabled by viewModel.workHoursEnabled.collectAsState()
    val startTime by viewModel.startTime.collectAsState()
    val endTime by viewModel.endTime.collectAsState()
    
    var showTimePicker by remember { mutableStateOf(false) }
    var isPickingStartTime by remember { mutableStateOf(true) }
    
    val timePickerState = rememberTimePickerState()

    if (showTimePicker) {
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateTime(isPickingStartTime, timePickerState.hour, timePickerState.minute)
                    showTimePicker = false
                }) { Text("OK", color = Primary) }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) { Text("Cancel", color = Color.Gray) }
            },
            text = {
                TimePicker(state = timePickerState)
            },
            containerColor = CrystalDesign.Colors.SurfaceStitch
        )
    }

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
                    mode = if (workHoursEnabled) "WORK MODE" else "AUTOMATION OFF",
                    time = if (workHoursEnabled) "Active ${startTime} - ${endTime}" else "Tap to enable"
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
                    subtitle = "${startTime} - ${endTime}",
                    icon = Icons.Default.Schedule,
                    iconColor = if (workHoursEnabled) Color(0xFF3B82F6) else Color.Gray,
                    tag = if (workHoursEnabled) "active" else "disabled",
                    tagColor = if (workHoursEnabled) Emerald else Color.Gray,
                    onClick = { viewModel.toggleWorkHours(!workHoursEnabled) }
                )
            }
            
            if (workHoursEnabled) {
                item {
                    // Quick Presets
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf("09:00" to "17:00", "10:00" to "18:00").forEach { (start, end) ->
                            SuggestionChip(
                                onClick = { 
                                    viewModel.updateTime(true, start.split(":")[0].toInt(), 0)
                                    viewModel.updateTime(false, end.split(":")[0].toInt(), 0)
                                },
                                label = { Text("$start - $end") },
                                colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = Color.White.copy(0.05f),
                                    labelColor = Color.White
                                ),
                                border = null
                            )
                        }
                    }
                }

                item {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        PremiumActionCard(
                            title = "Start Time",
                            subtitle = startTime.toString(),
                            icon = Icons.Default.Schedule,
                            iconColor = Primary,
                            tag = null,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                isPickingStartTime = true
                                showTimePicker = true 
                            }
                        )
                        PremiumActionCard(
                            title = "End Time",
                            subtitle = endTime.toString(),
                            icon = Icons.Default.Schedule,
                            iconColor = Primary,
                            tag = null,
                            modifier = Modifier.weight(1f),
                            onClick = { 
                                isPickingStartTime = false
                                showTimePicker = true 
                            }
                        )
                    }
                }
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
