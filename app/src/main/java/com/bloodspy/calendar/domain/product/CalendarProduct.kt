package com.bloodspy.calendar.domain.product

import androidx.compose.runtime.Immutable
import java.time.LocalDate

@Immutable
data class CalendarProduct(
    val date: LocalDate,
    val events: List<EventProduct>?,
)