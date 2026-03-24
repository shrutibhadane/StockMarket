package com.assignments.stockmarket.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.assignments.stockmarket.R

/**
 * T3.10 - CircularSubmitButton Shared Composable
 *
 * An 86dp circular button with ArrowForward icon (idle) or
 * CircularProgressIndicator (loading). Dimmed when disabled.
 * Reusable by all auth screens.
 *
 * @param onClick Action when the button is clicked.
 * @param isLoading Show spinner instead of arrow.
 * @param enabled When false, button is dimmed and not clickable.
 * @param modifier Optional modifier for positioning.
 */
@Composable
fun CircularSubmitButton(
    onClick: () -> Unit,
    isLoading: Boolean,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(86.dp)
            .clip(CircleShape)
            .background(
                if (enabled)
                    colorResource(R.color.button_background_color)
                else
                    colorResource(R.color.button_background_color).copy(alpha = 0.4f)
            )
            .border(2.dp, colorResource(R.color.white), CircleShape)
            .clickable {
                if (!isLoading && enabled) {
                    onClick()
                }
            },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(32.dp),
                color = colorResource(R.color.white),
                strokeWidth = 3.dp
            )
        } else {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Submit",
                tint = if (enabled)
                    colorResource(R.color.white)
                else
                    colorResource(R.color.white).copy(alpha = 0.4f),
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

