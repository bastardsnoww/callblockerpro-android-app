package com.callblockerpro.app.di

import com.callblockerpro.app.data.repository.FirebaseRemoteConfigRepository
import com.callblockerpro.app.domain.repository.RemoteConfigRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteConfigModule {

    @Binds
    @Singleton
    abstract fun bindRemoteConfigRepository(
        impl: FirebaseRemoteConfigRepository
    ): RemoteConfigRepository
}
