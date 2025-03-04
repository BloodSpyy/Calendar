package com.bloodspy.calendar.domain.repository

import com.bloodspy.calendar.domain.products.EventProduct
import kotlinx.coroutines.flow.Flow

interface EventsRepository {
    fun getEvents(): Flow<List<EventProduct>>

    suspend fun getEvent(id: Int): EventProduct?

    suspend fun addEvent(event: EventProduct)

    suspend fun updateEvent(event: EventProduct)

    suspend fun removeEvent(id: Int)
}