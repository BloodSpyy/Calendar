package com.bloodspy.calendar.data

import com.bloodspy.calendar.data.local.event.EventDao
import com.bloodspy.calendar.domain.event.Event
import com.bloodspy.calendar.domain.event.EventRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultEventRepository @Inject constructor(
    private val localDataSource: EventDao,
) : EventRepository {
    override fun getEvents(): Flow<List<Event>> = localDataSource.getAll().map { eventsLocal ->
        eventsLocal.map { it.toExternal() }
    }

    override suspend fun getEvent(id: Int): Event? = localDataSource.getById(id)?.toExternal()

    override suspend fun upsertEvent(event: Event) {
        localDataSource.upsert(event.toLocal())
    }

    override suspend fun removeEvent(id: Int) {
        localDataSource.remove(id)
    }
}