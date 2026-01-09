package com.callblockerpro.app.ui.components

import androidx.compose.animation.*
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

/**
 * Wraps content in a premium stagger animation.
 * Optimal for LazyColumn items.
 *
 * @param index The index of the item in the list (used for delay calculation).
 * @param delayBase How much delay per index (default 50ms).
 * @param modifier Modifier for the wrapper.
 */
@Composable
fun AnimatedEntrance(
    index: Int,
    modifier: Modifier = Modifier,
    delayBase: Long = 50L,
    content: @Composable () -> Unit
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        // Stagger functionality
        delay(60L + (index * delayBase)) // Initial 60ms delay + stagger
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            initialOffsetY = { 50 }, // Slide up from 50px
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        ) + fadeIn(
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        ) + expandVertically(
            expandFrom = androidx.compose.ui.Alignment.Top
        ),
        modifier = modifier
    ) {
        content()
    }
}
