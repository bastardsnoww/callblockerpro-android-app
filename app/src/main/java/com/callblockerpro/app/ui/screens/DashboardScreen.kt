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
        containerColor = CrystalDesign.Colors.BackgroundDarkStitch,
        bottomBar = { BottomNavBar(currentRoute = "home", onNavigate = onNavigate) }
    ) { paddingValues ->
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 24.dp), // px-6
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    // 1. Stitch Toggle
                    item {
                        StitchToggle(
                            options = listOf("Normal", "Whitelist", "Blocklist"),
                            selectedIndex = selectedMode,
                            onOptionSelected = { index -> 
                                viewModel.onModeSelected(index) 
                            }
                        )
                    }

                    // 2. System Status (HomeStatusCard replacement)
                    item {
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
                                    viewModel.toggleSystemShield(true)
                                    android.widget.Toast.makeText(context, "System Shield Active", android.widget.Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
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
                        .padding(top = 8.dp, start = 24.dp, end = 24.dp), // Padding matches px-6
                    actionIcon = Icons.Default.Settings,
                    onAction = { onNavigate("settings") }
                )
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
    // Determine visuals based on specific mock data intent
    // Reference: 
    // 1. Spam (Red) -> +1 (555)...
    // 2. Unknown (Orange) -> Unknown Private
    // 3. Business (Slate) -> Telemarketers Inc.
    
    val isSpam = log.phoneNumber.contains("555")
    val isHidden = log.phoneNumber.contains("Unknown")
    val isBusiness = log.phoneNumber.contains("Telemarketers")
    
    // Fallback logic if not using mocks
    val isBlocked = log.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED
    val isAllowed = log.result == com.callblockerpro.app.domain.model.CallResult.ALLOWED

    val primaryColor = when {
        isSpam -> CrystalDesign.Colors.NeonRed
        isHidden -> CrystalDesign.Colors.NeonGold // Orange
        isBusiness -> Color.White // Slate/White per reference
        isAllowed -> CrystalDesign.Colors.NeonGreen
        else -> CrystalDesign.Colors.NeonRed
    }
    
    val icon = when {
        isSpam -> Icons.Default.PersonOff // person_off
        isHidden -> Icons.Default.Warning // call_quality -> closest is Warning or PhoneLocked. Using Warning for now or generic Phone.
        isBusiness -> Icons.Default.DomainDisabled // domain_disabled
        isAllowed -> Icons.Default.VerifiedUser
        else -> Icons.Default.Block
    }
    
    val badgeText = when {
        isSpam -> "Spam"
        isHidden -> "Hidden ID"
        isBusiness -> "Business"
        isAllowed -> "Whitelist"
        else -> "Blocked"
    }
    
    // HTML: group relative overflow-hidden rounded-2xl bg-surface border border-white/5 p-4 transition-all hover:bg-surface-lighter
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(16.dp) // matches p-4
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Icon Box
                // HTML: h-12 w-12 rounded-full bg-red-500/10 text-red-500 border border-red-500/20 shadow-glow
                Box(
                    modifier = Modifier
                        .size(48.dp) // h-12
                        .clip(CircleShape)
                        .background(primaryColor.copy(0.1f))
                        .border(1.dp, primaryColor.copy(0.2f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = if (isBusiness) com.callblockerpro.app.ui.theme.CrystalDesign.Colors.NeonRed else primaryColor, // Business icon is red in snapshot actually? Snapshot 3rd item is "Telemarketers Inc" with RED icon. 2nd item Unknown is ORANGE.
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                Spacer(Modifier.width(16.dp))
                
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                         Text(
                             log.phoneNumber, 
                             style = MaterialTheme.typography.titleSmall, 
                             fontWeight = FontWeight.Bold, 
                             color = Color.White
                         )
                         Spacer(Modifier.width(8.dp))
                         // Badge
                         // HTML: rounded bg-red-500/20 px-1.5 py-0.5 text-[10px] font-bold text-red-400 border border-red-500/20
                         val badgeColor = if (isBusiness) androidx.compose.ui.graphics.Color(0xFFcbd5e1) else primaryColor // Business badge is Slate in snapshot "Business"
                         val badgeBg = if (isBusiness) androidx.compose.ui.graphics.Color(0xFF334155) else primaryColor // slate-700
                         
                         Surface(
                            color = badgeBg.copy(if (isBusiness) 1f else 0.2f),
                            shape = RoundedCornerShape(4.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, if(isBusiness) Color.White.copy(0.1f) else badgeColor.copy(0.2f))
                         ) {
                             Text(
                                 text = badgeText,
                                 style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                                 fontWeight = FontWeight.Bold,
                                 color = if (isBusiness) androidx.compose.ui.graphics.Color(0xFFcbd5e1) else badgeColor,
                                 modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                             )
                         }
                    }
                    Spacer(Modifier.height(4.dp))
                    
                    // Subtitle: "Added 2 hours ago • Auto-detected"
                    val timeString = when {
                        isSpam -> "Added 2 hours ago"
                        isHidden -> "Added yesterday"
                        isBusiness -> "Added 3 days ago"
                        else -> "Just now"
                    }
                    Text(
                        "$timeString • ${log.reason ?: "Auto-blocked"}",
                        style = MaterialTheme.typography.labelSmall,
                        color = CrystalDesign.Colors.TextTertiary
                    )
                }
                
                // Trailing Menu
                Icon(
                    Icons.Default.MoreVert,
                    null,
                    tint = CrystalDesign.Colors.TextTertiary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

