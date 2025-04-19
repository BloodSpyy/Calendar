package com.bloodspy.calendar.data.network.request

import com.google.gson.annotations.SerializedName
import java.util.Locale

data class AddressRequest(
    @SerializedName("query") val query: String,
    @SerializedName("language") val language: String = Locale.getDefault().language,
    @SerializedName("count") val count: Int = 5,
    @SerializedName("locations") val locations: List<LocationsParam> = listOf(LocationsParam.Country())
)

sealed interface LocationsParam {
    data class Country(
        @SerializedName("country") val country: String = ALL_COUNTRY
    ) : LocationsParam

    companion object {
        private const val ALL_COUNTRY = "*"
    }
}

