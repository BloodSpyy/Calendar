package com.bloodspy.calendar.data.local.relationship

import androidx.room.Dao
import androidx.room.Query

@Dao
interface RelationshipDao {
    @Query("SELECT * FROM events WHERE id=:eventId")
    suspend fun getEventWithNotifications(eventId: Int): EventWithNotifications?
}