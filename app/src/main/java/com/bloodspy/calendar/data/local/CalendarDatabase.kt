package com.bloodspy.calendar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bloodspy.calendar.data.local.event.EventDao
import com.bloodspy.calendar.data.local.event.EventLocal

@Database(entities = [EventLocal::class], version = 1, exportSchema = false)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        const val DB_NAME = "calendar.db"
    }
}