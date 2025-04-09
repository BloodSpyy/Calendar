package com.bloodspy.calendar.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bloodspy.calendar.data.local.event.EventLocal
import com.bloodspy.calendar.domain.EventProduct
import com.bloodspy.calendar.utils.toLocalDateTimeFromSeconds
import com.bloodspy.calendar.utils.toTimestampInSeconds

fun EventProduct.toLocal() = EventLocal(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime.toTimestampInSeconds(),
    endTime = this.endTime?.toTimestampInSeconds(),
    isAllDay = this.isAllDay,
    location = this.location,
    color = this.color.toArgb(),
    recurrenceRule = this.recurrenceRule,
)

fun List<EventProduct>.toLocal() = this.map { it.toLocal() }

fun EventLocal.toProduct() = EventProduct(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime.toLocalDateTimeFromSeconds(),
    endTime = this.endTime?.toLocalDateTimeFromSeconds(),
    isAllDay = this.isAllDay,
    location = this.location,
    color = Color(this.color),
    recurrenceRule = this.recurrenceRule
)

fun List<EventLocal>.toProduct() = this.map { it.toProduct() }