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
    val month: Int = LocalDate.now().monthValue,
)

@HiltViewModel
class CalendarViewModel @Inject constructor(
    calendarRepository: CalendarRepository,
) : ViewModel() {
    private val _selectedDate =
        MutableStateFlow<LocalDate>(LocalDate.now().withDayOfMonth(FIRST_DAY))
    private val _events = calendarRepository.getAll()

    private val _daysWithEvents = combine(_selectedDate, _events) { date, events ->
        val days = getMonthDays(date)
        val eventsByDays = events.groupBy { event -> event.startTime.toLocalDate() }

        days.map { day ->
            CalendarProduct(
                date = day,
                events = eventsByDays[day]
            )
        }
    }
        .map { Async.Success(it) }
        .catch<Async<List<CalendarProduct>>> { emit(Async.Error(R.string.loading_events_error)) }

    private val _isLoading = MutableStateFlow(false)
    private val _userMessage: MutableStateFlow<Int?> = MutableStateFlow(null)

    val uiState: StateFlow<CalendarUiState> = combine(
        _daysWithEvents, _userMessage, _isLoading
    ) { daysWithEvents, userMessage, isLoading ->
        when (daysWithEvents) {
            is Async.Error -> CalendarUiState(userMessage = daysWithEvents.errorMessage)
            Async.Loading -> CalendarUiState(isLoading = true)
            is Async.Success -> CalendarUiState(
                items = daysWithEvents.data,
                isLoading = isLoading,
                userMessage = userMessage,
                month = _selectedDate.value.monthValue
            )
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = CalendarUiState(isLoading = true)
    )

    fun onLeftSwipe() {
        _selectedDate.update { it.minusMonths(1) }
    }

    fun onRightSwipe() {
        _selectedDate.update { it.plusMonths(1) }
    }
}