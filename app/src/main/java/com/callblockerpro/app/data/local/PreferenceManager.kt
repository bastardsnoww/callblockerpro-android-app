package com.callblockerpro.app.data.local

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChartType
import com.callblockerpro.app.domain.model.ThemeMode
import kotlinx.coroutines.flow.Flow


interface PreferenceManager {
    val currentMode: Flow<AppMode>
    suspend fun setMode(mode: AppMode)
    suspend fun getMode(): AppMode
    
    val preferredChartType: Flow<ChartType>
    suspend fun setChartType(type: ChartType)
    
    suspend fun setLastManualMode(mode: AppMode)
    suspend fun getLastManualMode(): AppMode

    val isOnboardingCompleted: Flow<Boolean>
    suspend fun setOnboardingCompleted(completed: Boolean)

    val themeMode: Flow<ThemeMode>
    suspend fun setThemeMode(mode: ThemeMode)

    val isBiometricEnabled: Flow<Boolean>
    suspend fun setBiometricEnabled(enabled: Boolean)

    val isAutoBackupEnabled: Flow<Boolean>
    suspend fun setAutoBackupEnabled(enabled: Boolean)

    val isWifiOnlyEnabled: Flow<Boolean>
    suspend fun setWifiOnlyEnabled(enabled: Boolean)

    val isBackupEncryptionEnabled: Flow<Boolean>
    suspend fun setBackupEncryptionEnabled(enabled: Boolean)

    val backupFrequency: Flow<String>
    suspend fun setBackupFrequency(frequency: String)

    val autoBackupLocation: Flow<String>
    suspend fun setAutoBackupLocation(location: String)

    val isNotificationsEnabled: Flow<Boolean>
    suspend fun setNotificationsEnabled(enabled: Boolean)

    val isBackgroundServiceEnabled: Flow<Boolean>
    suspend fun setBackgroundServiceEnabled(enabled: Boolean)

    val backupEncryptionKey: Flow<String>
    suspend fun setBackupEncryptionKey(key: String)

    suspend fun clearAllPreferences()
}


