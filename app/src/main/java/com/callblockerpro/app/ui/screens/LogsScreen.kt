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
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Call

import androidx.compose.material.icons.filled.CallReceived

import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Search


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
fun LogsScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = "logs", onNavigate = onNavigate) }
    ) { paddingValues ->
        var searchQuery by remember { mutableStateOf("") }
        PremiumBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    text = "Call Logs",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))

            // Premium Search Bar
            PremiumSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Search numbers, names...",
                onFilterClick = { /* Filter Action */ }
            )
            
            Spacer(modifier = Modifier.height(16.dp))

            // Filter Chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChipItem("All Logs", true)
                FilterChipItem("Missed", false)
                FilterChipItem("Blocked", false)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Logs List
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
                    Text("TODAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, letterSpacing = 2.sp)
                }
                
                item {
                    PremiumListItem(
                        title = "+1 (555) 019-2834",
                        subtitle = "10:42 AM • Spam Risk",
                        icon = Icons.Default.Block,
                        iconColor = Color(0xFFEF4444),
                        tag = "BLOCKED",
                        tagColor = Color(0xFFEF4444),
                        onClick = {}
                    )
                }
                
                item {
                    PremiumListItem(
                        title = "John Doe",
                        subtitle = "9:15 AM • Mobile • 5m 23s",
                        icon = Icons.Default.CallReceived,
                        iconColor = Primary,
                        onClick = {}
                    )
                }

                item {
                    Text("YESTERDAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, letterSpacing = 2.sp, modifier = Modifier.padding(top = 8.dp))
                }
                
                item {
                    PremiumListItem(
                        title = "Local Pizza",
                        subtitle = "Mon • Business • 45s",
                        icon = Icons.Default.Call,
                        iconColor = Color.Gray,
                        tag = "BUSINESS",
                        tagColor = Color.Gray,
                        onClick = {}
                    )
            }
        }
        }
    }
}

@Composable
fun FilterChipItem(text: String, selected: Boolean) {
    if (selected) {
        // Active State: Metallic Gradient
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(Brush.linearGradient(listOf(Primary, PrimaryLight)))
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clickable { }
        ) {
            Text(text, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White)
        }
    } else {
        // Inactive State: Glass
        GlassPanel(
            modifier = Modifier.clickable { },
            cornerRadius = 50.dp
        ) {
            Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(text, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
    }
}
