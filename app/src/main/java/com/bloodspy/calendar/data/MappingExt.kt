package com.bloodspy.calendar.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bloodspy.calendar.data.local.event.EventLocal
import com.bloodspy.calendar.domain.EventProduct
import com.bloodspy.calendar.utils.toLocalDateTime
import com.bloodspy.calendar.utils.toTimestamp

fun EventProduct.toLocal() = EventLocal(
    id = this.id,
    title = this.title,
    description = this.description,
    startTime = this.startTime.toTimestamp(),
    endTime = this.endTime?.toTimestamp(),
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
    startTime = this.startTime.toLocalDateTime(),
    endTime = this.endTime?.toLocalDateTime(),
    isAllDay = this.isAllDay,
    location = this.location,
    color = Color(this.color),
    recurrenceRule = this.recurrenceRule
)

fun List<EventLocal>.toProduct() = this.map { it.toProduct() }