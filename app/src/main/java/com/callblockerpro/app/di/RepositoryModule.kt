package com.callblockerpro.app.di

import com.callblockerpro.app.data.repository.ListRepositoryImpl
import com.callblockerpro.app.data.repository.ModeRepositoryImpl
import com.callblockerpro.app.domain.repository.ListRepository
import com.callblockerpro.app.domain.repository.ModeRepository
import com.callblockerpro.app.data.repository.ScheduleRepositoryImpl
import com.callblockerpro.app.domain.repository.ScheduleRepository
import com.callblockerpro.app.data.repository.BackupRepositoryImpl
import com.callblockerpro.app.domain.repository.BackupRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindListRepository(
        listRepositoryImpl: ListRepositoryImpl
    ): ListRepository

    @Binds
    @Singleton
    abstract fun bindModeRepository(
        modeRepositoryImpl: ModeRepositoryImpl
    ): ModeRepository

    @Binds
    @Singleton
    abstract fun bindScheduleRepository(
        scheduleRepositoryImpl: ScheduleRepositoryImpl
    ): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindBackupRepository(
        backupRepositoryImpl: BackupRepositoryImpl
    ): BackupRepository
}
