package com.bloodspy.calendar.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bloodspy.calendar.data.local.EventLocal
import com.bloodspy.calendar.domain.Event

fun Event.toLocal() = EventLocal(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime,
    endTime = this.endTime,
    isAllDay = this.isAllDay,
    location = this.location,
    color = this.color.toArgb(),
    recurrenceRule = this.recurrenceRule,
)

fun List<Event>.toLocal() = this.map { it.toLocal() }

fun EventLocal.toExternal() = Event(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime,
    endTime = this.endTime,
    isAllDay = this.isAllDay,
    location = this.location,
    color = Color(this.color),
    recurrenceRule = this.recurrenceRule
)

fun List<EventLocal>.toExternal() = this.map { it.toExternal() }