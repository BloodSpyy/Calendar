package com.bloodspy.calendar.domain

import androidx.compose.runtime.Immutable

@Immutable
data class NotificationProduct(
    val id: Int = 0,
    val eventId: Int,
    val time: Long,
)
