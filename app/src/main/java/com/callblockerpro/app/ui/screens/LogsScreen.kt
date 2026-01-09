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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
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
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = "logs", onNavigate = onNavigate) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        var searchQuery by remember { mutableStateOf("") }
        var selectedLog by remember { mutableStateOf<LogEntry?>(null) }
        val clipboardManager = LocalClipboardManager.current
        
        // State for Logs
        val todayLogs = remember { mutableStateListOf(
            LogEntry(1, "+1 (555) 019-2834", "Spam Risk", "10:42 AM", LogType.BLOCKED),
            LogEntry(2, "John Doe", "Known Contact", "9:15 AM", LogType.ALLOWED)
        ) }
        
        val yesterdayLogs = remember { mutableStateListOf(
             LogEntry(3, "Unknown Caller", "Potential Fraud", "Mon 4:32 PM", LogType.SPAM),
             LogEntry(4, "Pizza Delivery", "Whitelisted Business", "Mon 12:01 PM", LogType.ALLOWED)
        ) }

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

                    // TODAY Section
                    if (todayLogs.isNotEmpty()) {
                        item {
                            AnimatedEntrance(index = 2) {
                                Column {
                                    Spacer(Modifier.height(CrystalDesign.Spacing.xs))
                                    Text("TODAY", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextSecondary, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                                }
                            }
                        }

                        itemsIndexed(todayLogs, key = { _, item -> item.id }) { index, log ->
                            AnimatedEntrance(index = 3 + index) {
                                SwipeableLogItem(
                                    log = log,
                                    onDismiss = { 
                                        todayLogs.remove(log) 
                                        scope.launch {
                                            snackbarHostState.showSnackbar("Log Deleted", "UNDO")
                                               // In real app, re-add here on undo
                                        }
                                    },
                                    onClick = {
                                        selectedLog = log
                                    }
                                )
                            }
                        }
                    }

                    // YESTERDAY Section
                    if (yesterdayLogs.isNotEmpty()) {
                        item {
                            AnimatedEntrance(index = 10) { // Offset index
                                Column {
                                    Spacer(Modifier.height(CrystalDesign.Spacing.xs))
                                    Text("YESTERDAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                                }
                            }
                        }

                        itemsIndexed(yesterdayLogs, key = { _, item -> item.id }) { index, log ->
                            AnimatedEntrance(index = 11 + index) {
                                SwipeableLogItem(
                                    log = log,
                                    onDismiss = { yesterdayLogs.remove(log) },
                                    onClick = { selectedLog = log }
                                )
                            }
                        }
                    }
                }

                // Floating Crystal Header
                PremiumHeader(
                    title = "Security Logs",
                    onBack = null,
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    actionIcon = Icons.Default.CloudSync,
                )

                if (selectedLog != null) {
                    LogActionDialog(
                        log = selectedLog!!,
                        onDismiss = { selectedLog = null },
                        onBlock = {
                            // Toggle Block Status (Simulation)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = if(selectedLog!!.type == LogType.BLOCKED) "Number Unblocked" else "Number Blocked",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            selectedLog = null
                        },
                        onCopy = {
                            clipboardManager.setText(AnnotatedString(selectedLog!!.number))
                            scope.launch {
                                snackbarHostState.showSnackbar("Number Copied", duration = SnackbarDuration.Short)
                            }
                            selectedLog = null
                        }
                    )
                }
            }
        }
    }
}

data class LogEntry(
    val id: Int,
    val number: String,
    val label: String,
    val time: String,
    val type: LogType
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableLogItem(
    log: LogEntry,
    onDismiss: () -> Unit,
    onClick: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onDismiss()
                true
            } else false
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.EndToStart) 
                CrystalDesign.Colors.NeonRed else Color.Transparent
            
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(CrystalDesign.Glass.CornerRadius))
                    .background(color)
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        },
        enableDismissFromStartToEnd = false
    ) {
        LogItem(
            number = log.number,
            label = log.label,
            time = log.time,
            type = log.type,
            onClick = onClick
        )
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

@Composable
fun LogActionDialog(
    log: LogEntry,
    onDismiss: () -> Unit,
    onBlock: () -> Unit,
    onCopy: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Manage Number", style = MaterialTheme.typography.titleLarge) },
        text = { 
            Column {
                Text(log.number, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(log.label, style = MaterialTheme.typography.bodyMedium, color = CrystalDesign.Colors.TextSecondary)
            }
        },
        confirmButton = {
            TextButton(onClick = onBlock) {
                Text(
                    if (log.type == LogType.BLOCKED) "UNBLOCK" else "BLOCK",
                    color = if (log.type == LogType.BLOCKED) CrystalDesign.Colors.NeonGreen else CrystalDesign.Colors.NeonRed,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        dismissButton = {
            Row {
                TextButton(onClick = onCopy) {
                    Text("COPY", color = CrystalDesign.Colors.Primary)
                }
                TextButton(onClick = onDismiss) {
                    Text("CANCEL", color = CrystalDesign.Colors.TextSecondary)
                }
            }
        },
        containerColor = CrystalDesign.Colors.BackgroundSurface,
        titleContentColor = Color.White,
        textContentColor = Color.White
    )
}
