package com.bloodspy.calendar.data.network.response

import com.google.gson.annotations.SerializedName

data class AddressResponse(
    @SerializedName("value") val addressesSuggestion: String,
    @SerializedName("data") val addressSuggestionDetail: AddressDetailResponse
)
