package com.bloodspy.calendar.presentation.addEditTask

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bloodspy.calendar.R
import com.bloodspy.calendar.utils.ICON_BUTTON_SIZE

@Composable
fun AddEditTaskScreen(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            AddEditTaskAppBar(
                modifier = Modifier.fillMaxWidth(),
                onArrowBackClick = onArrowBackClick,
                onSaveButtonClick = onSaveButtonClick
            )
        },
    ) { paddingValues ->
        AddEditTaskContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            taskName = ""
        )
    }
}

@Composable
fun AddEditTaskContent(
    modifier: Modifier = Modifier,
    taskName: String,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        NameInput(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp),
            taskName = taskName
        )

        HorizontalDivider(thickness = 1.dp)

        EventTimePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 3.dp, end = 8.dp)
        )

        HorizontalDivider(thickness = 1.dp)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventTimePicker(
    modifier: Modifier = Modifier,
) {
    val itemSpacing = 24.dp

    Row(
        modifier = modifier,
        // Set the horizontal spacing between elements to (32.dp - ICON_BUTTON_SIZE)
        // to offset the icon width and maintain visual alignment based on the 'name' field.
        horizontalArrangement = Arrangement.spacedBy(32.dp - ICON_BUTTON_SIZE),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            Icon(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                painter = painterResource(R.drawable.schedule),
                contentDescription = stringResource(R.string.add_edit_task_screen_schedule_content_description)
            )

            Spacer(modifier = Modifier.size(ICON_BUTTON_SIZE))

            Spacer(modifier = Modifier.size(ICON_BUTTON_SIZE))

            Icon(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                painter = painterResource(R.drawable.repeat),
                contentDescription = stringResource(R.string.add_edit_task_screen_repeatable_days_content_description)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            Text(
                text = stringResource(R.string.add_edit_task_screen_all_day)
            )

            val state = rememberDatePickerState()
            var a by remember { mutableStateOf(false) }

            if (a) {
                DatePickerDialog(
                    onDismissRequest = { a = false },
                    confirmButton = {},
                    dismissButton = {}
                ) {
                    DatePicker(state)
                }
            }

            Text(
                modifier = Modifier.clickable {
                    a = true
                },
                text = "Пн, 31мар. 2025г"
            )

            Text(
                text = "Пн, 31мар. 2025г"
            )

            Text(
                text = stringResource(R.string.add_edit_task_screen_not_repeatable)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            Checkbox(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                checked = false,
                onCheckedChange = {}
            )

            Text(
                text = "11:00"
            )

            Text(
                text = "12:00"
            )

            Spacer(modifier = Modifier.size(ICON_BUTTON_SIZE))
        }
    }
}

@Composable
fun NameInput(
    modifier: Modifier = Modifier,
    taskName: String,
) {
    var name by remember { mutableStateOf(taskName) }

    OutlinedTextField(
        modifier = modifier,
        value = name,
        onValueChange = { name = it },
        label = {
            Text(text = stringResource(R.string.add_edit_task_screen_task_name_hint))
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditTaskAppBar(
    modifier: Modifier = Modifier,
    onArrowBackClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    TopAppBar(
        modifier = modifier,
        title = {},
        navigationIcon = {
            IconButton(onClick = onArrowBackClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                    contentDescription = stringResource(R.string.add_edit_task_screen_top_app_bar_go_back_content_description)
                )
            }
        },
        actions = {
            IconButton(onClick = onSaveButtonClick) {
                Icon(
                    modifier = Modifier.size(ICON_BUTTON_SIZE),
                    imageVector = Icons.Outlined.Done,
                    contentDescription = stringResource(R.string.add_edit_task_screen_top_app_bar_save_content_description)
                )
            }
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
            actionIconContentColor = MaterialTheme.colorScheme.onSurface,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
    )
}