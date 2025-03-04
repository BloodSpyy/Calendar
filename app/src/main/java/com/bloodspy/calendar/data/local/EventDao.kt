package com.bloodspy.calendar.data.local

import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<EventLocal>>

    @Query("SELECT * FROM events WHERE id=:id")
    suspend fun getById(id: Int): EventLocal?

    @Upsert
    suspend fun upsert(event: EventLocal)

    @Query("DELETE FROM events WHERE id=:id")
    suspend fun remove(id: Int)
}