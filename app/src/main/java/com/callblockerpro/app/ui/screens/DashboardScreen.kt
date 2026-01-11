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
import androidx.compose.material.icons.filled.DomainDisabled
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.automirrored.filled.CallMissed
import androidx.compose.material.icons.automirrored.filled.CallMade
import androidx.compose.material.icons.automirrored.filled.CallReceived
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.graphicsLayer
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

    androidx.compose.runtime.DisposableEffect(Unit) {
        isRoleGranted = com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)
        onDispose {}
    }

    Scaffold(
        bottomBar = { BottomNavBar(currentRoute = "home", onNavigate = onNavigate) }
    ) { paddingValues ->
        StitchScreenWrapper {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp), // px-6
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // 1. Stitch Toggle
                    item {
                        StitchToggle(
                            options = listOf("Standard", "Relaxed", "Strict"),
                            selectedIndex = selectedMode,
                            onOptionSelected = { index -> 
                                viewModel.onModeSelected(index) 
                            }
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = when(selectedMode) {
                                0 -> "Standard: Blocks known spam & user blocklist"
                                1 -> "Relaxed: Allows contacts & allowlist"
                                else -> "Strict: Blocks everything except allowlist"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = CrystalDesign.Colors.TextTertiary,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    // 2. System Status (HomeStatusCard replacement)
                    item {
                       var showStatusDialog by remember { mutableStateOf(false) }

                       if (showStatusDialog) {
                           AlertDialog(
                               onDismissRequest = { showStatusDialog = false },
                               title = { Text("System Status", color = Color.White) },
                               text = { 
                                   Column {
                                       Text("Gradient AI Engine: Active", color = Color.White.copy(0.8f))
                                       Spacer(Modifier.height(4.dp))
                                       Text("Database Version: v2.4 (Latest)", color = Color.White.copy(0.6f))
                                       Spacer(Modifier.height(4.dp))
                                       Text("Protection Level: High", color = Emerald)
                                   }
                               },
                               confirmButton = {
                                   TextButton(onClick = { showStatusDialog = false }) {
                                       Text("OK", color = Primary)
                                   }
                               },
                               containerColor = CrystalDesign.Colors.SurfaceStitch,
                               textContentColor = Color.White
                           )
                       }

                       val lastBlocked by viewModel.lastBlockedCall.collectAsState()

                       HomeStatusCard(
                            blockedCount = blockedToday,
                            threatCount = totalThreats,
                            isSystemActive = isRoleGranted,
                            modifier = Modifier.clickable {
                                if (!isRoleGranted) {
                                    com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)?.let {
                                        roleLauncher.launch(it)
                                    }
                                } else {
                                    showStatusDialog = true
                                }
                            }
                        )
                        
                        if (lastBlocked != null) {
                            Spacer(modifier = Modifier.height(16.dp))
                            // Small "Toast" Card for Last Blocked
                            Surface(
                                color = CrystalDesign.Colors.SurfaceStitch,
                                shape = RoundedCornerShape(12.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CrystalDesign.Colors.NeonRed.copy(0.2f)),
                                modifier = Modifier.fillMaxWidth().clickable { onNavigate("logs") }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Block, null, tint = CrystalDesign.Colors.NeonRed, modifier = Modifier.size(20.dp))
                                    Spacer(Modifier.width(12.dp))
                                    Column {
                                        Text("LAST BLOCKED CALL", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.NeonRed, fontWeight = FontWeight.Bold)
                                        Text(lastBlocked?.phoneNumber ?: "Unknown", style = MaterialTheme.typography.bodyMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }

                    // 3. Weekly Activity
                    item {
                         Column {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Weekly Insights", 
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.White, 
                                    fontWeight = FontWeight.Bold
                                )
                                Surface(
                                    color = CrystalDesign.Colors.PrimaryStitch.copy(0.1f),
                                    shape = RoundedCornerShape(50),
                                    border = androidx.compose.foundation.BorderStroke(1.dp, CrystalDesign.Colors.PrimaryStitch.copy(0.2f))
                                ) {
                                    Text(
                                        "FULL REPORT",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = CrystalDesign.Colors.PrimaryLightStitch,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Using existing chart but wrapped in Stitch container
                            Surface(
                                color = CrystalDesign.Colors.SurfaceStitch,
                                shape = RoundedCornerShape(24.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
                                shadowElevation = 4.dp
                            ) {
                                Column(Modifier.padding(24.dp)) {
                                     Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                        Column {
                                            Text("AVG. DAILY", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = FontWeight.Bold)
                                            Text("18 Calls", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                        Column {
                                            Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = FontWeight.Bold)
                                            Text("Tuesday", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold)
                                        }
                                     }
                                     Spacer(Modifier.height(24.dp))
                                     Box(Modifier.height(120.dp).fillMaxWidth()) {
                                         WeeklyActivityBarChart(data = weeklyActivity)
                                     }
                                }
                            }
                        }
                    }

                    // 4. Recent Activity
                    item {
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                            Text("Recent Activity", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
                            
                            if (recentLogs.isEmpty()) {
                                ScanningHorizon()
                            } else {
                                Column {
                                    recentLogs.forEach { log ->
                                        StitchRecentItem(log = log, onClick = { onNavigate("logs") })
                                    }
                                }
                            }
                        }
                    }
                }
                
                // Header Positioned Absolutely
                PremiumHeader(
                    title = "CallBlocker", // Pro string handled in component
                    subtitle = "Premium Protection",
                    onBack = null,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp),
                    actionIcon = null,
                    onAction = null
                )
            }
        }
    }
}

@Composable
fun StitchToggle(options: List<String>, selectedIndex: Int, onOptionSelected: (Int) -> Unit) {
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
        modifier = Modifier.fillMaxWidth().height(48.dp) // h-10 equivalent-ish + padding
    ) {
        Box(Modifier.padding(4.dp)) { // p-1
            Row(Modifier.fillMaxSize()) {
                options.forEachIndexed { index, option ->
                    val isSelected = index == selectedIndex
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(12.dp))
                            .clickable { onOptionSelected(index) }
                            .background(if (isSelected) CrystalDesign.Colors.PrimaryStitch else Color.Transparent),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) Color.White else CrystalDesign.Colors.TextTertiary.copy(0.7f)
                        )
                    }
                }
            }
        }
    }
}



