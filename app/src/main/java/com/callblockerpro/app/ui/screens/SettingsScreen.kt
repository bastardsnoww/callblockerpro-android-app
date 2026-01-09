package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
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
fun SettingsScreen(onNavigate: (String) -> Unit) {
    Scaffold(
        containerColor = BackgroundDark,
        bottomBar = { BottomNavBar(currentRoute = "settings", onNavigate = onNavigate) }
    ) { paddingValues ->
        var searchQuery by remember { mutableStateOf("") }
        
        PremiumBackground {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 100.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
             // Header & Search
             item {
                 Spacer(modifier = Modifier.height(24.dp))
                 Row(
                     modifier = Modifier.fillMaxWidth(),
                     horizontalArrangement = Arrangement.SpaceBetween,
                     verticalAlignment = Alignment.CenterVertically
                 ) {
                     Column {
                         Text("CallBlockerPro", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                         Text("PREMIUM PROTECTION", style = MaterialTheme.typography.labelSmall, fontWeight = FontWeight.Bold, color = Color.White.copy(0.5f))
                     }
                 }
                 Spacer(modifier = Modifier.height(24.dp))
                 
                 PremiumSearchBar(
                     query = searchQuery,
                     onQueryChange = { searchQuery = it },
                     placeholder = "Search settings..."
                 )
             }

             // Profile Card
             item {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .clip(RoundedCornerShape(24.dp))
                         .background(
                             Brush.linearGradient(
                                 colors = listOf(Color.White.copy(0.1f), Color.White.copy(0.0f))
                             )
                         )
                         .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(24.dp))
                         .clickable { /* TODO: Edit Profile */ }
                         .padding(1.dp)
                 ) {
                      Row(
                          modifier = Modifier
                              .fillMaxWidth()
                              .clip(RoundedCornerShape(22.dp))
                              .background(Color(0xFF1e1b2e))
                              .padding(20.dp),
                          verticalAlignment = Alignment.CenterVertically
                      ) {
                          Box(
                              modifier = Modifier
                                  .size(56.dp)
                                  .clip(CircleShape)
                                  .background(Brush.linearGradient(listOf(Primary, PrimaryLight))),
                              contentAlignment = Alignment.Center
                          ) {
                              Text("JD", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                          }
                          Spacer(Modifier.width(16.dp))
                          Column(Modifier.weight(1f)) {
                              Row(verticalAlignment = Alignment.CenterVertically) {
                                  Text("John Doe", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = Color.White)
                                  Spacer(Modifier.width(8.dp))
                                  Box(Modifier.background(Primary.copy(0.2f), RoundedCornerShape(4.dp)).border(1.dp, Primary.copy(0.2f), RoundedCornerShape(4.dp)).padding(horizontal = 6.dp, vertical = 2.dp)) {
                                      Text("PRO", style = MaterialTheme.typography.labelSmall, color = PrimaryLight, fontSize = 10.sp)
                                  }
                              }
                              Text("+1 (555) 012-3456", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                          }
                          Icon(Icons.Default.ChevronRight, null, tint = Color.Gray)
                      }
                 }
             }

             // Preference Group
             item {
                 SettingsGroup("Protection Preferences") {
                     SettingsToggleRow(
                         icon = Icons.Default.Block,
                         iconColor = Color(0xFFEF4444),
                         title = "Block Unknown Callers",
                         subtitle = "Only allow contacts",
                         checked = false,
                         onCheckedChange = {}
                     )
                     SettingsToggleRow(
                         icon = Icons.Default.Warning,
                         iconColor = Color(0xFFF59E0B),
                         title = "Scam Protection",
                         subtitle = "Aggressive filtering",
                         checked = true,
                         onCheckedChange = {}
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
                         checked = true,
                         onCheckedChange = {}
                     )
                     SettingsToggleRow(
                         icon = Icons.Default.Face,
                         iconColor = Emerald,
                         title = "FaceID Unlock",
                         subtitle = null,
                         checked = false,
                         onCheckedChange = {}
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
                 Spacer(Modifier.height(24.dp))
                 
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
    }
}
}

@Composable
fun SettingsGroup(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.labelSmall, color = Color.Gray, fontWeight = FontWeight.Bold, letterSpacing = 2.sp, modifier = Modifier.padding(start = 8.dp, bottom = 8.dp))
        GlassPanel(
            modifier = Modifier.fillMaxWidth(),
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                content()
            }
        }
    }
}

@Composable
fun SettingsToggleRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color,
    title: String,
    subtitle: String?,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(32.dp).background(iconColor.copy(0.1f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
            Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
        }
        Spacer(Modifier.width(16.dp))
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.White, fontWeight = FontWeight.Bold)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        
        PremiumSwitch(checked = checked, onCheckedChange = onCheckedChange)
    }
    HorizontalDivider(thickness = 1.dp, color = Color.White.copy(0.05f))
}

@Composable
fun SettingsLinkRow(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector?,
    iconColor: Color,
    subtitle: String? = null,
    trailingText: String? = null,
    trailingIcon: androidx.compose.ui.graphics.vector.ImageVector? = Icons.Default.ChevronRight
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Box(modifier = Modifier.size(32.dp).background(iconColor.copy(0.1f), RoundedCornerShape(8.dp)), contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = iconColor, modifier = Modifier.size(20.dp))
            }
            Spacer(Modifier.width(16.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyMedium, color = Color.White, fontWeight = FontWeight.Bold)
            if (subtitle != null) Text(subtitle, style = MaterialTheme.typography.labelSmall, color = Color.Gray)
        }
        if (trailingText != null) {
            Text(trailingText, style = MaterialTheme.typography.bodySmall, color = Color.Gray, fontWeight = FontWeight.Bold)
            Spacer(Modifier.width(8.dp))
        }
        if (trailingIcon != null) {
            Icon(trailingIcon, null, tint = Color.Gray)
        }
    }
    HorizontalDivider(thickness = 1.dp, color = Color.White.copy(0.05f))
}
