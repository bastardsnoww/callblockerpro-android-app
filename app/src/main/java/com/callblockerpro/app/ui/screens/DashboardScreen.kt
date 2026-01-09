package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
                    val firstItemOffset by remember { derivedStateOf { listState.firstVisibleItemScrollOffset } }
                    val firstItemIndex by remember { derivedStateOf { listState.firstVisibleItemIndex } }

                    LazyColumn(
                        state = listState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = CrystalDesign.Spacing.l),
                        contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp),
                        verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.l) // 24dp
                    ) {
                        // Spacer for header
                        item { Spacer(Modifier.height(0.dp)) }

                        // Mode Selector (Index 0 for animation)
                        item {
                            AnimatedEntrance(index = 0) {
                                if (!isRoleGranted) {
                                    PremiumWarningCard(
                                        title = "System Role Required",
                                        message = "Set as default blocker to enable protection.",
                                        buttonText = "FIX",
                                        onClick = {
                                            com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)?.let {
                                                roleLauncher.launch(it)
                                            }
                                        }
                                    )
                                    Spacer(modifier = Modifier.height(24.dp))
                                }

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
                            val parallaxProgress = if (firstItemIndex > 2) 1f else (firstItemOffset.toFloat() / 500f)
                            
                            AnimatedEntrance(index = 1) {
                                HomeStatusCard(
                                    blockedCount = blockedToday,
                                    threatCount = totalThreats,
                                    modifier = Modifier.scrollParallax(parallaxProgress)
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
                                        Text("Weekly Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Black)
                                        Text("FULL REPORT", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = FontWeight.Bold, modifier = Modifier.clickable {})
                                    }
                                    Spacer(modifier = Modifier.height(20.dp))
                                    GlassPanel(modifier = Modifier.fillMaxWidth()) {
                                        Column(Modifier.padding(24.dp).fillMaxWidth()) {
                                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                                Column {
                                                    Text("AVG. DAILY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                                                    Text("18 Calls", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Black)
                                                }
                                                Column {
                                                    Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                                                    Text("Tuesday", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Black)
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
                                                    Text(it, style = MaterialTheme.typography.labelSmall, color = Color.White.copy(alpha = 0.6f), modifier = Modifier.width(24.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold)
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        // Recent Activity (Index 3)
                        item {
                            AnimatedEntrance(index = 3) {
                                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                    Text("Recent Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Black)
                                    Spacer(modifier = Modifier.height(8.dp))
                                    
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
