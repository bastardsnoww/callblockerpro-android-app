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
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

@Composable
fun ListsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.ListsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = "lists", onNavigate = onNavigate) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigate("add") },
                containerColor = Primary,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Number")
            }
        }
    ) { paddingValues ->
        val listType by viewModel.listType.collectAsState(initial = 1)
        val searchQuery by viewModel.searchQuery.collectAsState()
        val currentItems by viewModel.currentItems.collectAsState(initial = emptyList())

        PremiumBackground {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
             // Toggle Header
             item {
                 Spacer(modifier = Modifier.height(24.dp))
                 MetallicToggle(
                     options = listOf("Whitelist", "Blocklist"),
                     selectedIndex = listType,
                     onOptionSelected = { viewModel.onListTypeChanged(it) },
                     modifier = Modifier.fillMaxWidth()
                 )
             }

             // Component: Premium Search Bar
             item {
                 PremiumSearchBar(
                     query = searchQuery,
                     onQueryChange = { viewModel.onSearchQueryChanged(it) },
                     placeholder = if (listType == 1) "Search blocklist..." else "Search whitelist...",
                     onFilterClick = {}
                 )
             }

             // Stats Cards
             item {
                 Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                     // Total Blocked
                     GlassPanel(modifier = Modifier.weight(1f).height(100.dp)) {
                         Box(Modifier.fillMaxSize()) {
                             Icon(Icons.Default.Block, contentDescription = null, tint = Color.White.copy(alpha = 0.05f), modifier = Modifier.size(60.dp).align(Alignment.TopEnd).offset(8.dp, (-8).dp))
                             Column(Modifier.padding(16.dp).align(Alignment.BottomStart)) {
                                 Text("TOTAL BLOCKED", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                 Row(verticalAlignment = Alignment.Bottom) {
                                     Text("428", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
                                     Spacer(Modifier.width(4.dp))
                                     Text("+12", style = MaterialTheme.typography.labelSmall, color = Emerald, modifier = Modifier.background(Emerald.copy(0.1f), RoundedCornerShape(4.dp)).padding(horizontal = 4.dp))
                                 }
                             }
                         }
                     }
                     
                     // DB Version
                     GlassPanel(modifier = Modifier.weight(1f).height(100.dp)) {
                         Box(Modifier.fillMaxSize()) {
                             Icon(Icons.Default.CloudSync, contentDescription = null, tint = Color.White.copy(alpha = 0.05f), modifier = Modifier.size(60.dp).align(Alignment.TopEnd).offset(8.dp, (-8).dp))
                             Column(Modifier.padding(16.dp).align(Alignment.BottomStart)) {
                                 Text("DATABASE VER.", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                                 Row(verticalAlignment = Alignment.Bottom) {
                                     Text("v2.4", style = MaterialTheme.typography.headlineSmall, color = Color.White, fontWeight = FontWeight.Bold)
                                     Spacer(Modifier.width(4.dp))
                                     Text("Latest", style = MaterialTheme.typography.labelSmall, color = PrimaryLight)
                                 }
                             }
                         }
                     }
                 }
             }

             // Recently Added Header
             if (currentItems.isNotEmpty()) {
                 item {
                     Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                         Text("RECENTLY ADDED", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                         Text("Sort by Date", style = MaterialTheme.typography.labelSmall, color = Primary, fontWeight = FontWeight.Bold)
                     }
                 }
             }

             // List Items or Empty State
             if (currentItems.isEmpty()) {
                 item {
                     PremiumEmptyState(
                         icon = Icons.Default.Shield,
                         title = "No Numbers Found",
                         message = "Your list is currently empty.\nAdd a number to start protection.",
                         actionLabel = "Add Number",
                         onActionClick = { onNavigate("add") }
                     )
                 }
             } else {
                 items(currentItems) {
                     PremiumListItem(
                         title = it.title,
                         subtitle = it.subtitle, // "Added recently • Auto-detected" hardcoded previously, now mapped
                         tag = if (listType == 1) "BLOCKED" else "ALLOWED", // Dynamic tag
                         tagColor = it.color,
                         icon = if (it.subtitle == "Business") Icons.Default.DomainDisabled else Icons.Default.PersonOff,
                         iconColor = it.color,
                         onClick = {}
                     )
                 }
             }
        }
             }
}
}
