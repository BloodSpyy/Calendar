package com.bloodspy.calendar.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EventLocal::class], version = 1, exportSchema = false)
abstract class CalendarDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao

    companion object {
        private const val DB_NAME = "calendar.db"
        private val LOCK = Any()
        private var INSTANCE: CalendarDatabase? = null

        fun getInstance(context: Context): CalendarDatabase {
            INSTANCE?.let { return it }

            synchronized(LOCK) {
                INSTANCE?.let { return it }

                val database = Room.databaseBuilder(
                    context = context,
                    klass = CalendarDatabase::class.java,
                    name = DB_NAME
                ).build()

                INSTANCE = database
                return database
            }
        }
    }
}