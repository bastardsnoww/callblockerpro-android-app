package com.callblockerpro.app.ui.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.animation.core.*
import com.callblockerpro.app.ui.theme.CrystalDesign
import com.callblockerpro.app.ui.theme.MetallicGradientColors
import androidx.compose.ui.hapticfeedback.HapticFeedbackType

/**
 * A unified, high-quality search bar for all list screens.
 */
@Composable
fun PremiumSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search...",
    modifier: Modifier = Modifier,
    onFilterClick: (() -> Unit)? = null,
    isLoading: Boolean = false
) {
    val focusManager = androidx.compose.ui.platform.LocalFocusManager.current
    
    // Stitch Search Bar Container
    Surface(
        color = CrystalDesign.Colors.SurfaceStitch,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.1f)),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            // Leading Icon
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = CrystalDesign.Colors.TextSecondary,
                modifier = Modifier.size(20.dp)
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Input Field
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium,
                        color = CrystalDesign.Colors.TextTertiary
                    )
                }
                BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White
                    ),
                    singleLine = true,
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            
            // Trailing Actions (Clear or Filter)
            if (query.isNotEmpty()) {
                 Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.Close,
                    contentDescription = "Clear",
                    tint = CrystalDesign.Colors.TextSecondary,
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onQueryChange("") }
                )
            } else if (onFilterClick != null) {
                 Icon(
                    imageVector = androidx.compose.material.icons.Icons.Default.FilterList,
                    contentDescription = "Filter",
                    tint = CrystalDesign.Colors.Primary,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { onFilterClick() }
                )
            }
        }
    }
}

/**
 * High-quality list item with built-in layout consistency.
 */
@Composable
fun PremiumListItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconColor: Color,
    modifier: Modifier = Modifier,
    tag: String? = null,
    tagColor: Color = CrystalDesign.Colors.TextSecondary,
    trailingContent: (@Composable () -> Unit)? = { Icon(Icons.Default.ChevronRight, null, tint = CrystalDesign.Colors.TextSecondary.copy(0.4f)) },
    onClick: () -> Unit
) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isPressed) 0.96f else 1f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 80),
        label = "PressScale"
    )

    GlassPanel(
        modifier = modifier
            .fillMaxWidth()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(RoundedCornerShape(CrystalDesign.Glass.CornerRadius))
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                onClick()
            },
        cornerRadius = CrystalDesign.Glass.CornerRadius,
        borderAlpha = 0.12f
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon Box
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(iconColor.copy(alpha = 0.1f), CircleShape)
                .border(1.dp, iconColor.copy(alpha = 0.2f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Text Content
        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.White,
                    fontWeight = CrystalDesign.Typography.WeightBold
                )
                if (tag != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(tagColor.copy(alpha = 0.15f), RoundedCornerShape(6.dp))
                            .border(1.dp, tagColor.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = tag.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = tagColor,
                            fontSize = 10.sp, // Explicit small caption override for tags if needed, or define in Type
                            fontWeight = CrystalDesign.Typography.WeightBlack
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }

        // Trailing
        if (trailingContent != null) {
            Spacer(modifier = Modifier.width(12.dp))
            trailingContent()
        }
    }
    }
}

/**
 * Beautiful empty state for lists.
 */
@Composable
fun PremiumEmptyState(
    icon: ImageVector,
    title: String,
    message: String,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(48.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    Brush.radialGradient(
                        colors = listOf(CrystalDesign.Colors.Primary.copy(alpha = 0.2f), Color.Transparent)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CrystalDesign.Colors.Primary.copy(alpha = 0.8f),
                modifier = Modifier.size(48.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        
        if (actionLabel != null && onActionClick != null) {
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = onActionClick,
                colors = ButtonDefaults.buttonColors(containerColor = CrystalDesign.Colors.Primary),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = actionLabel, fontWeight = FontWeight.Bold)
            }
        }
    }
}

/**
 * Custom "Cyber" styled switch.
 */
@Composable
fun PremiumSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val thumbColor = if (checked) Color.White else Color.Gray
    val trackColor = if (checked) CrystalDesign.Colors.Primary else Color(0xFF2d2a42)
    
    // We can stick to standard Switch for now but with custom colors to ensure reliability,
    // or build a custom one. A customized standard switch is often safer for accessibility.
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = CrystalDesign.Colors.Primary,
            uncheckedThumbColor = Color(0xFF8b8ea5),
            uncheckedTrackColor = Color(0xFF1e1b2e),
            uncheckedBorderColor = Color.White.copy(alpha = 0.1f)
        )
    )
}

/**
 * A glowing, glassy button for primary actions.
 */
@Composable
fun NeonButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    color: Color = CrystalDesign.Colors.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    var isPressed by remember { mutableStateOf(false) }
    val scale by androidx.compose.animation.core.animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = androidx.compose.animation.core.tween(durationMillis = 80),
        label = "ButtonScale"
    )

    Box(
        modifier = modifier
            .height(56.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                alpha = if (enabled) 1f else 0.5f
            }
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                enabled = enabled,
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    onClick()
                }
            )
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        color.copy(alpha = 0.8f),
                        color.copy(alpha = 0.5f)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.5f),
                        Color.White.copy(alpha = 0.1f)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        // Inner Glow
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.1f),
                            Color.Transparent
                        ),
                        radius = 150f
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
        )

        if (isLoading) {
            NeonLoader(modifier = Modifier.size(24.dp), color = Color.White)
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text.uppercase(),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = CrystalDesign.Typography.WeightBlack,
                    color = Color.White,
                    letterSpacing = 1.sp
                )
            }
        }
    }
}

