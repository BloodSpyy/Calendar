package com.bloodspy.calendar.domain.event

import androidx.compose.ui.graphics.Color

data class Event(
    val id: Int = 0,
    val title: String?,
    val description: String?,
    val startTime: Long,
    val endTime: Long?,
    val isAllDay: Boolean,
    val location: String?,
    val color: Color,
    val recurrenceRule: String?,
)