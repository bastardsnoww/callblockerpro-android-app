package com.callblockerpro.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.size
import androidx.hilt.navigation.compose.hiltViewModel
import com.callblockerpro.app.ui.theme.BackgroundDark
import com.callblockerpro.app.ui.theme.Primary
import com.callblockerpro.app.ui.viewmodel.SplashViewModel
import androidx.compose.animation.core.*
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.ui.graphics.Brush
import androidx.compose.material3.Icon
import com.callblockerpro.app.ui.components.NeonLoader
import com.callblockerpro.app.ui.components.PremiumBackground
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToOnboarding: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val isOnboardingCompleted by viewModel.isOnboardingCompleted.collectAsState(initial = null)

    LaunchedEffect(isOnboardingCompleted) {
        delay(1500) // Small delay for branding visibility
        when (isOnboardingCompleted) {
            true -> onNavigateToHome()
            false -> onNavigateToOnboarding()
            null -> { /* Loading */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundDark),
        contentAlignment = Alignment.Center
    ) {
        PremiumBackground { }
        
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            // Breathing Shield Animation
            val infiniteTransition = rememberInfiniteTransition(label = "ShieldBreath")
            val scale by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 1.1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000, easing = androidx.compose.animation.core.FastOutSlowInEasing),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ShieldScale"
            )
             val glowAlpha by infiniteTransition.animateFloat(
                initialValue = 0.3f,
                targetValue = 0.6f,
                animationSpec = infiniteRepeatable(
                    animation = tween(2000),
                    repeatMode = RepeatMode.Reverse
                ),
                label = "ShieldGlow"
            )

            Box(contentAlignment = Alignment.Center) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                            alpha = glowAlpha
                        }
                        .background(Primary, CircleShape)
                        .blur(32.dp)
                )
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(80.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Text(
                text = "CALL BLOCKER",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Black,
                color = Color.White,
                letterSpacing = 2.sp
            )
            Text(
                text = "PRO EDITION",
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = Primary,
                letterSpacing = 4.sp
            )
            
            Spacer(modifier = Modifier.height(48.dp))
            NeonLoader(modifier = Modifier.size(32.dp))
        }
    }
}
