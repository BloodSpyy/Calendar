package com.bloodspy.calendar.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BoxCentered(
    modifier: Modifier = Modifier,
    propagateMinConstraints: Boolean = false,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = modifier,
        propagateMinConstraints = propagateMinConstraints,
        contentAlignment = Alignment.Center
    ) { content() }
}

@Composable
fun LoadingProgress(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.size(72.dp),
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant
    )
}