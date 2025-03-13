package com.bloodspy.calendar.domain

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class EventProduct(
    val id: Int = 0,
    val title: String?,
    val description: String?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime?,
    val isAllDay: Boolean,
    val location: String?,
    val color: Color,
    val recurrenceRule: String?,
)