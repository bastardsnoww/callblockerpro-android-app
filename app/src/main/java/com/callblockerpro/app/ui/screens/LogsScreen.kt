package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CloudSync
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
            Box(Modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentPadding = PaddingValues(top = 100.dp, bottom = 120.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Search
                    item {
                        PremiumSearchBar(
                            query = searchQuery,
                            onQueryChange = { searchQuery = it },
                            placeholder = "Search logs for threats...",
                            onFilterClick = {}
                        )
                    }

                    // Filters
                    item {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            FilterChipItem("All Activity", true)
                            FilterChipItem("Blocked", false)
                            FilterChipItem("Whitelisted", false)
                        }
                    }

                    // Logs Section
                    item {
                        Spacer(Modifier.height(8.dp))
                        Text("TODAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                    }

                    item {
                        LogItem(
                            number = "+1 (555) 019-2834",
                            label = "Spam Risk",
                            time = "10:42 AM",
                            type = LogType.BLOCKED,
                            onClick = {}
                        )
                    }

                    item {
                        LogItem(
                            number = "John Doe",
                            label = "Known Contact",
                            time = "9:15 AM",
                            type = LogType.ALLOWED,
                            onClick = {}
                        )
                    }

                    item {
                        Spacer(Modifier.height(8.dp))
                        Text("YESTERDAY", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
                    }

                    item {
                        LogItem(
                            number = "Unknown Caller",
                            label = "Potential Fraud",
                            time = "Mon 4:32 PM",
                            type = LogType.SPAM,
                            onClick = {}
                        )
                    }

                    item {
                        LogItem(
                            number = "Pizza Delivery",
                            label = "Whitelisted Business",
                            time = "Mon 12:01 PM",
                            type = LogType.ALLOWED,
                            onClick = {}
                        )
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
                            Text("Security Logs", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Black, color = Color.White)
                            IconButton(onClick = { /* Export */ }, modifier = Modifier.background(Color.White.copy(0.05f), CircleShape).size(40.dp)) {
                                Icon(Icons.Default.CloudSync, null, tint = Color.White, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FilterChipItem(text: String, selected: Boolean) {
    val bgColor = if (selected) Primary else Color.White.copy(0.05f)
    val textColor = if (selected) Color.White else Color.Gray

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgColor)
            .then(if (!selected) Modifier.border(1.dp, androidx.compose.ui.graphics.Color.White.copy(0.1f), RoundedCornerShape(12.dp)) else Modifier)
            .clickable { }
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Text(text, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Black, color = textColor, letterSpacing = 0.5.sp)
    }
}
