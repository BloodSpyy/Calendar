package com.bloodspy.calendar.presentation.addEditTask

import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.bloodspy.calendar.R
import com.bloodspy.calendar.utils.ICON_BUTTON_SIZE
import com.bloodspy.calendar.utils.toLocalDateFromMillis
import com.bloodspy.calendar.utils.toTimestampInMillis
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
            var pickedStartDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
            var pickedEndDate by rememberSaveable { mutableStateOf(LocalDate.now()) }

            Text(
                text = stringResource(R.string.add_edit_task_screen_all_day)
            )

            DatePickerField(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = pickedStartDate,
                onDateSelected = { pickedStartDate = it },
            )

            DatePickerField(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = pickedEndDate,
                onDateSelected = { pickedEndDate = it },
            )

            Text(
                text = stringResource(R.string.add_edit_task_screen_not_repeatable)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            var pickedStartTime by rememberSaveable { mutableStateOf(LocalTime.now()) }
            var pickedEndTime by rememberSaveable { mutableStateOf(LocalTime.now()) }
            var isAllDay by remember { mutableStateOf(false) }

            Checkbox(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                checked = isAllDay,
                onCheckedChange = { isAllDay = !isAllDay }
            )

            TimePickerField(
                selectedTime = pickedStartTime,
                isAllDay = isAllDay
            ) { pickedStartTime = it }

            TimePickerField(
                selectedTime = pickedEndTime,
                isAllDay = isAllDay
            ) { pickedEndTime = it }

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
        },
        colors = TopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            scrolledContainerColor = MaterialTheme.colorScheme.surface,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onSurface
        ),
    )
}

@Composable
fun DatePickerField(
    modifier: Modifier = Modifier,
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
) {
    var isDatePickerVisible by rememberSaveable { mutableStateOf(false) }

    val dateFormatPattern = stringResource(R.string.add_edit_task_screen_date_format_pattern)
    val formattedDate = DateTimeFormatter.ofPattern(dateFormatPattern).format(selectedDate)

    Text(
        modifier = modifier.clickable { isDatePickerVisible = true },
        text = formattedDate
    )

    if (isDatePickerVisible) {
        CalendarDatePicker(
            initialDate = selectedDate,
            onDateSelected = {
                onDateSelected(it)
                isDatePickerVisible = false
            },
            onDismissRequest = { isDatePickerVisible = false }
        )
    }
}

@Composable
fun TimePickerField(
    modifier: Modifier = Modifier,
    selectedTime: LocalTime = LocalTime.now(),
    isAllDay: Boolean,
    onTimeSelected: (LocalTime) -> Unit
) {
    var isTimePickerVisible by rememberSaveable { mutableStateOf(false) }

    // if isAllDay -> hide time pickers and if !isAllDay -> show time pickers
    val alphaForTimePickers = if (isAllDay) 0f else 1f

    val timeFormatPattern = stringResource(R.string.add_edit_task_screen_time_format_pattern)

    Text(
        modifier = modifier
            .alpha(alphaForTimePickers)
            .clickable(enabled = !isAllDay) { isTimePickerVisible = true },
        text = selectedTime.format(DateTimeFormatter.ofPattern(timeFormatPattern))
    )

    if (isTimePickerVisible) {
        CalendarTimePicker(
            initialTime = selectedTime,
            onTimeSelected = {
                onTimeSelected(it)
                isTimePickerVisible = false
            },
            onDismissRequest = { isTimePickerVisible = false }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarTimePicker(
    initialTime: LocalTime = LocalTime.now(),
    onTimeSelected: (LocalTime) -> Unit,
    onDismissRequest: () -> Unit
) {
    val state = rememberTimePickerState(
        initialHour = initialTime.hour, initialMinute = initialTime.minute, is24Hour = true
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(usePlatformDefaultWidth = true)
    ) {
        ElevatedCard(
            modifier = Modifier.background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.extraLarge
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    fontWeight = FontWeight.SemiBold,
                    text = stringResource(R.string.add_edit_task_screen_choose_time_dialog_title)
                )

                TimePicker(
                    state = state,
                    layoutType = TimePickerLayoutType.Vertical
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = onDismissRequest
                    ) {
                        Text(text = stringResource(R.string.anyone_screen_picker_cancel))
                    }

                    Button(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            onTimeSelected(
                                LocalTime.of(state.hour, state.minute)
                            )
                            onDismissRequest()
                        }
                    ) {
                        Text(text = stringResource(R.string.anyone_screen_picker_ok))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarDatePicker(
    modifier: Modifier = Modifier,
    initialDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit,
    onDismissRequest: () -> Unit
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialDate.toTimestampInMillis()
    )

    DatePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        confirmButton = {
            Button(
                onClick = {
                    val selectedDate =
                        state.selectedDateMillis?.toLocalDateFromMillis() ?: initialDate
                    onDateSelected(selectedDate)
                    onDismissRequest()
                }
            ) {
                Text(text = stringResource(R.string.anyone_screen_picker_ok))
            }
        },
        dismissButton = {
            Button(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.anyone_screen_picker_cancel))
            }
        }
    ) {
        DatePicker(state)
    }
}