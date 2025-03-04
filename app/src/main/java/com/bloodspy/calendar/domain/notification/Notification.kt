package com.bloodspy.calendar.domain.notification

data class Notification(
    val id: Int = 0,
    val eventId: Int,
    val time: Long,
)
