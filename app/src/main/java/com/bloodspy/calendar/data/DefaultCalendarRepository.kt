package com.bloodspy.calendar.data

import com.bloodspy.calendar.data.local.event.EventDao
import com.bloodspy.calendar.data.network.LocationApiService
import com.bloodspy.calendar.data.network.request.AddressRequest
import com.bloodspy.calendar.domain.CalendarRepository
import com.bloodspy.calendar.domain.product.AddressProduct
import com.bloodspy.calendar.domain.product.EventProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DefaultCalendarRepository @Inject constructor(
    private val localDataSource: EventDao,
    private val locationApiService: LocationApiService
) : CalendarRepository {
    override fun getAll(): Flow<List<EventProduct>> =
        localDataSource.getAll().map { events -> events.toEventProduct() }

    override suspend fun upsertEvents(eventProduct: EventProduct) {
        localDataSource.upsert(eventProduct.toEventLocal())
    }

    override suspend fun removeEvents(id: Int) {
        localDataSource.remove(id)
    }

    override suspend fun getAddressesSuggestion(query: String): List<AddressProduct> {
        return locationApiService.getLocation(AddressRequest(query)).toAddressesProduct()
    }
}