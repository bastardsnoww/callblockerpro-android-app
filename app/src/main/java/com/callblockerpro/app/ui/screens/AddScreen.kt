package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.callblockerpro.app.ui.components.*
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.viewmodel.AddViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddViewModel = hiltViewModel()
) {
    val number by viewModel.number.collectAsState()
    val name by viewModel.name.collectAsState()
    val targetList by viewModel.targetList.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val saveSuccess by viewModel.saveSuccess.collectAsState()

    // Handle success navigation
    LaunchedEffect(saveSuccess) {
        if (saveSuccess) {
            onNavigateBack()
            viewModel.resetSaveSuccess()
        }
    }

    StitchScreenWrapper {
        Box(modifier = Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item { Spacer(modifier = Modifier.height(118.dp)) }

            item {
                MetallicToggle(
                    options = listOf("Whitelist", "Blocklist"),
                    selectedIndex = if (targetList == 1) 1 else 0,
                    onOptionSelected = { viewModel.onTargetListChanged(if (it == 0) 0 else 1) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(24.dp)) {
                        Text("PHONE NUMBER", style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                        OutlinedTextField(
                            value = number,
                            onValueChange = { viewModel.onNumberChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Phone, null, tint = Primary) },
                            placeholder = { Text("e.g. +1 555 123 4567", color = Color.Gray.copy(0.5f)) },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Color.White.copy(0.1f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            item {
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(24.dp)) {
                        Text("NAME (OPTIONAL)", style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { viewModel.onNameChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Person, null, tint = Primary) },
                            placeholder = { Text("e.g. John Doe", color = Color.Gray.copy(0.5f)) },
                            singleLine = true,
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedTextColor = Color.White,
                                unfocusedTextColor = Color.White,
                                focusedBorderColor = Primary,
                                unfocusedBorderColor = Color.White.copy(0.1f),
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent
                            )
                        )
                    }
                }
            }

            if (error != null) {
                item {
                    Text(
                        text = error ?: "",
                        color = Color(0xFFEF4444),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            item {
                NeonButton(
                    text = if (targetList == 1) "BLOCK NUMBER" else "ALLOW NUMBER",
                    onClick = { viewModel.saveEntry() },
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Default.Check,
                    color = if (targetList == 1) Color(0xFFEF4444) else Emerald,
                    enabled = !isLoading,
                    isLoading = isLoading
                )
            }
        }

        // Floating Header
        PremiumHeader(
            title = "ADD NUMBER",
            onBack = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )
        }
    }
}
