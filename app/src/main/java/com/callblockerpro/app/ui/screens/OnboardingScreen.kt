package com.callblockerpro.app.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.graphicsLayer
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
import com.callblockerpro.app.ui.components.NeonButton
import com.callblockerpro.app.ui.components.StitchScreenWrapper
import com.callblockerpro.app.ui.viewmodel.OnboardingViewModel
import androidx.compose.ui.res.stringResource
import com.callblockerpro.app.R
import kotlinx.coroutines.launch

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
            viewModel.completeOnboarding()
            onOnboardingFinished()
        }
    )

    val finishOnboarding = {
        viewModel.completeOnboarding()
        onOnboardingFinished()
    }

    // Launcher for Permissions
    val launcher = rememberLauncherForActivityResult(
        contract = androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { permissions ->
            val allGranted = permissions.entries.all { it.value }
            if (allGranted) {
                 val roleIntent = com.callblockerpro.app.util.CallScreeningPermissions.createRoleRequestIntent(context)
                 if (roleIntent != null && !com.callblockerpro.app.util.CallScreeningPermissions.isCallScreeningRoleGranted(context)) {
                     roleLauncher.launch(roleIntent)
                 } else {
                     finishOnboarding()
                 }
            }
        }
    )

    // Helper to check if permissions are already granted
    val checkPermissions = remember(context) {
        {
            val permissionsToCheck = listOf(
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_CONTACTS
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
             Manifest.permission.READ_CALL_LOG,
             Manifest.permission.READ_CONTACTS
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            list.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        list.toTypedArray()
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
                // Top Bar with Skip Button
                Box(Modifier.fillMaxWidth().padding(16.dp)) {
                    TextButton(
                        onClick = finishOnboarding,
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(stringResource(R.string.onboarding_skip), color = Color.Gray, style = MaterialTheme.typography.labelMedium)
                    }
                }

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

    var showRationale by remember { mutableStateOf(false) }

    if (showRationale) {
        AlertDialog(
            onDismissRequest = { showRationale = false },
            title = { Text(stringResource(R.string.onboarding_permission_title), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
            text = { Text(stringResource(R.string.onboarding_permission_rational), style = MaterialTheme.typography.bodyMedium) },
            confirmButton = {
                TextButton(
                    onClick = {
                        showRationale = false
                        try {
                            launcher.launch(permissionsToRequest)
                        } catch (e: Exception) {
                            finishOnboarding()
                        }
                    }
                ) {
                    Text(stringResource(R.string.action_continue), color = Primary)
                }
            },
            dismissButton = {
                TextButton(onClick = { showRationale = false }) {
                    Text(stringResource(R.string.action_cancel), color = Color.Gray)
                }
            },
            containerColor = com.callblockerpro.app.ui.theme.CrystalDesign.Colors.SurfaceStitch,
            titleContentColor = Color.White,
            textContentColor = com.callblockerpro.app.ui.theme.CrystalDesign.Colors.TextSecondary
        )
    }

                    NeonButton(
                        text = when {
                            pagerState.currentPage < 2 -> stringResource(R.string.onboarding_next)
                            checkPermissions() -> stringResource(R.string.onboarding_get_started)
                            else -> stringResource(R.string.onboarding_grant_permissions)
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
                                        finishOnboarding()
                                    }
                                } else {
                                    // Show Rationale first!
                                    showRationale = true
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        icon = if (pagerState.currentPage < 2) Icons.AutoMirrored.Filled.ArrowForward else null,
                        color = if (pagerState.currentPage == 2 && !checkPermissions()) Color.White else Primary
                    )

                        // Removed Bottom "Maybe Later" since we moved it to top
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
@Composable
fun OnboardingPage(page: Int) {
    val content = when (page) {
        0 -> OnboardingContent(
            title = stringResource(R.string.onboarding_p1_title),
            description = stringResource(R.string.onboarding_p1_desc),
            icon = Icons.Default.Shield,
            highlight = true
        )
        1 -> OnboardingContent(
            title = stringResource(R.string.onboarding_p2_title),
            description = stringResource(R.string.onboarding_p2_desc),
            icon = Icons.Default.VisibilityOff,
            highlight = false
        )
        else -> OnboardingContent(
             title = stringResource(R.string.onboarding_p3_title),
             description = stringResource(R.string.onboarding_p3_desc),
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
        val infiniteTransition = rememberInfiniteTransition(label = "OnboardingPulse")
        val scale by infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = if (page == 2) 1.1f else 1f, // Only pulse on last page
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "IconScale"
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
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
                 Text(stringResource(R.string.onboarding_trust_badge), style = MaterialTheme.typography.labelSmall, color = Emerald, fontWeight = FontWeight.Bold)
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
