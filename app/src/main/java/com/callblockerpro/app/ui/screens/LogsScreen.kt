package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
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
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

@Composable
fun LogsScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = "logs", onNavigate = onNavigate) }
    ) { paddingValues ->
        var searchQuery by remember { mutableStateOf("") }
        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = CrystalDesign.Spacing.l, end = CrystalDesign.Spacing.l),
                    verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.m)
                ) {
                    // Search (Index 0)
                    item {
                        AnimatedEntrance(index = 0) {
                            PremiumSearchBar(
                                query = searchQuery,
                                onQueryChange = { searchQuery = it },
                                placeholder = "Search logs for threats...",
                                onFilterClick = {}
                            )
                        }
                    }

                    // Filters (Index 1)
                    item {
                        AnimatedEntrance(index = 1) {
                            Row(horizontalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.s)) {
                                FilterChipItem("All Activity", true)
                                FilterChipItem("Blocked", false)
                                FilterChipItem("Whitelisted", false)
                            }
                        }
                    }

                    item {
                        AnimatedEntrance(index = 2) {
                            Column {
                                Spacer(Modifier.height(CrystalDesign.Spacing.xs))
                                Text("TODAY", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextSecondary, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                            }
                        }
                    }

                    item {
                        AnimatedEntrance(index = 3) {
                            LogItem(
                                number = "+1 (555) 019-2834",
                                label = "Spam Risk",
                                time = "10:42 AM",
                                type = LogType.BLOCKED,
                                onClick = {}
                            )
                        }
                    }

                    item {
                        AnimatedEntrance(index = 4) {
                            LogItem(
                                number = "John Doe",
                                label = "Known Contact",
                                time = "9:15 AM",
                                type = LogType.ALLOWED,
                                onClick = {}
                            )
                        }
                    }

                    item {
                        AnimatedEntrance(index = 5) {
                            Column {
                                Spacer(Modifier.height(CrystalDesign.Spacing.xs))
                                Text("YESTERDAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                            }
                        }
                    }

                    item {
                        AnimatedEntrance(index = 6) {
                            LogItem(
                                number = "Unknown Caller",
                                label = "Potential Fraud",
                                time = "Mon 4:32 PM",
                                type = LogType.SPAM,
                                onClick = {}
                            )
                        }
                    }

                    item {
                        AnimatedEntrance(index = 7) {
                            LogItem(
                                number = "Pizza Delivery",
                                label = "Whitelisted Business",
                                time = "Mon 12:01 PM",
                                type = LogType.ALLOWED,
                                onClick = {}
                            )
                        }
                    }
                }

                // Floating Crystal Header
                PremiumHeader(
                    title = "Security Logs",
                    onBack = null,
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    actionIcon = Icons.Default.CloudSync,
                    onAction = { /* Export */ }
                )
            }
        }
    }
}

@Composable
fun FilterChipItem(text: String, selected: Boolean) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val bgColor = if (selected) CrystalDesign.Colors.NeonPurple.copy(alpha = 0.8f) else Color.White.copy(0.05f)
    val borderColor = if (selected) CrystalDesign.Colors.NeonPurple else Color.White.copy(0.1f)
    val textColor = if (selected) Color.White else CrystalDesign.Colors.TextSecondary

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        bgColor,
                        bgColor.copy(alpha = if (selected) 0.6f else 0.02f)
                    )
                )
            )
            .border(1.dp, borderColor, RoundedCornerShape(CrystalDesign.Glass.CornerRadiusSmall))
            .clickable { 
                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
            }
            .padding(horizontal = CrystalDesign.Spacing.m, vertical = CrystalDesign.Spacing.s)
    ) {
        // Inner Glass Highlight
        if (selected) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(Color.White.copy(0.2f), Color.Transparent),
                            radius = 100f
                        )
                    )
            )
        }
        
        
        Text(text.uppercase(), style = MaterialTheme.typography.labelSmall, fontWeight = CrystalDesign.Typography.WeightBlack, color = textColor, letterSpacing = 1.sp)
    }
}
