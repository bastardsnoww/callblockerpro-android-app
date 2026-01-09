package com.callblockerpro.app.di

import com.callblockerpro.app.data.repository.CallLogRepositoryImpl
import com.callblockerpro.app.domain.repository.CallLogRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CallLogModule {

    @Binds
    @Singleton
    abstract fun bindCallLogRepository(
        callLogRepositoryImpl: CallLogRepositoryImpl
    ): CallLogRepository
}
