package com.callblockerpro.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.callblockerpro.app.ui.theme.CrystalDesign

/**
 * A responsive wrapper for all screens in the Stitch Design System.
 * Enforces a max-width of 600.dp to prevent UI stretching on tablets/desktops,
 * while ensuring full-width on mobile devices.
 */
@Composable
fun StitchScreenWrapper(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    // Outer container fills the screen and applies the deep background
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CrystalDesign.Colors.BackgroundDeep),
        contentAlignment = Alignment.TopCenter // Change to TopCenter to align content normally
    ) {
        // Inner container constrains width for responsive layouts
        Box(
            modifier = Modifier
                .widthIn(max = 600.dp) // Responsive constraint from Expert Audit
                .fillMaxHeight()
                .fillMaxWidth(), // Fills width up to the max constraint
            content = content
        )
    }
}
