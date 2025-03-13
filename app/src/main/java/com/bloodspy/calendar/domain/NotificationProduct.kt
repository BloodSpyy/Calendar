package com.bloodspy.calendar.domain

data class NotificationProduct(
    val id: Int = 0,
    val eventId: Int,
    val time: Long,
)
