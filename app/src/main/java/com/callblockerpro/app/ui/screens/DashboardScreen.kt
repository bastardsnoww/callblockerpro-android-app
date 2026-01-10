package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.filled.MoreVert
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

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration

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
    val recentLogs by viewModel.recentLogs.collectAsState()

    var isRoleGranted by remember { 
        mutableStateOf(com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)) 
    }
    
    val roleLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
        onResult = { isRoleGranted = com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context) }
    )

    // REMOVED: Auto-trigger toast on state change to prevent spam on launch
    /*
    LaunchedEffect(selectedMode) { ... }
    */

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
                val listState = rememberLazyListState()
                val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
                val maxWidth = com.callblockerpro.app.ui.theme.maxContentWidth()
                val contentPadding = com.callblockerpro.app.ui.theme.adaptiveContentPadding()
                
                @OptIn(ExperimentalFoundationApi::class)
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null
                ) {
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
                        item { Spacer(Modifier.height(0.dp)) }

                        item {
                            AnimatedEntrance(index = 0) {
                                MetallicToggle(
                                    options = listOf("Normal", "Whitelist", "Blocklist"),
                                    selectedIndex = selectedMode,
                                    onOptionSelected = { index -> 
                                        viewModel.onModeSelected(index) 
                                        haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                        
                                        // MOVED: Trigger toast ONLY on user interaction
                                        val modeName = when(index) {
                                            0 -> "Normal Mode"
                                            1 -> "Whitelist Mode"
                                            else -> "Blocklist Mode"
                                        }
                                        android.widget.Toast.makeText(context, "$modeName Activated", android.widget.Toast.LENGTH_SHORT).show()
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }

                        item {
                            val index = listState.firstVisibleItemIndex
                            val offset = listState.firstVisibleItemScrollOffset
                            val parallaxProgress = if (index > 2) 1f else (offset.toFloat() / 500f)
                            
                            AnimatedEntrance(index = 1) {
                                HomeStatusCard(
                                    blockedCount = blockedToday,
                                    threatCount = totalThreats,
                                    isSystemActive = isRoleGranted,
                                    modifier = Modifier
                                        .scrollParallax(parallaxProgress)
                                        .clickable {
                                            haptic.performHapticFeedback(androidx.compose.ui.hapticfeedback.HapticFeedbackType.LongPress)
                                            if (!isRoleGranted) {
                                                com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)?.let {
                                                    roleLauncher.launch(it)
                                                }
                                            } else {
                                                viewModel.toggleSystemShield(true)
                                                android.widget.Toast.makeText(context, "System Shield is Active & Monitoring", android.widget.Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                )
                            }
                        }

                        item {
                            AnimatedEntrance(index = 2) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            "Weekly Activity", 
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.White, 
                                            fontWeight = FontWeight.Bold
                                        )
                                        IconButton(onClick = { onNavigate("logs") }) {
                                            Icon(
                                                imageVector = androidx.compose.material.icons.Icons.Default.MoreVert,
                                                contentDescription = "Options",
                                                tint = CrystalDesign.Colors.TextTertiary
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    GlassPanel(modifier = Modifier.fillMaxWidth()) {
                                        Column(Modifier.padding(24.dp).fillMaxWidth()) {
                                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                                Column {
                                                    Text(
                                                        "AVG. DAILY", 
                                                        style = MaterialTheme.typography.labelSmall, 
                                                        color = CrystalDesign.Colors.TextTertiary,
                                                        fontWeight = FontWeight.Bold,
                                                        letterSpacing = 0.5.sp
                                                    )
                                                    Text(
                                                        "18 Calls", 
                                                        style = MaterialTheme.typography.headlineSmall,
                                                        color = Color.White, 
                                                        fontWeight = FontWeight.Bold
                                                    )
                                                }
                                                Column {
                                                    Text(
                                                        "PEAK DAY", 
                                                        style = MaterialTheme.typography.labelSmall, 
                                                        color = CrystalDesign.Colors.TextTertiary,
                                                        fontWeight = FontWeight.Bold,
                                                        letterSpacing = 0.5.sp
                                                    )
                                                    Text(
                                                        "Tuesday", 
                                                        style = MaterialTheme.typography.headlineSmall,
                                                        color = Color.White, 
                                                        fontWeight = FontWeight.Bold
                                                    )
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
                                                    Text(
                                                        it, 
                                                        style = MaterialTheme.typography.labelSmall, 
                                                        color = CrystalDesign.Colors.TextSecondary,
                                                        modifier = Modifier.width(CrystalDesign.Spacing.l), 
                                                        textAlign = androidx.compose.ui.text.style.TextAlign.Center, 
                                                        fontWeight = FontWeight.Bold
                                                    )
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
                                    
                                    if (recentLogs.isEmpty()) {
                                        ScanningHorizon()
                                    } else {
                                        recentLogs.forEach { log ->
                                            val icon = when(log.result) {
                                                com.callblockerpro.app.domain.model.CallResult.BLOCKED -> Icons.Default.Block
                                                com.callblockerpro.app.domain.model.CallResult.ALLOWED -> Icons.Default.VerifiedUser
                                                else -> Icons.Default.Warning
                                            }
                                            val iconColor = when(log.result) {
                                                com.callblockerpro.app.domain.model.CallResult.BLOCKED -> Red
                                                com.callblockerpro.app.domain.model.CallResult.ALLOWED -> Emerald
                                                else -> CrystalDesign.Colors.NeonGold
                                            }
                                            
                                            PremiumListItem(
                                                   title = log.phoneNumber,
                                                   subtitle = "${log.result} • ${log.reason ?: "Unknown"}",
                                                   tag = log.result.name,
                                                   tagColor = iconColor,
                                                   icon = icon,
                                                   iconColor = iconColor,
                                                   onClick = { onNavigate("logs") }
                                               )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                
                PremiumHeader(
                    title = "CallBlockerPro",
                    subtitle = "NEON CRYSTAL",
                    onBack = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    actionIcon = Icons.Default.Settings,
                    onAction = { onNavigate("settings") }
                )
            }
        }
    }
}
