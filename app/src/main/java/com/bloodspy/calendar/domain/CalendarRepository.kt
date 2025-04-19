package com.bloodspy.calendar.domain

import com.bloodspy.calendar.domain.product.AddressProduct
import com.bloodspy.calendar.domain.product.EventProduct
import kotlinx.coroutines.flow.Flow

interface CalendarRepository {
    fun getAll(): Flow<List<EventProduct>>

    suspend fun upsertEvents(eventProduct: EventProduct)

    suspend fun removeEvents(id: Int)

    suspend fun getAddressesSuggestion(query: String): List<AddressProduct>
}