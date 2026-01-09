package com.callblockerpro.app.di

import com.callblockerpro.app.data.local.PreferenceManager
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChartType
import com.callblockerpro.app.domain.model.ThemeMode
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [PreferenceModule::class]
)
object TestPreferenceModule {

    @Provides
    @Singleton
    fun providePreferenceManager(): PreferenceManager {
        val mock = mockk<PreferenceManager>(relaxed = true)
        
        // Critical: Ensure Onboarding is NOT completed
        every { mock.isOnboardingCompleted } returns flowOf(false)
        
        // Default other flows to sane values
        every { mock.themeMode } returns flowOf(ThemeMode.SYSTEM)
        every { mock.currentMode } returns flowOf(AppMode.NEUTRAL)
        every { mock.isBiometricEnabled } returns flowOf(false)
        every { mock.preferredChartType } returns flowOf(ChartType.BAR)
        
        return mock
    }
}