/**
 * A custom pulsing crystal loader.
 */
@Composable
fun NeonLoader(
    modifier: Modifier = Modifier,
    color: Color = CrystalDesign.Colors.Primary
) {
    val infiniteTransition = androidx.compose.animation.core.rememberInfiniteTransition(label = "Loader")
    
    val isReducedMotion = com.callblockerpro.app.ui.theme.isReducedMotionEnabled()
    
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = if (isReducedMotion) 0.8f else 1.2f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable(
            animation = androidx.compose.animation.core.tween<Float>(800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Reverse
        ),
        label = "LoaderScale"
    )
    
    val alpha by infiniteTransition.animateFloat(
        initialValue = if (isReducedMotion) 1f else 0.5f,
        targetValue = if (isReducedMotion) 1f else 0.0f,
        animationSpec = androidx.compose.animation.core.infiniteRepeatable( // Keeping spec to avoid composition errors but values are static
            animation = androidx.compose.animation.core.tween<Float>(800),
            repeatMode = androidx.compose.animation.core.RepeatMode.Restart
        ),
        label = "LoaderAlpha"
    )

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        // Outer Ripple
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    this.alpha = alpha
                }
                .background(color, CircleShape)
        )
        // Core
        Box(
            modifier = Modifier
                .fillMaxSize(0.5f)
                .background(color, CircleShape)
                .border(1.dp, Color.White.copy(alpha = 0.8f), CircleShape)
        )
    }
}

/**
 * Custom metallic button for the header.
 */
@Composable
fun MetallicHeaderButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    val shimmerTransition = rememberInfiniteTransition(label = "HeaderShimmer")
    val isReducedMotion = com.callblockerpro.app.ui.theme.isReducedMotionEnabled()
    val shimmerAlpha by shimmerTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isReducedMotion) 0f else 0.4f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "HeaderShimmerAlpha"
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .clip(CircleShape)
            .background(Brush.verticalGradient(MetallicGradientColors))
            .border(1.dp, Color.White.copy(0.15f), CircleShape)
            .clickable {
                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        // Shimmer Layer
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { alpha = shimmerAlpha }
                .background(
                    Brush.linearGradient(
                        listOf(Color.White.copy(0f), Color.White.copy(0.3f), Color.White.copy(0f))
                    )
                )
        )
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}


/**
 * Standardized Floating Crystal Header - Redesigned (10/10) with Adaptive Sizing.
 */
@Composable
fun PremiumHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    onBack: (() -> Unit)? = null,
    actionIcon: ImageVector? = null,
    onAction: (() -> Unit)? = null
) {
    val haptic = androidx.compose.ui.platform.LocalHapticFeedback.current
    
    // Stitch Header
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(com.callblockerpro.app.ui.theme.adaptiveHeaderHeight())
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBack != null) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { 
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onBack() 
                    }
                    .background(Color.White.copy(0.05f))
                    .border(1.dp, Color.White.copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier.graphicsLayer { rotationZ = 180f }
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
        } else {
             // [STITCH] Gradient Shield Box with Ring
             Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryStitch,
                                com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryLightStitch
                            )
                        )
                    )
                    // ring-1 ring-white/10 equivalent
                    .border(1.dp, Color.White.copy(0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
             ) {
                 // Composite Icon: Shield (Base) + Lock (Overlay/Cutout)
                 // This simulates the 'shield_lock' symbol where the lock is 'inside' the shield.
                 
                 // Base Shield
                 Icon(
                     imageVector = androidx.compose.material.icons.Icons.Default.Shield, 
                     contentDescription = "Shield",
                     tint = Color.White,
                     modifier = Modifier.size(24.dp)
                 )
                 
                 // Inner Lock (Cutout effect using Primary color)
                 Icon(
                     imageVector = androidx.compose.material.icons.Icons.Default.Lock,
                     contentDescription = null,
                     tint = com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryStitch, // Matches gradient start to simulate cutout
                     modifier = Modifier.size(12.dp).offset(y = 1.dp) // Slight offset to center visually in shield body
                 )
             }
             Spacer(modifier = Modifier.width(12.dp))
        }

        Column(modifier = Modifier.weight(1f)) {
            // [STITCH] Title with Mixed Colors
            androidx.compose.ui.text.AnnotatedString.Builder().apply {
                append(androidx.compose.ui.text.AnnotatedString("CallBlocker", spanStyle = androidx.compose.ui.text.SpanStyle(color = Color.White)))
                append(androidx.compose.ui.text.AnnotatedString("Pro", spanStyle = androidx.compose.ui.text.SpanStyle(color = com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryLightStitch)))
            }.toAnnotatedString().let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.titleLarge, 
                    fontWeight = FontWeight.ExtraBold,
                    lineHeight = 20.sp
                )
            }
            
            if (subtitle != null) {
                Text(
                    text = subtitle.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp),
                    color = CrystalDesign.Colors.TextTertiary, // slate-500
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            }
        }

        if (actionIcon != null && onAction != null) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .clickable { 
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onAction() 
                    }
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryStitch,
                                com.callblockerpro.app.ui.theme.CrystalDesign.Colors.PrimaryLightStitch
                            )
                        )
                    )
                    .border(1.dp, Color.White.copy(0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = "Action",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
