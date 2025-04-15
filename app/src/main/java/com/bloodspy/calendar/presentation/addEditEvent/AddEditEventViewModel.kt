package com.bloodspy.calendar.presentation.addEditEvent

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.CalendarRepository
import com.bloodspy.calendar.theme.defaultEventColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

data class AddEditEventUiState(
    val title: String = "",
    val description: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val isAllDay: Boolean = false,
    val location: String = "",
    val color: Color = defaultEventColor,
    val recurrenceRule: String = "",
    val userMessage: Int? = null
)

@HiltViewModel
class AddEditEventViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AddEditEventUiState())
    val uiState: StateFlow<AddEditEventUiState> = _uiState.asStateFlow()

    fun onTitleChanged(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun onAllDayClick(isChecked: Boolean) {
        _uiState.update { it.copy(isAllDay = isChecked) }
    }

    fun onStartTimeChanged(time: LocalTime) {
        updateStartTime { it.with(time) }
    }

    fun onStartDateChanged(date: LocalDate) {
        updateStartTime { it.with(date) }
    }

    fun onEndTimeChanged(time: LocalTime) {
        validateAndUpdateEndTime { it.with(time) }
    }

    fun onEndDateChanged(date: LocalDate) {
        validateAndUpdateEndTime { it.with(date) }
    }

    fun onLocationChanged(location: String) {
        _uiState.update { it.copy(location = location) }
    }

    fun onDescriptionChanged(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onColorChanged(color: Color) {
        _uiState.update { it.copy(color = color) }
    }

    fun onSnackbarMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

    private fun updateStartTime(transform: (LocalDateTime) -> LocalDateTime) {
        val fullStartTime = transform(_uiState.value.startTime)

        _uiState.update {
            if (fullStartTime <= it.endTime) {
                it.copy(startTime = fullStartTime)
            } else {
                it.copy(startTime = fullStartTime, endTime = fullStartTime)
            }
        }
    }

    private fun validateAndUpdateEndTime(transform: (LocalDateTime) -> LocalDateTime) {
        val fullEndTime = transform(_uiState.value.endTime)

        if (fullEndTime < _uiState.value.startTime) {
            showSnackbarMessage(R.string.add_edit_task_screen_end_date_error_message)
        } else {
            _uiState.update { it.copy(endTime = fullEndTime) }
        }
    }

    private fun showSnackbarMessage(@StringRes message: Int) {
        _uiState.update { it.copy(userMessage = message) }
    }
}