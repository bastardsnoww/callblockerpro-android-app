package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.domain.model.CallLogEntry
import com.callblockerpro.app.domain.model.CallResult
import kotlinx.coroutines.launch

@Composable
fun LogsScreen(
    onNavigate: (String) -> Unit,
    viewModel: com.callblockerpro.app.ui.viewmodel.LogsViewModel = androidx.hilt.navigation.compose.hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val activeFilter by viewModel.filter.collectAsState()

    Scaffold(
        bottomBar = { BottomNavBar(currentRoute = "logs", onNavigate = onNavigate) }
    ) { paddingValues ->
        StitchScreenWrapper {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(Modifier.height(24.dp))
                
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Call Logs",
                        style = MaterialTheme.typography.displaySmall.copy(fontSize = 24.sp),
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold
                    )
                    
                    Surface(
                        color = Color.White.copy(0.05f),
                        shape = RoundedCornerShape(50),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
                        modifier = Modifier.clickable { /* Filter logic */ }
                    ) {
                        Row(
                            Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_preferences), null, tint = Color.Gray, modifier = Modifier.size(14.dp)) // Placeholder icon
                            Text("FILTER", style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                        }
                    }
                }
                
                Spacer(Modifier.height(16.dp))
                
                // Stitch Search Bar
                Box(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        value = searchQuery,
                        onValueChange = { viewModel.onSearchQueryChanged(it) },
                        placeholder = { Text("Search numbers, names...", color = CrystalDesign.Colors.TextTertiary) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = CrystalDesign.Colors.SurfaceStitch,
                            unfocusedContainerColor = CrystalDesign.Colors.SurfaceStitch,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = { Icon(Icons.Default.Search, null, tint = CrystalDesign.Colors.TextTertiary) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp))
                    )
                }
                
                Spacer(Modifier.height(16.dp))
                
                // Horizontal Filters
                Row(
                    Modifier.fillMaxWidth().horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StitchFilterChip("All Logs", activeFilter == null) { viewModel.onFilterSelected(null) }
                    
                    // Allowed (Green)
                    StitchFilterChip(
                        text = "Allowed", 
                        selected = activeFilter == CallResult.ALLOWED, 
                        isRed = false,
                        isGreen = true 
                    ) { viewModel.onFilterSelected(CallResult.ALLOWED) }

                    // Blocked (Red)
                    StitchFilterChip(
                        text = "Blocked", 
                        selected = activeFilter == CallResult.BLOCKED, 
                        isRed = true 
                    ) { viewModel.onFilterSelected(CallResult.BLOCKED) }
                }
                
                Spacer(Modifier.height(24.dp))
                
                // Logs List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    item {
                        Text("TODAY", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    }
                    
                    itemsIndexed(uiState) { _, log ->
                        StitchLogItem(log)
                    }
                }
            }
        }
        }
    }
}

@Composable
fun StitchFilterChip(text: String, selected: Boolean, isRed: Boolean = false, isGreen: Boolean = false, onClick: () -> Unit) {
    val bgColor = if (selected) {
        when {
            isRed -> CrystalDesign.Colors.NeonRed
            isGreen -> com.callblockerpro.app.ui.theme.Emerald
            else -> CrystalDesign.Colors.PrimaryStitch
        }
    } else CrystalDesign.Colors.SurfaceStitch
    
    val textColor = if (selected) Color.White else CrystalDesign.Colors.TextTertiary
    val borderColor = if (selected) bgColor else Color.White.copy(0.05f)
    
    Surface(
        color = bgColor,
        shape = RoundedCornerShape(50),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(Modifier.padding(horizontal = 20.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            if ((isRed || isGreen) && selected) {
                Box(Modifier.size(6.dp).clip(CircleShape).background(Color.White))
                Spacer(Modifier.width(6.dp))
            }
            Text(text, style = MaterialTheme.typography.labelSmall, color = textColor, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun StitchLogItem(log: CallLogEntry) {
    val isBlocked = log.result == CallResult.BLOCKED
    
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(0.05f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box {
            // Vertical Strip Indicator (Red for Blocked, Green for Allowed)
            val stripColor = if (isBlocked) CrystalDesign.Colors.NeonRed else com.callblockerpro.app.ui.theme.Emerald
            Box(Modifier.width(6.dp).fillMaxHeight().background(stripColor).align(Alignment.CenterStart))
            
            Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            if (isBlocked) CrystalDesign.Colors.NeonRed.copy(0.1f) 
                            else CrystalDesign.Colors.SurfaceStitch // Lighter surface
                        )
                        .border(1.dp, if (isBlocked) CrystalDesign.Colors.NeonRed.copy(0.2f) else Color.White.copy(0.05f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        if (isBlocked) Icons.Default.Block else Icons.Default.Person,
                        null,
                        tint = if (isBlocked) CrystalDesign.Colors.NeonRed else Color.White
                    )
                }
                
                Spacer(Modifier.width(16.dp))
                
                Column(Modifier.weight(1f)) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(log.phoneNumber, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold, color = Color.White)
                        Text("10:42 AM", style = MaterialTheme.typography.labelSmall, color = CrystalDesign.Colors.TextTertiary)
                    }
                    Spacer(Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (isBlocked) {
                            Surface(
                                color = CrystalDesign.Colors.NeonRed.copy(0.1f),
                                shape = RoundedCornerShape(4.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CrystalDesign.Colors.NeonRed.copy(0.1f))
                            ) {
                                Text(
                                    "SPAM RISK", 
                                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp), 
                                    color = CrystalDesign.Colors.NeonRed,
                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                )
                            }
                            Spacer(Modifier.width(8.dp))
                        }
                        Text(
                            if (isBlocked) "• Auto-Blocked" else "Mobile • 5m 23s", 
                            style = MaterialTheme.typography.labelSmall, 
                            color = CrystalDesign.Colors.TextTertiary
                        )
                    }
                }
                
                Spacer(Modifier.width(8.dp))
                
                IconButton(
                    onClick = { /* Info */ },
                    modifier = Modifier.size(32.dp).background(Color.White.copy(0.05f), CircleShape)
                ) {
                    Icon(Icons.Default.Info, null, tint = CrystalDesign.Colors.TextTertiary, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
