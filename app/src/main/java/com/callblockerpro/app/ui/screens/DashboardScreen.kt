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
import com.callblockerpro.app.ui.theme.PrimaryLight

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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),

            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Header
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "CallBlockerPro",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                        Text(
                            text = "PREMIUM PROTECTION",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            letterSpacing = 2.sp
                        )
                    }
                    IconButton(
                        onClick = { onNavigate("settings") },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.1f), CircleShape)
                            .size(48.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.White
                        )
                    }
                }
            }
            
            // Mode Selector
            item {
                if (!isRoleGranted) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFEF4444).copy(0.1f),
                                        Color(0xFFEF4444).copy(0.05f)
                                    )
                                )
                            )
                            .border(
                                1.dp,
                                Brush.linearGradient(
                                    listOf(
                                        Color(0xFFEF4444).copy(0.5f),
                                        Color(0xFFEF4444).copy(0.2f)
                                    )
                                ),
                                RoundedCornerShape(24.dp)
                            )
                    ) {
                        Row(
                            Modifier.padding(20.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Icon(Icons.Default.Warning, null, tint = Color(0xFFEF4444))
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
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF4444)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Text("FIX", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
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

            // Status Card (Variant 5 Style)
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
                        Text("Weekly Insights", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                        Text("FULL REPORT", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    GlassPanel(modifier = Modifier.fillMaxWidth().height(240.dp)) {
                        Column(Modifier.padding(24.dp).fillMaxSize()) {
                             Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(32.dp)) {
                                 Column {
                                     Text("AVG. DAILY", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                     Text("18 Calls", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                                 }
                                 Column {
                                     Text("PEAK DAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                     Text("Tuesday", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
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
                             Spacer(Modifier.height(12.dp))
                             Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                 listOf("M", "T", "W", "T", "F", "S", "S").forEach { 
                                     Text(it, style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.width(20.dp), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                                 }
                             }
                        }
                    }
                }
            }

            // Recent Activity
            item {
                Text("Recent Activity", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                     PremiumListItem(
                            title = "+1 (555) 019-2834",
                            subtitle = "2m ago • Spam Risk",
                            tag = "BLOCKED",
                            tagColor = Color(0xFFEF4444),
                            icon = Icons.Default.Block,
                            iconColor = Color(0xFFEF4444),
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
                     PremiumListItem(
                            title = "Unknown Caller",
                            subtitle = "3h ago • Telemarketing",
                            tag = "SPAM",
                            tagColor = Color(0xFFF59E0B), // Amber for Spam
                            icon = Icons.Default.Warning,
                            iconColor = Color(0xFFF59E0B),
                            onClick = {}
                        )
                }
            }
            
            // Removed Spacer as contentPadding handles this now
        }
        } // Closing PremiumBackground
    }
}
