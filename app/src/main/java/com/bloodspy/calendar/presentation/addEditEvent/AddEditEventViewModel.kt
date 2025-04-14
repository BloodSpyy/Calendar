package com.bloodspy.calendar.presentation.addEditEvent

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
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
    val recurrenceRule: String = ""
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
        _uiState.update { it.copy(startTime = it.startTime.with(time)) }
    }

    fun onEndTimeChanged(time: LocalTime) {
        _uiState.update { it.copy(endTime = it.endTime.with(time)) }
    }

    fun onStartDateChanged(date: LocalDate) {
        _uiState.update { it.copy(startTime = it.startTime.with(date)) }
    }

    fun onEndDateChanged(date: LocalDate) {
        _uiState.update { it.copy(endTime = it.endTime.with(date)) }
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
}