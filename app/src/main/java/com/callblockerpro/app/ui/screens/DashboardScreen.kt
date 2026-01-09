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
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = "home", onNavigate = onNavigate) }
    ) { paddingValues ->
        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(28.dp)
                ) {
                    // Spacer for header
                    item { Spacer(Modifier.height(0.dp)) }

                    // Mode Selector
                    item {
                        if (!isRoleGranted) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(24.dp))
                                    .background(Red.copy(0.1f))
                                    .border(1.dp, Red.copy(0.3f), RoundedCornerShape(24.dp))
                            ) {
                                Row(
                                    Modifier.padding(20.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                                ) {
                                    Icon(Icons.Default.Warning, null, tint = Red)
                                    Column(Modifier.weight(1f)) {
                                        Text("System Role Required", style = MaterialTheme.typography.titleSmall, color = Color.White, fontWeight = FontWeight.Bold)
                                        Text("Set as default blocker to enable protection.", style = MaterialTheme.typography.labelSmall, color = Color.White.copy(0.7f))
                                    }
                                    Button(
                                        onClick = { 
                                            com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)?.let {
                                                roleLauncher.launch(it)
                                            }
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Red),
                                        shape = RoundedCornerShape(12.dp),
                                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                                    ) {
                                        Text("FIX", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black)
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        MetallicToggle(
                            options = listOf("Normal", "Whitelist", "Blocklist"),
                            selectedIndex = selectedMode,
                            onOptionSelected = { viewModel.onModeSelected(it) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    // Status Card
                    item {
                        HomeStatusCard(
                            blockedCount = blockedToday,
                            threatCount = totalThreats
                        )
                    }

                    // Weekly Insights
                    item {
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
                            GlassPanel(modifier = Modifier.fillMaxWidth().height(260.dp)) {
                                Column(Modifier.padding(24.dp).fillMaxSize()) {
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
                                     Box(Modifier.weight(1f).fillMaxWidth()) {
                                         if (weeklyActivity.isNotEmpty()) {
                                             WeeklyActivityBarChart(data = weeklyActivity)
                                         } else {
                                             WeeklyActivityBarChart()
                                         }
                                     }
                                     Spacer(Modifier.height(16.dp))
                                     Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                         listOf("M", "T", "W", "T", "F", "S", "S").forEach { 
                                             Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray.copy(alpha = 0.6f), modifier = Modifier.width(24.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center, fontWeight = FontWeight.Bold)
                                         }
                                     }
                                }
                            }
                        }
                    }

                    // Recent Activity
                    item {
                        Text("Recent Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Black)
                        Spacer(modifier = Modifier.height(20.dp))
                        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
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

                // Floating Crystal Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(BackgroundDark, Color.Transparent)
                            )
                        )
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GlassPanel(
                        modifier = Modifier.fillMaxWidth().height(64.dp),
                        cornerRadius = 20.dp
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "CallBlockerPro",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Text(
                                    text = "NEON CRYSTAL EVOLUTION",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryLight,
                                    fontSize = 8.sp,
                                    letterSpacing = 1.sp
                                )
                            }
                            IconButton(
                                onClick = { onNavigate("settings") },
                                modifier = Modifier
                                    .background(Color.White.copy(alpha = 0.05f), CircleShape)
                                    .size(40.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Settings",
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                    }
                }
            }
        } // Closing PremiumBackground
    }
}
