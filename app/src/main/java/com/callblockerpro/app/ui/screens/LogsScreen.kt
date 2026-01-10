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
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.material3.*
import androidx.compose.runtime.*
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
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
fun LogsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.LogsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    
    // State from ViewModel
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val activeFilter by viewModel.filter.collectAsState()

    // Loading State (preserved for skeleton effect during initial fetch)
    // In a real app, this would come from a "Resource" wrapper in ViewModel
    var isLoading by remember { mutableStateOf(true) }
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(1000) 
        isLoading = false
    }

    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = "logs", onNavigate = onNavigate) },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        var selectedLog by remember { mutableStateOf<com.callblockerpro.app.domain.model.CallLogEntry?>(null) }
        val clipboardManager = androidx.compose.ui.platform.LocalClipboardManager.current
        
        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = CrystalDesign.Spacing.l, end = CrystalDesign.Spacing.l),
                    verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.m)
                ) {
                    if (isLoading) {
                         // Search Skeleton
                        item {
                            PremiumSearchBar(
                                query = searchQuery,
                                onQueryChange = { viewModel.onSearchQueryChanged(it) },
                                placeholder = "Search activity...",
                                onFilterClick = {},
                                isLoading = true
                            )
                        }
                        
                         // Filter Skeleton
                        item {
                            Row(horizontalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.s)) {
                                FilterChipItem("All", true)
                                FilterChipItem("Blocked", false)
                                FilterChipItem("Allowed", false)
                            }
                        }

                        // List Skeleton
                        items(6) { LogListSkeleton() }
                    } else {
                        // Search
                        item {
                            AnimatedEntrance(index = 0) {
                                PremiumSearchBar(
                                    query = searchQuery,
                                    onQueryChange = { viewModel.onSearchQueryChanged(it) },
                                    placeholder = "Search activity...",
                                    onFilterClick = { /* Show advanced filter dialog? */ }
                                )
                            }
                        }

                        // Filters
                        item {
                            AnimatedEntrance(index = 1) {
                                Row(horizontalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.s)) {
                                    FilterChipItem("All", activeFilter == null) { viewModel.onFilterSelected(null) }
                                    FilterChipItem("Blocked", activeFilter == com.callblockerpro.app.domain.model.CallResult.BLOCKED) { viewModel.onFilterSelected(com.callblockerpro.app.domain.model.CallResult.BLOCKED) }
                                    FilterChipItem("Allowed", activeFilter == com.callblockerpro.app.domain.model.CallResult.ALLOWED) { viewModel.onFilterSelected(com.callblockerpro.app.domain.model.CallResult.ALLOWED) }
                                }
                            }
                        }

                        // Group Logs by Date logic
                        // This logic should ideally be in ViewModel, but OK for simple "Today/Yesterday" split here for now
                        val groupedLogs = uiState.groupBy { 
                            // Simple logic: check if timestamp is today/yesterday.
                            // For MVP, we can just group by "Recent" if implementing full date logic is too complex here.
                            // Let's assume the list is sorted by time descending.
                            // We'll just show a "Recent Activity" header.
                            "Recent Activity" 
                        }

                        groupedLogs.forEach { (header, logs) ->
                            if (logs.isNotEmpty()) {
                                item {
                                    AnimatedEntrance(index = 2) {
                                        Column {
                                            Spacer(Modifier.height(CrystalDesign.Spacing.xs))
                                            Text(header.uppercase(), style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextSecondary, fontWeight = CrystalDesign.Typography.WeightBlack, letterSpacing = 2.sp)
                                        }
                                    }
                                }

                                itemsIndexed(logs, key = { _, item -> item.id }) { index, log ->
                                    AnimatedEntrance(index = 3 + index) {
                                        SwipeableLogItem(
                                            log = log,
                                            onDismiss = { 
                                                // Optimistic remove handled by Flow update, but for instant feedback:
                                                viewModel.deleteLog(log.id)
                                                scope.launch {
                                                    val result = snackbarHostState.showSnackbar("Log Deleted", duration = SnackbarDuration.Short)
                                                }
                                            },
                                            onClick = { selectedLog = log }
                                        )
                                    }
                                }
                            }
                        }
                        
                        if (uiState.isEmpty()) {
                            item {
                                PremiumEmptyState(
                                    icon = Icons.Default.CloudSync,
                                    title = "No Logs Found",
                                    message = "Your call activity will appear here."
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
                    actionIcon = Icons.Default.Delete, // Clear all option
                    onAction = { 
                        // Optional: Clear All Logic
                    }
                )

                if (selectedLog != null) {
                    LogActionDialog(
                        log = selectedLog!!,
                        onDismiss = { selectedLog = null },
                        onBlock = {
                            viewModel.blockNumber(selectedLog!!)
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Validation Rule Updated",
                                    duration = SnackbarDuration.Short
                                )
                            }
                            selectedLog = null
                        },
                        onCopy = {
                            clipboardManager.setText(AnnotatedString(selectedLog!!.phoneNumber))
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

// Updating to use Domain Model
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableLogItem(
    log: com.callblockerpro.app.domain.model.CallLogEntry,
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
        // Map Domain Model to UI Component
        // We reuse LogItem logic but with Domain types
        val icon = when(log.result) {
            com.callblockerpro.app.domain.model.CallResult.BLOCKED -> Icons.Default.Block
            com.callblockerpro.app.domain.model.CallResult.ALLOWED -> Icons.Default.VerifiedUser
            else -> Icons.Default.Warning
        }
        val iconColor = when(log.result) {
            com.callblockerpro.app.domain.model.CallResult.BLOCKED -> CrystalDesign.Colors.NeonRed
            com.callblockerpro.app.domain.model.CallResult.ALLOWED -> CrystalDesign.Colors.NeonGreen
            else -> CrystalDesign.Colors.NeonGold
        }
        
        PremiumListItem(
            title = log.phoneNumber,
            subtitle = "${log.result} • ${log.reason ?: "Unknown"}", // Formatting timestamp is better
            tag = log.result.name,
            tagColor = iconColor,
            icon = icon,
            iconColor = iconColor,
            onClick = onClick
        )
    }
}

@Composable
fun FilterChipItem(text: String, selected: Boolean, onClick: () -> Unit = {}) {
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
            .clickable(onClick = onClick)
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
    log: com.callblockerpro.app.domain.model.CallLogEntry,
    onDismiss: () -> Unit,
    onBlock: () -> Unit,
    onCopy: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Number Options", style = MaterialTheme.typography.titleLarge) },
        text = { 
            Column {
                Text(log.phoneNumber, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(log.reason ?: "Unknown", style = MaterialTheme.typography.bodyMedium, color = CrystalDesign.Colors.TextSecondary)
            }
        },
        confirmButton = {
            TextButton(onClick = onBlock) {
                Text(
                    if (log.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED) "UNBLOCK" else "BLOCK",
                    color = if (log.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED) CrystalDesign.Colors.NeonGreen else CrystalDesign.Colors.NeonRed,
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

@Composable
fun LogListSkeleton() {
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.05f),
        Color.White.copy(alpha = 0.1f),
        Color.White.copy(alpha = 0.05f),
    )
    
    val transition = rememberInfiniteTransition(label = "Skeleton")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "SkeletonTranslate"
    )

    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = androidx.compose.ui.geometry.Offset.Zero,
        end = androidx.compose.ui.geometry.Offset(x = translateAnim, y = translateAnim)
    )

    GlassPanel(
        modifier = Modifier.fillMaxWidth().height(80.dp),
        cornerRadius = CrystalDesign.Glass.CornerRadius,
        borderAlpha = 0.05f
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Skeleton
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(brush)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Box(Modifier.height(16.dp).fillMaxWidth(0.6f).clip(RoundedCornerShape(4.dp)).background(brush))
                Spacer(Modifier.height(8.dp))
                Box(Modifier.height(12.dp).fillMaxWidth(0.4f).clip(RoundedCornerShape(4.dp)).background(brush))
            }
        }
    }
}
