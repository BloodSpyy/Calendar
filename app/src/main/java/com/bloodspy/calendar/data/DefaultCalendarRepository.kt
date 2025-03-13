package com.bloodspy.calendar.data

import com.bloodspy.calendar.data.local.event.EventDao
import com.bloodspy.calendar.domain.CalendarRepository
import com.bloodspy.calendar.domain.EventProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCalendarRepository @Inject constructor(
    private val localDataSource: EventDao,
) : CalendarRepository {
    override fun getAll(): Flow<List<EventProduct>> =
        localDataSource.getAll().map { events -> events.toProduct() }

    override suspend fun upsertEvents(eventProduct: EventProduct) {
        localDataSource.upsert(eventProduct.toLocal())
    }

    override suspend fun removeEvents(id: Int) {
        localDataSource.remove(id)
    }
}