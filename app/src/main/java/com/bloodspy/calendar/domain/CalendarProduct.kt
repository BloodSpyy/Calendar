package com.bloodspy.calendar.domain

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarProduct(
    val date: LocalDate,
    val events: List<EventProduct>?,
)