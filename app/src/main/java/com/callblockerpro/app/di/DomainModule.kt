package com.callblockerpro.app.di

import com.callblockerpro.app.domain.logic.CallDecisionEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideCallDecisionEngine(): CallDecisionEngine {
        return CallDecisionEngine()
    }
}
