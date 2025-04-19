package com.bloodspy.calendar.data.network.response

import com.google.gson.annotations.SerializedName

data class AddressDetailResponse(
    @SerializedName("country") val country: String
)
