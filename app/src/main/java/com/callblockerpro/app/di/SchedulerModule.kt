package com.callblockerpro.app.di

import com.callblockerpro.app.domain.scheduler.SchedulerManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SchedulerModule {

    @Provides
    @Singleton
    fun provideSchedulerManager(): SchedulerManager {
        return SchedulerManager()
    }
}
