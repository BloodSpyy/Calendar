package com.bloodspy.calendar.presentation.addEditTask

import androidx.lifecycle.ViewModel
import com.bloodspy.calendar.domain.CalendarRepository
import javax.inject.Inject

class AddEditTaskViewModel @Inject constructor(
    private val calendarRepository: CalendarRepository
): ViewModel() {

}

