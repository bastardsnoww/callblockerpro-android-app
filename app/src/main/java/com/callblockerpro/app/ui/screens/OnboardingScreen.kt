package com.callblockerpro.app.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Emerald
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.theme.PrimaryLight
import com.callblockerpro.app.ui.viewmodel.OnboardingViewModel
import kotlinx.coroutines.launch
import com.callblockerpro.app.ui.components.NeonButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onOnboardingFinished: () -> Unit,
    viewModel: OnboardingViewModel = hiltViewModel()
) {
    val context = androidx.compose.ui.platform.LocalContext.current
    val pagerState = rememberPagerState(pageCount = { 3 })
    val scope = rememberCoroutineScope()
    
    // Launcher for Role Request
    val roleLauncher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult(),
        onResult = { _ ->
            // Even if they cancel the role request, we finish onboarding
            // The Dashboard can show a warning later
            viewModel.completeOnboarding()
            onOnboardingFinished()
        }
    )

    // Launcher for Permissions
    val launcher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                 // After permissions, we still need to check for the Role
                 val roleIntent = com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)
                 if (roleIntent != null && !com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)) {
                     roleLauncher.launch(roleIntent)
                 } else {
                     viewModel.completeOnboarding()
                     onOnboardingFinished()
                 }
            }
        }
    )

    // Helper to check if permissions are already granted

    val checkPermissions = remember(context) {
        {
            val permissionsToCheck = listOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG
            ).toMutableList()
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissionsToCheck.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            
            permissionsToCheck.all { 
                androidx.core.content.ContextCompat.checkSelfPermission(context, it) == android.content.pm.PackageManager.PERMISSION_GRANTED 
            }
        }
    }
    
    // Core permissions list
    val permissionsToRequest = remember {
        val list = mutableListOf(
             Manifest.permission.READ_PHONE_STATE,
             Manifest.permission.READ_CALL_LOG
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        list.toTypedArray()
    }
    
    // Safely complete onboarding
    val completeOnboarding = {
        viewModel.completeOnboarding()
        onOnboardingFinished()
    }

    Scaffold(
        containerColor = Color.Transparent
    ) { padding ->
        StitchScreenWrapper {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // Pager Section
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) { page ->
                    OnboardingPage(page = page)
                }

                // Bottom Control Section
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    // Page Indicators
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(3) { iteration ->
                            val color = if (pagerState.currentPage == iteration) Primary else Color.White.copy(alpha = 0.2f)
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(24.dp))

                    // Primary Button
                    NeonButton(
                        text = when {
                            pagerState.currentPage < 2 -> "Next"
                            checkPermissions() -> "Get Started"
                            else -> "Grant Permissions"
                        },
                        onClick = {
                            if (pagerState.currentPage < 2) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            } else {
                                if (checkPermissions()) {
                                    // Already have permissions, check Role
                                    val roleIntent = com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)
                                    if (roleIntent != null && !com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)) {
                                        roleLauncher.launch(roleIntent)
                                    } else {
                                        completeOnboarding()
                                    }
                                } else {
                                    try {
                                        launcher.launch(permissionsToRequest)
                                    } catch (e: Exception) {
                                        // Fallback if system dialog fails
                                        completeOnboarding()
                                    }
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        icon = if (pagerState.currentPage < 2) Icons.AutoMirrored.Filled.ArrowForward else null,
                        color = if (pagerState.currentPage == 2 && !checkPermissions()) Color.White else Primary
                    )

                    // Secondary Action (Always visible on last page for stability)
                    AnimatedVisibility(
                        visible = pagerState.currentPage == 2,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Spacer(Modifier.height(16.dp))
                            TextButton(onClick = completeOnboarding) {
                                Text(
                                    "Maybe Later",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelLarge
                                )
                            }
                            // Helper text if permissions are persistently denied
                            if (!checkPermissions()) {
                                Text(
                                    "Permissions are needed to verify calls locally.",
                                    color = Color.Gray.copy(0.5f),
                                    style = MaterialTheme.typography.labelSmall,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OnboardingPage(page: Int) {
    val content = when (page) {
        0 -> OnboardingContent(
            title = "Bank-Grade\nProtection",
            description = "Advanced AI analyzes call patterns in real-time to block scams before your phone even rings.",
            icon = Icons.Default.Shield,
            highlight = true
        )
        1 -> OnboardingContent(
            title = "Your Privacy\nFirst",
            description = "We process everything locally on your device. Your call logs and contacts never leave your phone.",
            icon = Icons.Default.VisibilityOff,
            highlight = false
        )
        else -> OnboardingContent(
            title = "Empower Your\nDigital Life",
            description = "Grant permissions to enable full protection. We need access to identify and filter incoming calls.",
            icon = Icons.Default.Security,
            highlight = false
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(180.dp)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(Primary.copy(alpha = 0.2f), Color.Transparent)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = content.icon,
                contentDescription = null,
                modifier = Modifier.size(90.dp),
                tint = if (content.highlight) Emerald else PrimaryLight
            )
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        Text(
            text = content.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = content.description,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            lineHeight = 26.sp
        )
        
        if (page == 2) {
             Spacer(modifier = Modifier.height(32.dp))
             Row(
                 verticalAlignment = Alignment.CenterVertically, 
                 horizontalArrangement = Arrangement.spacedBy(8.dp),
                 modifier = Modifier
                    .background(Emerald.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
             ) {
                 Icon(Icons.Default.Check, null, tint = Emerald, modifier = Modifier.size(16.dp))
                 Text("Zero-Data Collection Policy", style = MaterialTheme.typography.labelSmall, color = Emerald, fontWeight = FontWeight.Bold)
             }
        }
    }
}

data class OnboardingContent(
    val title: String,
    val description: String,
    val icon: ImageVector,
    val highlight: Boolean
)
