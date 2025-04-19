package com.bloodspy.calendar.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.bloodspy.calendar.data.local.event.EventLocal
import com.bloodspy.calendar.data.network.response.AddressResponse
import com.bloodspy.calendar.data.network.response.AddressesResponse
import com.bloodspy.calendar.domain.product.AddressProduct
import com.bloodspy.calendar.domain.product.EventProduct
import com.bloodspy.calendar.utils.toLocalDateTimeFromSeconds
import com.bloodspy.calendar.utils.toTimestampInSeconds

fun EventProduct.toEventLocal(): EventLocal = EventLocal(
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

fun List<EventProduct>.toEventLocal(): List<EventLocal> = this.map { it.toEventLocal() }

fun EventLocal.toEventProduct(): EventProduct = EventProduct(
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

fun List<EventLocal>.toEventProduct(): List<EventProduct> = this.map { it.toEventProduct() }

fun AddressesResponse.toAddressesProduct(): List<AddressProduct> = addresses.toAddressesProduct()

fun List<AddressResponse>.toAddressesProduct(): List<AddressProduct> =
    this.map { it.toAddressProduct() }

fun AddressResponse.toAddressProduct(): AddressProduct = AddressProduct(
    addressSuggestion = addressesSuggestion,
    country = addressSuggestionDetail.country
)