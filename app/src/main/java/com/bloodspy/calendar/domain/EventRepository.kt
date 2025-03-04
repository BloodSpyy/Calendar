package com.bloodspy.calendar.domain

import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun getEvents(): Flow<List<Event>>

    suspend fun getEvent(id: Int): Event?

    suspend fun upsertEvent(event: Event)

    suspend fun removeEvent(id: Int)
}