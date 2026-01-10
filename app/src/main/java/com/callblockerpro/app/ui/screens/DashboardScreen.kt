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
    val isBlocked = log.result == com.callblockerpro.app.domain.model.CallResult.BLOCKED
    val isAllowed = log.result == com.callblockerpro.app.domain.model.CallResult.ALLOWED
    
    val bgHover = Color.White.copy(0.05f) // Simulation of hover state
    
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 8.dp) // Matches py-3 px-2
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                         if (isBlocked) CrystalDesign.Colors.NeonRed.copy(0.1f) 
                         else if (isAllowed) CrystalDesign.Colors.NeonGreen.copy(0.1f)
                         else CrystalDesign.Colors.NeonGold.copy(0.1f)
                    )
                    .border(1.dp, 
                        if (isBlocked) CrystalDesign.Colors.NeonRed.copy(0.2f) 
                         else if (isAllowed) CrystalDesign.Colors.NeonGreen.copy(0.2f)
                         else CrystalDesign.Colors.NeonGold.copy(0.2f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    if (isBlocked) Icons.Default.Block else if (isAllowed) Icons.Default.VerifiedUser else Icons.Default.Warning,
                    null,
                    tint = if (isBlocked) CrystalDesign.Colors.NeonRed else if (isAllowed) CrystalDesign.Colors.NeonGreen else CrystalDesign.Colors.NeonGold,
                    modifier = Modifier.size(20.dp)
                )
                // Red Dot for Blocked
                if (isBlocked) {
                    Box(
                         Modifier
                            .align(Alignment.TopEnd)
                            .offset(x = 2.dp, y = (-2).dp)
                            .size(10.dp)
                            .background(CrystalDesign.Colors.BackgroundDarkStitch, CircleShape)
                            .padding(2.dp)
                    ) {
                         Box(Modifier.fillMaxSize().background(CrystalDesign.Colors.NeonRed, CircleShape))
                    }
                }
            }
            
            Spacer(Modifier.width(16.dp))
            
            Column(Modifier.weight(1f)) {
                 Text(
                     log.phoneNumber, 
                     style = MaterialTheme.typography.bodyMedium, 
                     fontWeight = FontWeight.Bold, 
                     color = Color.White
                 )
                 Text(
                     if (isBlocked) "Spam Risk • Auto-Blocked" else if (isAllowed) "Whitelist • Allowed" else "Unknown",
                     style = MaterialTheme.typography.labelSmall,
                     color = if (isBlocked) CrystalDesign.Colors.NeonRed.copy(0.8f) else CrystalDesign.Colors.TextTertiary
                 )
            }
            
            Spacer(Modifier.width(8.dp))
            
            Surface(
                color = Color.White.copy(0.05f),
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    "2m", 
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = CrystalDesign.Colors.TextTertiary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
        Spacer(Modifier.height(12.dp))
        Divider(color = Color.White.copy(0.05f))
    }
}

