package com.bloodspy.calendar.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloodspy.calendar.R
import com.bloodspy.calendar.domain.CalendarProduct
import com.bloodspy.calendar.domain.CalendarRepository
import com.bloodspy.calendar.utils.Async
import com.bloodspy.calendar.utils.FIRST_DAY
import com.bloodspy.calendar.utils.getMonthDays
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import javax.inject.Inject

data class CalendarUiState(
    val items: List<CalendarProduct> = emptyList(),
    val isLoading: Boolean = false,
    val userMessage: Int? = null,
    val monthWithYear: Pair<Int, Int> = LocalDate.now().monthValue to LocalDate.now().year,
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    calendarRepository: CalendarRepository,
) : ViewModel() {
    private val _selectedDate =
        MutableStateFlow<LocalDate>(LocalDate.now().withDayOfMonth(FIRST_DAY))

    private val _eventsByDays = calendarRepository.getAll()
        .map { events -> events.groupBy { event -> event.startTime.toLocalDate() } }

    private val _daysWithEvents = combine(_selectedDate, _eventsByDays) { date, events ->
        val days = getMonthDays(date)

        days.map { day ->
            CalendarProduct(
                date = day,
                events = events[day]
            )
        }
    }
        .map { Async.Success(it) }
        .catch<Async<List<CalendarProduct>>> { emit(Async.Error(R.string.calendar_screen_loading_events_error)) }

    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<CalendarUiState> = combine(
        _daysWithEvents, _userMessage, _isLoading
    ) { daysWithEvents, userMessage, isLoading ->
        when (daysWithEvents) {
            is Async.Error -> CalendarUiState(userMessage = daysWithEvents.errorMessage)
            is Async.Success -> CalendarUiState(
                items = daysWithEvents.data,
                userMessage = userMessage,
                isLoading = isLoading,
                monthWithYear = _selectedDate.value.monthValue to _selectedDate.value.year
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CalendarUiState(isLoading = true)
    )

    fun onArrowBackClick() {
        _selectedDate.update { it.minusMonths(1) }
    }

    fun onArrowForwardClick() {
        _selectedDate.update { it.plusMonths(1) }
    }

    fun onHomeClick() {
        _selectedDate.value = LocalDate.now().withDayOfMonth(FIRST_DAY)
    }
}