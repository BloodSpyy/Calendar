package com.bloodspy.calendar.utils.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bloodspy.calendar.R
import com.bloodspy.calendar.constants.ICON_BUTTON_SIZE

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTopAppBar(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = title,
        navigationIcon = navigationIcon,
        actions = actions,
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        )
    )
}

@Composable
fun AddEditTaskTopAppBar(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    DefaultTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.add_edit_task_screen_top_app_bar_go_back_content_description),
                )
            }
        },
        actions = {
            IconButton(onClick = onSaveButtonClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(R.string.add_edit_task_screen_top_app_bar_save_content_description),
                )
            }
        }
    )
}

@Composable
fun LocationPickerTopAppBar(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit
) {
    DefaultTopAppBar(
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.location_picker_screen_top_app_bar_go_back_content_description),
                )
            }
        }
    )
}

@Composable
fun CalendarTopAppBar(
    modifier: Modifier = Modifier,
    onTasksClick: () -> Unit,
    onMenuClick: () -> Unit,
    onHomeClick: () -> Unit,
) {
    DefaultTopAppBar(
        modifier = modifier,
        title = { Text(text = stringResource(R.string.app_name)) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.Outlined.Menu,
                    contentDescription = stringResource(R.string.calendar_screen_top_app_bar_open_menu_content_description),
                )
            }
        },
        actions = {
            IconButton(onClick = onHomeClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.Outlined.Home,
                    contentDescription = stringResource(R.string.calendar_screen_top_app_bar_go_to_current_month_content_description),
                )
            }
            IconButton(onClick = onTasksClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    painter = painterResource(R.drawable.tasks),
                    contentDescription = stringResource(R.string.calendar_screen_top_app_bar_open_settings_content_description),
                )
            }
        }
    )
}