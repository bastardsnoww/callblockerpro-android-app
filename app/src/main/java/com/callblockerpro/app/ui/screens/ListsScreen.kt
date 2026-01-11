package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.DomainDisabled
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
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
import com.callblockerpro.app.ui.viewmodel.ListItem
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

@Composable
fun ListsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.ListsViewModel = hiltViewModel()
) {
    Scaffold(
        bottomBar = { BottomNavBar(currentRoute = "lists", onNavigate = onNavigate) }
    ) { paddingValues ->
        StitchScreenWrapper {
        val listType by viewModel.listType.collectAsState(initial = 1)
        val searchQuery by viewModel.searchQuery.collectAsState()
        val currentItems by viewModel.currentItems.collectAsState(initial = emptyList())

        PremiumBackground {
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    // Mode Selector
                    item {
                        StitchListToggle(
                            selectedIndex = listType,
                            onOptionSelected = { viewModel.onListTypeChanged(it) }
                        )
                    }

                    // Search
                    item {
                        PremiumSearchBar(
                            query = searchQuery,
                            onQueryChange = { viewModel.onSearchQueryChanged(it) },
                            placeholder = if (listType == 1) "Search blocklist..." else "Search whitelist...",
                            onFilterClick = {}
                        )
                    }

                    // Stats Section
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            GlassPanel(modifier = Modifier.weight(1f).height(110.dp)) {
                                Column(Modifier.padding(20.dp).align(Alignment.BottomStart)) {
                                    Text("PROTECTED", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                                    Text("428", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Black)
                                }
                                Icon(Icons.Default.Shield, null, tint = Primary.copy(0.1f), modifier = Modifier.size(80.dp).align(Alignment.TopEnd).offset(20.dp, (-20).dp))
                            }
                            GlassPanel(modifier = Modifier.weight(1f).height(110.dp)) {
                                Column(Modifier.padding(20.dp).align(Alignment.BottomStart)) {
                                    Text("DATABASE", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                                    Text("v2.4", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Black)
                                }
                                Icon(Icons.Default.CloudSync, null, tint = Primary.copy(0.1f), modifier = Modifier.size(80.dp).align(Alignment.TopEnd).offset(20.dp, (-20).dp))
                            }
                        }
                    }

                    // List Header
                    if (currentItems.isNotEmpty()) {
                        item {
                            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                                Text("NUMBERS", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                                Text("SORT BY DATE", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = FontWeight.Bold)
                            }
                        }
                    }

                    // Items
                    if (currentItems.isEmpty()) {
                        item {
                            AnimatedEntrance(index = 3) {
                                PremiumEmptyState(
                                    icon = Icons.Default.Shield,
                                    title = "List Empty",
                                    message = "Protect your peace.\nAdd your first number now.",
                                    actionLabel = "Add Number",
                                    onActionClick = { onNavigate("add") }
                                )
                            }
                        }
                    } else {
                        itemsIndexed(currentItems) { index, listItem: ListItem ->
                             // Ensure compatibility with ViewModel items
                            AnimatedEntrance(index = index + 3) {
                                PremiumListItem(
                                    title = listItem.title,
                                    subtitle = listItem.subtitle,
                                    tag = if (listType == 1) "BLOCKED" else "ALLOWED",
                                    tagColor = listItem.color,
                                    icon = if (listItem.subtitle == "Business") Icons.Default.DomainDisabled else Icons.Default.PersonOff,
                                    iconColor = listItem.color,
                                    onClick = {}
                                )
                            }
                        }
                    }
                }

                // Floating Crystal Header
                PremiumHeader(
                    title = "Protection Lists",
                    onBack = null, // Root screen, no back
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    actionIcon = Icons.Default.Add,
                    onAction = { onNavigate("add") }
                )
            }
        }
        }
    }
}

@Composable
fun StitchListToggle(selectedIndex: Int, onOptionSelected: (Int) -> Unit) {
    // HTML ref: p-1.5 rounded-2xl bg-surface border border-white/5 shadow-inner
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
        modifier = Modifier.fillMaxWidth().height(56.dp)
    ) {
        Box(Modifier.padding(6.dp)) { // p-1.5 -> 6.dp
            Row(Modifier.fillMaxSize()) {
                // Option 0: Whitelist (Code uses 0 for whitelist? No, ViewModel defaults to 1. 
                // Let's check ViewModel usage: 0=Whitelist, 1=Blocklist usually? 
                // Wait, in ListsViewModel: listType 1=Blocklist usually.
                // Let's look at previous call: options = listOf("Whitelist", "Blocklist") -> index 0=Whitelist, 1=Blocklist.
                // Reference HTML: Blocklist is first input checked. "value=blocklist".
                // I will maintain the existing ViewModel logic: 0 -> Whitelist, 1 -> Blocklist (based on previous listOf order).
                // BUT, if I want to match the visual order of the snapshot (Blocklist | Whitelist), I might need to swap them visually but keep logic.
                // However, user said "ins whitelist tab...".
                // I'll stick to the text labels "Whitelist" and "Blocklist" and use the icons.
                
                // Blocklist (Index 1)
                val isBlocklist = selectedIndex == 1
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOptionSelected(1) } // Select Blocklist
                        .background(if (isBlocklist) CrystalDesign.Colors.PrimaryStitch else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                     Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(
                            Icons.Default.Block, 
                            null, 
                            tint = if (isBlocklist) Color.White else CrystalDesign.Colors.TextTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Blocklist",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isBlocklist) Color.White else CrystalDesign.Colors.TextTertiary
                        )
                    }
                }

                // Whitelist (Index 0)
                val isWhitelist = selectedIndex == 0
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onOptionSelected(0) } // Select Whitelist
                        .background(if (isWhitelist) CrystalDesign.Colors.PrimaryStitch else Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(
                            Icons.Default.VerifiedUser, 
                            null, 
                            tint = if (isWhitelist) Color.White else CrystalDesign.Colors.TextTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(
                            text = "Whitelist", // Or "Allowlist" if preferred, but keeping "Whitelist"
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = if (isWhitelist) Color.White else CrystalDesign.Colors.TextTertiary
                        )
                    }
                }
            }
        }
    }
}
