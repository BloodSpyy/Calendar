package com.bloodspy.calendar.domain

import java.time.LocalDate

data class CalendarProduct(
    val date: LocalDate,
    val events: List<EventProduct>?,
)