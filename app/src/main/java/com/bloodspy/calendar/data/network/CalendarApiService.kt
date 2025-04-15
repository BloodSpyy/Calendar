package com.bloodspy.calendar.data.network

interface CalendarApiService {
    suspend fun getLocation(userInput: String)
}