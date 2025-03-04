package com.bloodspy.calendar.data.local.notification

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notifications")
data class NotificationLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val eventId: Int,
    val time: Long,
)
