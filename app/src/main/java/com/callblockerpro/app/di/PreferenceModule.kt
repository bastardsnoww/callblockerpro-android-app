package com.callblockerpro.app.di

import com.callblockerpro.app.data.local.PreferenceManager
import com.callblockerpro.app.data.local.PreferenceManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule {

    @Binds
    @Singleton
    abstract fun bindPreferenceManager(
        preferenceManagerImpl: PreferenceManagerImpl
    ): PreferenceManager
}
