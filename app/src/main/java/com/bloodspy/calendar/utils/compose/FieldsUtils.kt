package com.bloodspy.calendar.utils.compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.bloodspy.calendar.R
import com.bloodspy.calendar.constants.ICON_BUTTON_SIZE
import com.bloodspy.calendar.constants.SPACE_BETWEEN_ICON_AND_FIELD
import com.bloodspy.calendar.theme.lightBlue
import com.bloodspy.calendar.theme.lightPurple
import com.bloodspy.calendar.theme.lightRed
import com.bloodspy.calendar.theme.lightYellow
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun ColorSelectionField(
    modifier: Modifier = Modifier,
    color: Color = lightBlue,
    @StringRes text: Int = R.string.add_edit_task_screen_default_color,
    onColorSelected: (String) -> Unit
) {
    var isColorSelectionVisible by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable { isColorSelectionVisible = true },
        horizontalArrangement = Arrangement.spacedBy(SPACE_BETWEEN_ICON_AND_FIELD),
    ) {
        Box(
            modifier = Modifier
                .size(ICON_BUTTON_SIZE)
                .clip(CircleShape)
                .background(color = color),
        )

        Text(
            modifier = Modifier,
            text = stringResource(text)
        )
    }

    if (isColorSelectionVisible) {
        val colorNames = stringArrayResource(R.array.add_edit_task_colors).toList()
        // Ensure the color values are in the same order as the resource array
        val colors = listOf(lightPurple, lightYellow, lightRed, lightBlue)
        val colorsByName = remember(colorNames) { colorNames.zip(colors).toMap() }

        SingleSelectDialog(
            title = R.string.add_edit_task_screen_choose_color_dialog_title,
            optionsList = colorNames,
            onDismissRequest = { isColorSelectionVisible = false },
            selectionIndicator = {
                Box(
                    modifier = Modifier
                        .size(ICON_BUTTON_SIZE)
                        .clip(CircleShape)
                        .background(color = colorsByName[it] ?: lightBlue),
                )
            }
        ) {
            onColorSelected(it)
            isColorSelectionVisible = false
        }
    }
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

@Composable
fun AddEditInputField(
    modifier: Modifier = Modifier,
    value: String,
    @DrawableRes icon: Int,
    @StringRes contentDescription: Int,
    @StringRes hint: Int,
    onValueChanged: (String) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            modifier = Modifier.size(ICON_BUTTON_SIZE),
            painter = painterResource(icon),
            contentDescription = stringResource(contentDescription)
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChanged,
            placeholder = { Text(text = stringResource(hint)) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                focusedIndicatorColor = MaterialTheme.colorScheme.background,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = MaterialTheme.colorScheme.background
            )
        )
    }
}