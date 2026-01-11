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
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.History
import android.provider.ContactsContract

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
import com.callblockerpro.app.ui.components.StitchScreenWrapper
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.viewmodel.AddViewModel
import androidx.compose.ui.res.stringResource
import com.callblockerpro.app.R

import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester

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

    val context = androidx.compose.ui.platform.LocalContext.current

    // Contact Picker Launcher
    val contactLauncher = androidx.activity.compose.rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.PickContact()
    ) { uri: android.net.Uri? ->
        uri?.let { contactUri ->
            val projection = arrayOf(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
            context.contentResolver.query(contactUri, projection, null, null, null)?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)
                    val nameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                    
                    if (numberIndex >= 0) viewModel.onNumberChanged(cursor.getString(numberIndex))
                    if (nameIndex >= 0) viewModel.onNameChanged(cursor.getString(nameIndex))
                }
            }
        }
    }

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
                    options = listOf(stringResource(R.string.protection_tab_allowlist), stringResource(R.string.protection_tab_blocklist)),
                    selectedIndex = if (targetList == 1) 1 else 0,
                    onOptionSelected = { viewModel.onTargetListChanged(if (it == 0) 0 else 1) },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                GlassPanel(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(24.dp)) {
                        Text(stringResource(R.string.add_input_phone).uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                        
                        // Import Buttons
                        Row(
                            Modifier.fillMaxWidth().padding(bottom = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { contactLauncher.launch(null) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(0.05f)),
                                shape = RoundedCornerShape(8.dp),
                                contentPadding = PaddingValues(vertical = 12.dp) // Taller touch target
                            ) {
                                Icon(Icons.Default.Contacts, null, tint = Primary, modifier = Modifier.size(16.dp))
                                Spacer(Modifier.width(8.dp))
                                Text(stringResource(R.string.add_import_contacts), style = MaterialTheme.typography.labelSmall, color = Color.White)
                            }
                            
                            // Placeholder for Logs picker (Future improvement: Real Log Picker)
                            // For now, let's keep it Contact only or mock the log picker interaction
                        }

                        val focusRequester = remember { FocusRequester() }
                        
                        LaunchedEffect(Unit) {
                            focusRequester.requestFocus()
                        }

                        OutlinedTextField(
                            value = number,
                            onValueChange = { viewModel.onNumberChanged(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .focusRequester(focusRequester),
                            leadingIcon = { Icon(Icons.Default.Phone, null, tint = Primary) },
                            placeholder = { Text(stringResource(R.string.add_input_phone_placeholder), color = Color.Gray.copy(0.5f)) },
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
                        Text(stringResource(R.string.add_input_name).uppercase(), style = MaterialTheme.typography.labelSmall, color = Color.Gray, modifier = Modifier.padding(bottom = 8.dp))
                        OutlinedTextField(
                            value = name,
                            onValueChange = { viewModel.onNameChanged(it) },
                            modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Person, null, tint = Primary) },
                            placeholder = { Text(stringResource(R.string.add_input_name_placeholder), color = Color.Gray.copy(0.5f)) },
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
                    text = if (targetList == 1) stringResource(R.string.add_action_block) else stringResource(R.string.add_action_allow),
                    onClick = { viewModel.saveEntry() },
                    modifier = Modifier.fillMaxWidth(),
                    icon = Icons.Default.Check,
                    color = if (targetList == 1) Color(0xFFEF4444) else Emerald,
                    enabled = !isLoading,
                    isLoading = isLoading
                )
            }
        }

        val headerTitle = if (targetList == 1) stringResource(R.string.add_number_header_block) else stringResource(R.string.add_number_header_allow)

        // Floating Header
        PremiumHeader(
            title = headerTitle,
            onBack = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )
        }
    }
}
