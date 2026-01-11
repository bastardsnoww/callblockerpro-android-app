package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.automirrored.filled.*
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
import com.callblockerpro.app.ui.theme.CrystalDesign

@Composable
fun SettingsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.SettingsViewModel = hiltViewModel()
) {
    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            title = { Text("Sign Out", style = MaterialTheme.typography.titleLarge) },
            text = { Text("Are you sure you want to sign out?", style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(onClick = { 
                    showLogoutDialog = false 
                    /* TODO: Logout Logic */ 
                }) {
                    Text("Sign Out", color = CrystalDesign.Colors.NeonRed)
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutDialog = false }) {
                    Text("Cancel", color = CrystalDesign.Colors.TextSecondary)
                }
            },
            containerColor = CrystalDesign.Colors.BackgroundSurface,
            titleContentColor = Color.White,
            textContentColor = CrystalDesign.Colors.TextSecondary
        )
    }

    Scaffold(
        bottomBar = { BottomNavBar(currentRoute = "settings", onNavigate = onNavigate) }
    ) { paddingValues ->
        StitchScreenWrapper {
        val searchQuery by viewModel.searchQuery.collectAsState()
        val blockUnknown by viewModel.blockUnknown.collectAsState()
        val scamProtection by viewModel.scamProtection.collectAsState()
        val notifications by viewModel.notifications.collectAsState()
        val faceId by viewModel.faceId.collectAsState()
        
        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = CrystalDesign.Spacing.l, end = CrystalDesign.Spacing.l),
                    verticalArrangement = Arrangement.spacedBy(CrystalDesign.Spacing.l)
                ) {
                    // Profile Card
                    item {
                        GlassPanel(
                            modifier = Modifier.fillMaxWidth(),
                            cornerRadius = 24.dp,
                            borderAlpha = 0.15f
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(20.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(60.dp)
                                        .clip(CircleShape)
                                        .background(Brush.linearGradient(listOf(Primary, PrimaryLight))),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("JD", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Black, color = Color.White)
                                }
                                Spacer(Modifier.width(16.dp))
                                Column(Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text("John Doe", style = MaterialTheme.typography.titleMedium, fontWeight = CrystalDesign.Typography.WeightBlack, color = Color.White)
                                        Spacer(Modifier.width(CrystalDesign.Spacing.xs))
                                        Box(Modifier.background(Primary.copy(0.2f), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                                            Text("PRO", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = CrystalDesign.Typography.WeightBlack, fontSize = 10.sp)
                                        }
                                    }
                                    Text("+1 (555) 012-3456", style = MaterialTheme.typography.bodySmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightMedium)
                                }
                                Icon(Icons.Default.ChevronRight, null, tint = Color.Gray.copy(0.5f))
                            }
                        }
                    }

                    // Search
                    item {
                        PremiumSearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.onSearchQueryChanged(it) },
                            placeholder = "Search features..."
                        )
                    }

                    // Preference Group
                    item {
                        SettingsGroup("Protection Preferences") {
                            SettingsToggleRow(
                                icon = Icons.Default.Block,
                                iconColor = CrystalDesign.Colors.NeonRed,
                                title = "Block Unknown Callers",
                                subtitle = "Only allow contacts",
                                checked = blockUnknown,
                                onCheckedChange = { viewModel.toggleBlockUnknown() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Warning,
                                iconColor = CrystalDesign.Colors.NeonGold,
                                title = "Scam Protection",
                                subtitle = "Strict filtering",
                                checked = scamProtection,
                                onCheckedChange = { viewModel.toggleScamProtection() }
                            )
                            SettingsLinkRow(
                                icon = Icons.Default.Schedule,
                                iconColor = CrystalDesign.Colors.NeonBlue,
                                title = "Auto-Schedule",
                                subtitle = "10:00 PM - 7:00 AM"
                            )
                        }
                    }
                    
                    // General Group
                    item {
                        SettingsGroup("General") {
                            SettingsToggleRow(
                                icon = Icons.Default.Notifications,
                                iconColor = CrystalDesign.Colors.NeonPurple,
                                title = "Notifications",
                                subtitle = null,
                                checked = notifications,
                                onCheckedChange = { viewModel.toggleNotifications() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Face,
                                iconColor = CrystalDesign.Colors.NeonGreen,
                                title = "Biometric Unlock",
                                subtitle = null,
                                checked = faceId,
                                onCheckedChange = { viewModel.toggleFaceId() }
                            )
                            SettingsLinkRow(
                                icon = Icons.Default.Language,
                                iconColor = CrystalDesign.Colors.TextTertiary,
                                title = "Language",
                                subtitle = null,
                                trailingText = "English"
                            )
                        }
                    }

                    // Support Group
                    item {
                        SettingsGroup("Support") {
                            SettingsLinkRow(
                                title = "Help Center", 
                                icon = null, 
                                iconColor = Color.Unspecified, 
                                trailingIcon = Icons.AutoMirrored.Filled.OpenInNew,
                                onClick = { uriHandler.openUri("https://example.com/help") }
                            )
                            SettingsLinkRow(title = "Report an Issue", icon = null, iconColor = Color.Unspecified, onClick = {})
                        }
                        Spacer(Modifier.height(CrystalDesign.Spacing.m))

                        // Destructive Action: Log Out
                        Button(
                            onClick = { showLogoutDialog = true },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CrystalDesign.Colors.NeonRed.copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(CrystalDesign.Glass.CornerRadiusSmall)
                        ) {
                            Icon(Icons.AutoMirrored.Filled.Logout, null, tint = CrystalDesign.Colors.NeonRed)
                            Spacer(Modifier.width(CrystalDesign.Spacing.xs))
                            Text("Sign Out", color = CrystalDesign.Colors.NeonRed, fontWeight = CrystalDesign.Typography.WeightBold)
                        }

                        Spacer(Modifier.height(CrystalDesign.Spacing.m))
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Version 4.2.0 (Build 302)", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, letterSpacing = 2.sp)
                        }
                    }
                }

                // Floating Crystal Header
                PremiumHeader(
                    title = "Settings",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp),
                    actionIcon = Icons.AutoMirrored.Filled.OpenInNew,
                    onAction = { /* Help */ }
                )
            }
            }
        }
    }
}
