package com.bloodspy.calendar.di

import com.bloodspy.calendar.data.DefaultCalendarRepository
import com.bloodspy.calendar.domain.CalendarRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindCalendarRepository(repository: DefaultCalendarRepository): CalendarRepository
}