package com.bloodspy.calendar.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class EventLocal(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val title: String?,
    val description: String?,
    val startTime: Long,
    val endTime: Long?,
    val isAllDay: Boolean,
    val location: String?,
    val color: Int,
    val recurrenceRule: String?,
)