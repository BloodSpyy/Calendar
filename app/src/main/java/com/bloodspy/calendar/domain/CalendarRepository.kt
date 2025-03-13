package com.bloodspy.calendar.domain

import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    fun getAll(): Flow<List<EventProduct>>

    suspend fun upsertEvents(eventProduct: EventProduct)

    suspend fun removeEvents(id: Int)
}