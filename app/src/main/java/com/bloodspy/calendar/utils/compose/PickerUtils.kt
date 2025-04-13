package com.bloodspy.calendar.utils.compose

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.bloodspy.calendar.R
import com.bloodspy.calendar.utils.toLocalDateFromMillis
import com.bloodspy.calendar.utils.toTimestampInMillis
import java.time.LocalDate
import java.time.LocalTime

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

    CalendarDialogWithButtons(
        title = R.string.add_edit_task_screen_choose_time_dialog_title,
        onDismissRequest = onDismissRequest,
        onAcceptButtonClick = {
            onTimeSelected(
                LocalTime.of(state.hour, state.minute)
            )
        }
    ) {
        TimePicker(
            state = state,
            layoutType = TimePickerLayoutType.Vertical
        )
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