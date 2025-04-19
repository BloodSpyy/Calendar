package com.bloodspy.calendar.data.network

import com.bloodspy.calendar.data.network.request.AddressRequest
import com.bloodspy.calendar.data.network.response.AddressesResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LocationApiService {
    @POST("suggest/address")
    suspend fun getLocation(@Body addressRequest: AddressRequest): AddressesResponse
}