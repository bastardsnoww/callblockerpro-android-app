package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

@Composable
fun ListsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.ListsViewModel = hiltViewModel()
) {
    Scaffold(
        containerColor = CrystalDesign.Colors.BackgroundDeep,
        bottomBar = { BottomNavBar(currentRoute = "lists", onNavigate = onNavigate) }
    ) { paddingValues ->
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
                        MetallicToggle(
                            options = listOf("Whitelist", "Blocklist"),
                            selectedIndex = listType,
                            onOptionSelected = { viewModel.onListTypeChanged(it) },
                            modifier = Modifier.fillMaxWidth()
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
                        itemsIndexed(currentItems) { index, it ->
                            AnimatedEntrance(index = index + 3) {
                                PremiumListItem(
                                    title = it.title,
                                    subtitle = it.subtitle,
                                    tag = if (listType == 1) "BLOCKED" else "ALLOWED",
                                    tagColor = it.color,
                                    icon = if (it.subtitle == "Business") Icons.Default.DomainDisabled else Icons.Default.PersonOff,
                                    iconColor = it.color,
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
