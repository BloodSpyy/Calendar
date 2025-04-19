package com.bloodspy.calendar.presentation.addEditEvent

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import com.bloodspy.calendar.utils.compose.AddEditTaskTopAppBar
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
    onLocationClick: (String) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AddEditTaskTopAppBar(
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
            onLocationClick = onLocationClick,
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
    onLocationClick: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onColorChanged: (Color) -> Unit
) {
    val screenPadding = 12.dp
    val dividerThickness = 1.dp

    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier.padding(top = screenPadding, bottom = screenPadding),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {

        AddEditInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = screenPadding, end = screenPadding)
                .focusRequester(focusRequester),
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

        LocationPicker(
            modifier = Modifier
                .fillMaxWidth()
                .padding(screenPadding),
            location = location,
            onLocationClick = onLocationClick
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
fun LocationPicker(
    modifier: Modifier = Modifier,
    location: String,
    onLocationClick: (String) -> Unit
) {
    Row(
        modifier = modifier
            .clickable { onLocationClick(location) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ICON_AND_FIELD)
    ) {
        val isHint = location.isEmpty()

        val (textColor, text) = if (isHint) {
            MaterialTheme.colorScheme.onSurfaceVariant to stringResource(R.string.add_edit_task_screen_task_location_hint)
        } else {
            MaterialTheme.colorScheme.onBackground to location
        }

        Icon(
            modifier = Modifier.size(ICON_BUTTON_SIZE),
            painter = painterResource(R.drawable.location_city),
            contentDescription = stringResource(
                R.string.add_edit_task_screen_location_content_description
            )
        )

        Text(
            text = text,
            color = textColor
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

            AnimatedDateTimeTransition(pickedStartDate) { startDate ->
                DatePickerField(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDate = startDate,
                    onDateSelected = onStartDateChanged,
                )
            }

            AnimatedDateTimeTransition(pickedEndDate) { endDate ->
                DatePickerField(
                    modifier = Modifier.fillMaxWidth(),
                    selectedDate = endDate,
                    onDateSelected = onEndDateChanged,
                )
            }

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

            AnimatedDateTimeTransition(pickedStartTime) { startTime ->
                TimePickerField(
                    selectedTime = startTime,
                    isAllDay = isAllDay,
                    onTimeSelected = onStartTimeChanged
                )
            }

            AnimatedDateTimeTransition(pickedEndTime) { endTime ->
                TimePickerField(
                    selectedTime = endTime,
                    isAllDay = isAllDay,
                    onTimeSelected = onEndTimeChanged
                )
            }

            Spacer(modifier = Modifier.size(ICON_BUTTON_SIZE))
        }
    }
}

@Composable
fun <S : Comparable<S>> AnimatedDateTimeTransition(
    targetState: S,
    content: @Composable AnimatedContentScope.(S) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        content = content,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { fullHeight -> fullHeight } togetherWith
                        slideOutVertically { fullHeight -> -fullHeight }
            } else {
                slideInVertically { fullHeight -> -fullHeight } togetherWith
                        slideOutVertically { fullHeight -> fullHeight }
            }
        }
    )
}