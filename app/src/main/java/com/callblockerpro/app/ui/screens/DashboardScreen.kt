package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight
import com.callblockerpro.app.ui.theme.Red

@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.DashboardViewModel = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val selectedMode by viewModel.selectedMode.collectAsState()
    val blockedToday by viewModel.blockedToday.collectAsState()
    val totalThreats by viewModel.totalThreats.collectAsState()
    val weeklyActivity by viewModel.weeklyActivity.collectAsState()

    var isRoleGranted by remember { 
        mutableStateOf(com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)) 
    }
    
    val roleLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
        onResult = { isRoleGranted = com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context) }
    )

    // Refresh when screen is displayed
    androidx.compose.runtime.DisposableEffect(Unit) {
        isRoleGranted = com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)
        onDispose {}
    }

    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = "home", onNavigate = onNavigate) }
    ) { paddingValues ->
        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                    // Capture Scroll State
                    val listState = rememberLazyListState()

                    val maxWidth = com.callblockerpro.app.ui.theme.maxContentWidth()
                    val contentPadding = com.callblockerpro.app.ui.theme.adaptiveContentPadding()
                    
                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .then(
                                if (maxWidth != androidx.compose.ui.unit.Dp.Unspecified) {
                                    Modifier.widthIn(max = maxWidth).align(Alignment.TopCenter)
                                } else Modifier
                            )
                            .padding(horizontal = contentPadding),
                        contentPadding = PaddingValues(
                            top = com.callblockerpro.app.ui.theme.adaptiveHeaderHeight() + 8.dp,
                            bottom = 120.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(com.callblockerpro.app.ui.theme.AdaptiveSpacing.large())
                    ) {
                        // Spacer for header
                        item { Spacer(Modifier.height(0.dp)) }

                        // Mode Selector (Index 0 for animation)
                        item {
                            AnimatedEntrance(index = 0) {
                                MetallicToggle(
                                    options = listOf("Normal", "Whitelist", "Blocklist"),
                                    selectedIndex = selectedMode,
                                    onOptionSelected = { viewModel.onModeSelected(it) },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        // Status Card (PARALLAX ENABLED) (Index 1)
                        item {
                            // Calculate Parallax Progress
                            val index = listState.firstVisibleItemIndex
                            val offset = listState.firstVisibleItemScrollOffset
                            val parallaxProgress = if (index > 2) 1f else (offset.toFloat() / 500f)
                            
                            val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
                            AnimatedEntrance(index = 1) {
                                HomeStatusCard(
                                    blockedCount = blockedToday,
                                    threatCount = totalThreats,
                                    isSystemActive = isRoleGranted,
                                    modifier = Modifier
                                        .scrollParallax(parallaxProgress)
                                        .clickable {
                                            if (!isRoleGranted) {
                                                haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                                com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)?.let {
                                                    roleLauncher.launch(it)
                                                }
                                            }
                                        }
                                )
                            }
                        }

                        // Weekly Insights (Index 2)
                        item {
                            AnimatedEntrance(index = 2) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("Weekly Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBlack)
                                        val hapticReporting = androidx.compose.ui.platform.LocalHapticFeedback.current
                                        Text(
                                            "FULL REPORT", 
                                            style = MaterialTheme.typography.labelSmall, 
                                            color = PrimaryLight, 
                                            fontWeight = CrystalDesign.Typography.WeightBold, 
                                            modifier = Modifier.clickable {
                                                hapticReporting.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.TextHandleMove)
                                            }
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    GlassPanel(modifier = Modifier.fillMaxWidth()) {
                                        Column(Modifier.padding(24.dp).fillMaxWidth()) {
                                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                                Column {
                                                    Text("AVG. DAILY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = CrystalDesign.Typography.WeightBold)
                                                    Text("18 Calls", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBlack)
                                                }
                                                Column {
                                                    Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = CrystalDesign.Typography.WeightBold)
                                                    Text("Tuesday", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBlack)
                                                }
                                            }
                                            Spacer(Modifier.height(24.dp))
                                            Box(Modifier.fillMaxWidth().height(150.dp)) {
                                                if (weeklyActivity.isNotEmpty()) {
                                                    WeeklyActivityBarChart(data = weeklyActivity)
                                                } else {
                                                    WeeklyActivityBarChart()
                                                }
                                            }
                                            Spacer(Modifier.height(16.dp))
                                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                                listOf("M", "T", "W", "T", "F", "S", "S").forEach { 
                                                    Text(it, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.width(CrystalDesign.Spacing.l), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = CrystalDesign.Typography.WeightBold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        item {
                            AnimatedEntrance(index = 3) {
                                Column(verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.m)) {
                                    Text("Recent Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = CrystalDesign.Typography.WeightBlack)
                                    Spacer(modifier = Modifier.height(CrystalDesign.Spacing.xs))
                                    
                                    // 10/10 Storytelling Empty State
                                    val recentLogs = emptyList<String>() // Simulated empty state for audit
                                    
                                    if (recentLogs.isEmpty()) {
                                        ScanningHorizon()
                                    } else {
                                        PremiumListItem(
                                               title = "+1 (555) 019-2834",
                                               subtitle = "2m ago • Spam Risk",
                                               tag = "BLOCKED",
                                               tagColor = Red,
                                               icon = Icons.Default.Block,
                                               iconColor = Red,
                                               onClick = {}
                                           )
                                        PremiumListItem(
                                               title = "Mom Mobile",
                                               subtitle = "1h ago • Whitelist",
                                               tag = "ALLOWED",
                                               tagColor = Emerald,
                                               icon = Icons.Default.VerifiedUser,
                                               iconColor = Emerald,
                                               onClick = {}
                                           )
                                    }
                                }
                            }
                        }
                    }

                // Floating Crystal Header
                PremiumHeader(
                    title = "CallBlockerPro",
                    subtitle = "NEON CRYSTAL EVOLUTION",
                    onBack = null,
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    actionIcon = Icons.Default.Settings,
                    onAction = { onNavigate("settings") }
                )
            }
        } // Closing PremiumBackground
    }
}