@Composable
fun StitchRecentItem(log: com.callblockerpro.app.domain.model.CallLogEntry, onClick: () -> Unit) {
    // Determine visuals based on specific mock data
    val isSpam = log.phoneNumber.contains("555")
    val isHidden = log.phoneNumber.equals("Unknown Caller")
    val isJohn = log.phoneNumber.contains("John")
    val isSarah = log.phoneNumber.contains("Sarah")
    
    // Logic mapping from Mock Data
    val isBlocked = log.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED
    val isAllowed = log.result == com.callblockerpro.app.domain.model.CallResult.ALLOWED
    val isMissed = log.result == com.callblockerpro.app.domain.model.CallResult.MISSED
    val isOutgoing = log.result == com.callblockerpro.app.domain.model.CallResult.OUTGOING

    // Color Logic
    val primaryColor = when {
        isBlocked && isSpam -> CrystalDesign.Colors.NeonRed
        isMissed -> CrystalDesign.Colors.NeonRed // Reference uses Red for Missed Call Icon
        isAllowed || isOutgoing -> CrystalDesign.Colors.Primary // John/Sarah use Gradient/Primary/Indigo
        else -> CrystalDesign.Colors.NeonRed
    }
    
    // Vertical Strip Logic (Only for Blocked/Spam)
    val showStrip = isBlocked
    
    // Icon Logic
    val icon = when {
        isSpam -> Icons.Default.Block // block
        isMissed -> Icons.AutoMirrored.Filled.CallMissed // call_missed
        isOutgoing -> Icons.AutoMirrored.Filled.CallMade // call_made
        isAllowed -> Icons.AutoMirrored.Filled.CallReceived // call_received
        else -> Icons.Default.Block
    }
    
    // Badge Logic
    val showBadge = isBlocked || isMissed
    val badgeText = if (isSpam) "SPAM RISK" else if (isMissed) "MISSED CALL" else "BLOCKED"
    
    // HTML: group relative overflow-hidden rounded-2xl bg-surface border border-white/5 p-4 transition-all hover:bg-surface-lighter
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Box {
             // [STITCH] Vertical Strip for Blocked Items
             // [STITCH] Vertical Strip for Item Status
             // Red for Blocked/Missed, Green for Allowed/Outgoing
             val showStrip = true // Always show strip now per user request "same way"
             val stripColorVal = if (isBlocked || isMissed || isSpam) CrystalDesign.Colors.NeonRed else Emerald

             if (showStrip) {
                 Box(
                     modifier = Modifier
                         .width(4.dp)
                         .fillMaxHeight()
                         .align(Alignment.CenterStart)
                         .background(stripColorVal)
                 )
             }
             
             Row(
                modifier = Modifier.padding(16.dp), // matches p-4
                verticalAlignment = Alignment.CenterVertically
             ) {
                // Icon Box / Avatar
                if (isJohn || isSarah) {
                    // Avatar Placeholder
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(16.dp)) // rounded-2xl
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        if (isJohn) androidx.compose.ui.graphics.Color(0xFF6366f1) else CrystalDesign.Colors.SurfaceStitch, // indigo-500 or surface-lighter
                                        if (isJohn) CrystalDesign.Colors.Primary else CrystalDesign.Colors.SurfaceStitch
                                    )
                                )
                            )
                            .border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (isSarah) {
                             // Image placeholder would go here, using Initials for now
                             Text("SM", fontWeight = FontWeight.Bold, color = Color.White.copy(0.7f))
                        } else {
                             Text("JD", fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                } else {
                    // Standard Icon Box
                    Box(
                        modifier = Modifier
                            .size(48.dp) // h-12
                            .clip(RoundedCornerShape(16.dp)) // rounded-2xl
                            .background(if (isMissed) CrystalDesign.Colors.SurfaceStitch else primaryColor.copy(0.1f)) // Missed is surface-lighter in ref
                            .border(1.dp, if (isMissed) Color.White.copy(0.05f) else primaryColor.copy(0.2f), RoundedCornerShape(16.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = if (isMissed) CrystalDesign.Colors.NeonRed else primaryColor, // Missed icon is Red
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(Modifier.width(16.dp))
                
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         Text(
                             log.phoneNumber, 
                             style = MaterialTheme.typography.titleSmall, 
                             fontWeight = FontWeight.Bold, 
                             color = if (isMissed) Color.White.copy(0.9f) else Color.White
                         )
                         
                         if (isSpam) {
                             Spacer(Modifier.width(8.dp))
                             // Spam Badge
                            Surface(
                                color = CrystalDesign.Colors.NeonRed.copy(0.1f),
                                shape = RoundedCornerShape(4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CrystalDesign.Colors.NeonRed.copy(0.1f))
                            ) {
                                Text(
                                    text = "SPAM RISK",
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                    fontWeight = FontWeight.Bold,
                                    color = CrystalDesign.Colors.NeonRed,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                         }
                    }
                    Spacer(Modifier.height(4.dp))
                    
                    // Subtitle logic
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isOutgoing || isAllowed || isMissed) {
                            // "Mobile • 5m 23s" or icon + text
                            if (isOutgoing) {
                                Icon(Icons.AutoMirrored.Filled.CallMade, null, tint = Color.Gray, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                            } else if (isAllowed) {
                                Icon(Icons.AutoMirrored.Filled.CallReceived, null, tint = Emerald, modifier = Modifier.size(12.dp).graphicsLayer { rotationZ = 180f })
                                Spacer(Modifier.width(4.dp))
                            } else if (isMissed) {
                                Icon(Icons.AutoMirrored.Filled.CallMissed, null, tint = CrystalDesign.Colors.NeonRed, modifier = Modifier.size(12.dp))
                                Spacer(Modifier.width(4.dp))
                            }
                            
                            Text(
                                log.reason ?: "",
                                style = MaterialTheme.typography.labelSmall,
                                color = if (isMissed) CrystalDesign.Colors.NeonRed else CrystalDesign.Colors.TextTertiary
                            )
                        } else {
                            // Spam timestamp
                            Text(
                                "10:42 AM", // Hardcoded for spam mock match
                                style = MaterialTheme.typography.labelSmall,
                                color = CrystalDesign.Colors.TextTertiary
                            )
                        }
                        
                        if (isBlocked) {
                             Spacer(Modifier.width(4.dp))
                             Text("• Auto-Blocked", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary)
                        }
                    }
                }
                
                // Trailing Action Button
                if (isAllowed || isOutgoing) {
                    // Green Call Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Emerald.copy(0.1f))
                            .border(1.dp, Emerald.copy(0.2f), CircleShape)
                            .clickable { /* Call */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Call, null, tint = Emerald, modifier = Modifier.size(16.dp))
                    }
                } else {
                    // Info Button
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(0.05f))
                            .clickable { /* Info */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Info, null, tint = CrystalDesign.Colors.TextTertiary, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}


