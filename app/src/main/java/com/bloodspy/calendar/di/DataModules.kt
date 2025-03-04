package com.bloodspy.calendar.di

import android.content.Context
import androidx.room.Room
import com.bloodspy.calendar.data.DefaultEventRepository
import com.bloodspy.calendar.data.local.CalendarDatabase
import com.bloodspy.calendar.domain.event.EventRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindEventRepository(repository: DefaultEventRepository): EventRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): CalendarDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CalendarDatabase::class.java,
            name = CalendarDatabase.DB_NAME
        ).build()
    }

    @Singleton
    @Provides
    fun provideEventDao(database: CalendarDatabase) = database.eventDao()

    @Singleton
    @Provides
    fun provideRelationshipDao(database: CalendarDatabase) = database.relationshipDao()
}