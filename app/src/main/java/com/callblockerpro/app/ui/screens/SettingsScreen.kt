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
import androidx.compose.ui.res.stringResource
import com.callblockerpro.app.R

@Composable
fun SettingsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.SettingsViewModel = hiltViewModel()
) {
    val uriHandler = androidx.compose.ui.platform.LocalUriHandler.current
    // Removed Logout Dialog


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
                    // Local Data Management Header (Replaces Profile)
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
                                        .background(Brush.linearGradient(listOf(Emerald, Primary))),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Shield, contentDescription = null, tint = Color.White, modifier = Modifier.size(32.dp))
                                }
                                Spacer(Modifier.width(16.dp))
                                Column(Modifier.weight(1f)) {
                                    Text(stringResource(R.string.settings_device_protected), style = MaterialTheme.typography.titleMedium, fontWeight = CrystalDesign.Typography.WeightBlack, color = Color.White)
                                    Spacer(Modifier.height(4.dp))
                                    Text(stringResource(R.string.settings_device_local_desc), style = MaterialTheme.typography.bodySmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = CrystalDesign.Typography.WeightMedium)
                                }
                            }
                        }
                    }

                    // Search
                    item {
                        PremiumSearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.onSearchQueryChanged(it) },
                            placeholder = stringResource(R.string.settings_search_placeholder)
                        )
                    }

                    // Preference Group
                    item {
                        SettingsGroup(stringResource(R.string.settings_group_protection)) {
                            SettingsToggleRow(
                                icon = Icons.Default.Block,
                                iconColor = CrystalDesign.Colors.NeonRed,
                                title = stringResource(R.string.settings_block_unknown),
                                subtitle = stringResource(R.string.settings_block_unknown_desc),
                                checked = blockUnknown,
                                onCheckedChange = { viewModel.toggleBlockUnknown() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Warning,
                                iconColor = CrystalDesign.Colors.NeonGold,
                                title = stringResource(R.string.settings_scam_shield),
                                subtitle = stringResource(R.string.settings_scam_shield_desc),
                                checked = scamProtection,
                                onCheckedChange = { viewModel.toggleScamProtection() }
                            )
                            SettingsLinkRow(
                                icon = Icons.Default.Schedule,
                                iconColor = CrystalDesign.Colors.NeonBlue,
                                title = stringResource(R.string.settings_auto_schedule),
                                subtitle = stringResource(R.string.settings_auto_schedule_desc),
                                onClick = { onNavigate("schedule") }
                            )
                        }
                    }
                    
                    // General Group
                    item {
                        SettingsGroup(stringResource(R.string.settings_group_general)) {
                            SettingsToggleRow(
                                icon = Icons.Default.Notifications,
                                iconColor = CrystalDesign.Colors.NeonPurple,
                                title = stringResource(R.string.settings_notifications),
                                subtitle = null,
                                checked = notifications,
                                onCheckedChange = { viewModel.toggleNotifications() }
                            )
                            SettingsToggleRow(
                                icon = Icons.Default.Face,
                                iconColor = CrystalDesign.Colors.NeonGreen,
                                title = stringResource(R.string.settings_biometric),
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
                        SettingsGroup(stringResource(R.string.settings_group_support)) {
                            SettingsLinkRow(
                                title = stringResource(R.string.settings_help_center), 
                                icon = null, 
                                iconColor = Color.Unspecified, 
                                trailingIcon = Icons.AutoMirrored.Filled.OpenInNew,
                                onClick = { uriHandler.openUri("https://github.com/bastardsnoww/callblockerpro-android-app/wiki") } // Real URL
                            )
                            SettingsLinkRow(
                                title = stringResource(R.string.settings_privacy_policy), 
                                icon = null, 
                                iconColor = Color.Unspecified, 
                                trailingIcon = Icons.AutoMirrored.Filled.OpenInNew,
                                onClick = { uriHandler.openUri("https://github.com/bastardsnoww/callblockerpro-android-app/blob/main/PRIVACY.md") } // Real URL
                            )
                            SettingsLinkRow(title = stringResource(R.string.settings_report_issue), icon = null, iconColor = Color.Unspecified, onClick = {})
                        }
                        Spacer(Modifier.height(CrystalDesign.Spacing.m))

                        // Removed Destructive Sign Out (Not applicable for local-first app)


                        Spacer(Modifier.height(CrystalDesign.Spacing.m))
                        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(stringResource(R.string.version_template, "4.2.0", 302), style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, letterSpacing = 2.sp)
                        }
                    }
                }

                // Floating Crystal Header
                PremiumHeader(
                    title = stringResource(R.string.settings_header),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .statusBarsPadding()
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
