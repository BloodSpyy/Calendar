package com.bloodspy.calendar.domain.product

import androidx.compose.runtime.Immutable

@Immutable
data class NotificationProduct(
    val id: Int = 0,
    val eventId: Int,
    val time: Long,
)
