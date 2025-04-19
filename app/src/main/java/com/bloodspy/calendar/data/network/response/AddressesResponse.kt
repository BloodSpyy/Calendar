package com.bloodspy.calendar.data.network.response

import com.google.gson.annotations.SerializedName

data class AddressesResponse(
    @SerializedName("suggestions") val addresses: List<AddressResponse>
)
