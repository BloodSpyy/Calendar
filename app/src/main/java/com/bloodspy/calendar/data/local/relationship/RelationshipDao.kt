package com.bloodspy.calendar.data.local.relationship

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface RelationshipDao {
    @Query("SELECT * FROM events WHERE id=:eventId")
    @Transaction
    suspend fun getEventWithNotifications(eventId: Int): EventWithNotifications?
}