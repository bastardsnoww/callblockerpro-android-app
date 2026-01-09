package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Weekend
import androidx.compose.material.icons.filled.Work
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
import com.callblockerpro.app.ui.components.BottomNavBar
import com.callblockerpro.app.ui.components.GlassPanel
import com.callblockerpro.app.ui.components.MetallicToggle
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight

@Composable
fun ScheduleScreen(currentRoute: String, onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate) }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
             // Header
             item {
                 Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Column {
                         Text("CallBlockerPro", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                         Text("AUTO-SCHEDULE", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White.copy(0.5f))
                     }
                      IconButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .background(Color.White.copy(alpha = 0.05f), CircleShape)
                            .size(40.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings",
                            tint = Color.Gray
                        )
                    }
                 }
             }

             // Status Card
             item {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clip(RoundedCornerShape(32.dp))
                         .background(Color(0xFF1e1b2e))
                         .border(1.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(32.dp))
                 ) {
                     Box(Modifier.fillMaxSize().background(Brush.linearGradient(listOf(Color(0xFF2d2a42), BackgroundDark))))
                     
                     Row(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                 Box(modifier = Modifier.size(8.dp).background(Primary, CircleShape))
                                 Text("CURRENT STATUS", color = PrimaryLight, style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Normal Mode", style = MaterialTheme.typography.titleLarge, color = Color.White, fontWeight = FontWeight.ExtraBold)
                            Text("Until 10:00 PM (Night Shift)", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                        }
                        Box(
                            modifier = Modifier.size(48.dp).background(Color.White.copy(0.05f), RoundedCornerShape(12.dp)).border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.Schedule, null, tint = Color.Gray)
                        }
                    }
                 }
             }

             // Config Panel
             item {
                 Text("NEW CONFIGURATION", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                 Spacer(Modifier.height(8.dp))
                 GlassPanel(Modifier.fillMaxWidth()) {
                     Column(Modifier.padding(20.dp)) {
                         Text("SCHEDULE NAME", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                         Spacer(Modifier.height(8.dp))
                         OutlinedTextField(
                             value = "",
                             onValueChange = {},
                             placeholder = { Text("e.g. Deep Work", color = Color.Gray) },
                             modifier = Modifier.fillMaxWidth(),
                             shape = RoundedCornerShape(12.dp),
                             colors = OutlinedTextFieldDefaults.colors(
                                 focusedContainerColor = Color(0xFF161022), // darker bg
                                 unfocusedContainerColor = Color(0xFF161022),
                                 focusedBorderColor = Primary,
                                 unfocusedBorderColor = Color.White.copy(0.1f)
                             )
                         )
                         Spacer(Modifier.height(16.dp))
                         Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                             // Start Time
                             Column(Modifier.weight(1f).background(Color(0xFF161022), RoundedCornerShape(12.dp)).border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(12.dp)).padding(12.dp)) {
                                 Text("START TIME", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
                                 Text("09:00 AM", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                             }
                             // End Time
                             Column(Modifier.weight(1f).background(Color(0xFF161022), RoundedCornerShape(12.dp)).border(1.dp, Color.White.copy(0.05f), RoundedCornerShape(12.dp)).padding(12.dp)) {
                                 Text("END TIME", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontSize = 10.sp)
                                 Text("05:00 PM", style = MaterialTheme.typography.titleMedium, color = Color.White, fontWeight = FontWeight.Bold)
                             }
                         }
                         Spacer(Modifier.height(16.dp))
                         Text("REPEATS ON", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold)
                         Spacer(Modifier.height(8.dp))
                         Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                             listOf("M", "T", "W", "T", "F", "S", "S").forEachIndexed { index, day ->
                                 val active = index < 5
                                 Box(
                                     modifier = Modifier
                                        .size(32.dp)
                                        .clip(CircleShape)
                                        .background(if (active) Primary else Color.White.copy(0.05f)),
                                     contentAlignment = Alignment.Center
                                 ) {
                                     Text(day, style = MaterialTheme.typography.labelSmall, color = if (active) Color.White else Color.Gray, fontWeight = FontWeight.Bold)
                                 }
                             }
                         }
                     }
                 }
             }

             // Active Schedules
             item {
                 Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Text("ACTIVE SCHEDULES", style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                     Text("2 Enabled", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontWeight = FontWeight.Bold)
                 }
                 Spacer(Modifier.height(8.dp))
                 ScheduleCard(
                     title = "Night Shift",
                     time = "10:00 PM - 07:00 AM",
                     tag1 = "Daily",
                     tag2 = "Block All",
                     color = Color(0xFFA855F7),
                     icon = Icons.Default.Nightlight
                 )
                 Spacer(Modifier.height(12.dp))
                 ScheduleCard(
                     title = "Focus Time",
                     time = "09:00 AM - 12:00 PM",
                     tag1 = "Mon-Fri",
                     tag2 = "Whitelist Only",
                     color = Color(0xFF3B82F6),
                     icon = Icons.Default.Work
                 )
             }
             item { Spacer(Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun ScheduleCard(
    title: String,
    time: String,
    tag1: String,
    tag2: String,
    color: Color,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    GlassPanel(Modifier.fillMaxWidth()) {
        Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Box(
                Modifier.size(48.dp).background(color.copy(0.1f), RoundedCornerShape(12.dp)).border(1.dp, color.copy(0.2f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, null, tint = color)
            }
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                     Text(title, style = MaterialTheme.typography.titleSmall, color = Color.White, fontWeight = FontWeight.Bold)
                     Switch(checked = true, onCheckedChange = {}, modifier = Modifier.scale(0.7f))
                }
                Text(time, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(Modifier.background(Color.White.copy(0.05f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(tag1, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
                    }
                     Box(Modifier.background(color.copy(0.1f), RoundedCornerShape(4.dp)).border(1.dp, color.copy(0.2f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                        Text(tag2, style = MaterialTheme.typography.labelSmall, color = color, fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

fun Modifier.scale(scale: Float): Modifier = this.then(Modifier) // Placeholder for simplicity, ideally use graphicsLayer
