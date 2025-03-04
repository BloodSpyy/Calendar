package com.bloodspy.calendar.domain.products

import androidx.compose.ui.graphics.Color

data class EventProduct(
    val id: Int,
    val title: String?,
    val description: String?,
    val startTime: Long,
    val endTime: Long?,
    val isAllDay: Boolean,
    val location: String?,
    val color: Color,
    val recurrenceRule: String?,
)