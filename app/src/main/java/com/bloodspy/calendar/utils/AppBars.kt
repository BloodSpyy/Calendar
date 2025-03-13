package com.bloodspy.calendar.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bloodspy.calendar.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTopAppBar(
    title: String,
    onTasksClick: () -> Unit,
    onMenuClick: () -> Unit
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = stringResource(R.string.open_menu)
                )
            }
        },
        actions = {
            IconButton(onClick = onTasksClick) {
                Icon(
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = stringResource(R.string.open_settings)
                )
            }
        }
    )
}