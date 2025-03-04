package com.bloodspy.calendar.data.local.relationship

import androidx.room.Query

interface RelationshipDao {
    @Query("SELECT * FROM events WHERE id=:eventId")
    suspend fun getEventWithNotifications(eventId: Int): EventWithNotifications?
}