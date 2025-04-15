package com.bloodspy.calendar.presentation.addEditEvent

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bloodspy.calendar.R
import com.bloodspy.calendar.constants.ICON_BUTTON_SIZE
import com.bloodspy.calendar.constants.SPACE_BETWEEN_ICON_AND_FIELD
import com.bloodspy.calendar.utils.compose.AddEditInputField
import com.bloodspy.calendar.utils.compose.ColorSelectionField
import com.bloodspy.calendar.utils.compose.DatePickerField
import com.bloodspy.calendar.utils.compose.TimePickerField
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Composable
fun AddEditEventScreen(
    modifier: Modifier = Modifier,
    viewModel: AddEditEventViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
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
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { paddingValues ->
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()

        AddEditEventContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            title = uiState.title,
            location = uiState.location,
            description = uiState.description,
            startTime = uiState.startTime,
            endTime = uiState.endTime,
            isAllDay = uiState.isAllDay,
            color = uiState.color,
            recurrenceRule = uiState.recurrenceRule,
            onTitleChanged = viewModel::onTitleChanged,
            onStartTimeChanged = viewModel::onStartTimeChanged,
            onEndTimeChanged = viewModel::onEndTimeChanged,
            onStartDateChanged = viewModel::onStartDateChanged,
            onEndDateChanged = viewModel::onEndDateChanged,
            onAllDayClick = viewModel::onAllDayClick,
            onLocationChanged = viewModel::onLocationChanged,
            onDescriptionChanged = viewModel::onDescriptionChanged,
            onColorChanged = viewModel::onColorChanged,
        )

        uiState.userMessage?.let { message ->
            val snackbarText = stringResource(message)
            LaunchedEffect(snackbarHostState, viewModel, message, snackbarText) {
                snackbarHostState.showSnackbar(snackbarText)
                viewModel.onSnackbarMessageShown()
            }
        }
    }
}

@Composable
fun AddEditEventContent(
    modifier: Modifier = Modifier,
    title: String,
    location: String,
    description: String,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    isAllDay: Boolean,
    color: Color,
    recurrenceRule: String,
    onTitleChanged: (String) -> Unit,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onAllDayClick: (Boolean) -> Unit,
    onLocationChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onColorChanged: (Color) -> Unit
) {
    val screenPadding = 12.dp
    val dividerThickness = 1.dp

    Column(
        modifier = modifier.padding(top = screenPadding, bottom = screenPadding),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        AddEditInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, end = screenPadding),
            value = title,
            icon = R.drawable.edit_square,
            contentDescription = R.string.add_edit_task_screen_name_content_description,
            hint = R.string.add_edit_task_screen_task_name_hint,
            onValueChanged = onTitleChanged
        )

        HorizontalDivider(thickness = dividerThickness)

        EventTimePicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            startTime = startTime,
            endTime = endTime,
            isAllDay = isAllDay,
            onStartTimeChanged = onStartTimeChanged,
            onEndTimeChanged = onEndTimeChanged,
            onStartDateChanged = onStartDateChanged,
            onEndDateChanged = onEndDateChanged,
            onAllDayClick = onAllDayClick
        )

        HorizontalDivider(thickness = dividerThickness)

        AddEditInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, end = screenPadding),
            value = location,
            icon = R.drawable.location_city,
            contentDescription = R.string.add_edit_task_screen_location_content_description,
            hint = R.string.add_edit_task_screen_task_location_hint,
            onValueChanged = onLocationChanged
        )

        HorizontalDivider(thickness = dividerThickness)

        AddEditInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, end = screenPadding),
            value = description,
            icon = R.drawable.description,
            contentDescription = R.string.add_edit_task_screen_description_content_description,
            hint = R.string.add_edit_task_screen_description_hint,
            onValueChanged = onDescriptionChanged
        )

        HorizontalDivider(thickness = dividerThickness)

        ColorSelectionField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            color = color,
            onColorSelected = onColorChanged
        )
    }
}

@Composable
fun EventTimePicker(
    modifier: Modifier = Modifier,
    startTime: LocalDateTime,
    endTime: LocalDateTime,
    isAllDay: Boolean,
    onStartTimeChanged: (LocalTime) -> Unit,
    onEndTimeChanged: (LocalTime) -> Unit,
    onStartDateChanged: (LocalDate) -> Unit,
    onEndDateChanged: (LocalDate) -> Unit,
    onAllDayClick: (Boolean) -> Unit
) {
    val itemSpacing = 24.dp

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ICON_AND_FIELD),
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
            val pickedStartDate = startTime.toLocalDate()
            val pickedEndDate = endTime.toLocalDate()

            Text(
                text = stringResource(R.string.add_edit_task_screen_all_day)
            )

            DatePickerField(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = pickedStartDate,
                onDateSelected = onStartDateChanged,
            )

            DatePickerField(
                modifier = Modifier.fillMaxWidth(),
                selectedDate = pickedEndDate,
                onDateSelected = onEndDateChanged,
            )

            Text(
                text = stringResource(R.string.add_edit_task_screen_not_repeatable)
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(itemSpacing)
        ) {
            val pickedStartTime = startTime.toLocalTime()
            val pickedEndTime = endTime.toLocalTime()

            Checkbox(
                modifier = Modifier.size(ICON_BUTTON_SIZE),
                checked = isAllDay,
                onCheckedChange = onAllDayClick
            )

            TimePickerField(
                selectedTime = pickedStartTime,
                isAllDay = isAllDay,
                onTimeSelected = onStartTimeChanged
            )

            TimePickerField(
                selectedTime = pickedEndTime,
                isAllDay = isAllDay,
                onTimeSelected = onEndTimeChanged
            )

            Spacer(modifier = Modifier.size(ICON_BUTTON_SIZE))
        }
    }
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