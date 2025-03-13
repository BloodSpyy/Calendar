package com.bloodspy.calendar.data.local.event

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    @Query("SELECT * FROM events")
    fun getAll(): Flow<List<EventLocal>>

    @Upsert
    suspend fun upsert(event: EventLocal)

    @Query("DELETE FROM events WHERE id=:id")
    suspend fun remove(id: Int)
}