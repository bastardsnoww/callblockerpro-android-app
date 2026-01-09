package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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

@Composable
fun SettingsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.SettingsViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = "settings", onNavigate = onNavigate) }
    ) { paddingValues ->
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
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
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
                                        Text("John Doe", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                                        Spacer(Modifier.width(8.dp))
                                        Box(Modifier.background(Primary.copy(0.2f), RoundedCornerShape(6.dp)).padding(horizontal = 8.dp, vertical = 2.dp)) {
                                            Text("PRO", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = FontWeight.Black, fontSize = 10.sp)
                                        }
                                    }
                                    Text("+1 (555) 012-3456", style = MaterialTheme.typography.bodySmall, color = Color.Gray, fontWeight = FontWeight.Medium)
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
                                iconColor = Color(0xFFEF4444),
                                title = "Block Unknown Callers",
                                subtitle = "Only allow contacts",
                                checked = blockUnknown,
                                onCheckedChange = { viewModel.toggleBlockUnknown() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Warning,
                                iconColor = Color(0xFFF59E0B),
                                title = "Scam Protection",
                                subtitle = "Aggressive filtering",
                                checked = scamProtection,
                                onCheckedChange = { viewModel.toggleScamProtection() }
                            )
                            SettingsLinkRow(
                                icon = Icons.Default.Schedule,
                                iconColor = Color(0xFF3B82F6),
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
                                iconColor = Color(0xFFA855F7),
                                title = "Notifications",
                                subtitle = null,
                                checked = notifications,
                                onCheckedChange = { viewModel.toggleNotifications() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Face,
                                iconColor = Emerald,
                                title = "FaceID Unlock",
                                subtitle = null,
                                checked = faceId,
                                onCheckedChange = { viewModel.toggleFaceId() }
                            )
                            SettingsLinkRow(
                                icon = Icons.Default.Language,
                                iconColor = Color.Gray,
                                title = "Language",
                                subtitle = null,
                                trailingText = "English"
                            )
                        }
                    }

                    // Support Group
                    item {
                        SettingsGroup("Support") {
                            SettingsLinkRow(title = "Help Center", icon = null, iconColor = Color.Unspecified, trailingIcon = Icons.Default.OpenInNew)
                            SettingsLinkRow(title = "Report an Issue", icon = null, iconColor = Color.Unspecified)
                        }
                        Spacer(Modifier.height(8.dp))

                        // Destructive Action: Log Out
                        Button(
                            onClick = { /* TODO: Logout */ },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f)),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Icon(Icons.Default.Logout, null, tint = Color(0xFFEF4444))
                            Spacer(Modifier.width(8.dp))
                            Text("Log Out", color = Color(0xFFEF4444), fontWeight = FontWeight.Bold)
                        }

                        Spacer(Modifier.height(16.dp))
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Version 4.2.0 (Build 302)", style = MaterialTheme.typography.labelSmall, color = Color.Gray, letterSpacing = 2.sp)
                        }
                    }
                }

                // Floating Crystal Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Brush.verticalGradient(listOf(BackgroundDark, Color.Transparent)))
                        .padding(horizontal = 24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    GlassPanel(modifier = Modifier.fillMaxWidth().height(64.dp), cornerRadius = 20.dp) {
                        Row(Modifier.fillMaxSize().padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                            Text("Security Settings", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                            IconButton(onClick = { /* Help */ }, modifier = Modifier.background(Color.White.copy(0.05f), CircleShape).size(40.dp)) {
                                Icon(Icons.Default.OpenInNew, null, tint = Color.White, modifier = Modifier.size(18.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}
