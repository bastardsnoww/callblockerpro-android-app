package com.callblockerpro.app.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChartType
import com.callblockerpro.app.domain.model.ThemeMode
import dagger.hilt.android.qualifiers.ApplicationContext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "settings")

@Singleton
class PreferenceManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : PreferenceManager {

    private val MODE_KEY = stringPreferencesKey("app_mode")
    private val MANUAL_MODE_KEY = stringPreferencesKey("last_manual_mode")
    private val CHART_TYPE_KEY = stringPreferencesKey("preferred_chart_type")
    private val ONBOARDING_COMPLETED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("onboarding_completed")
    private val THEME_MODE_KEY = stringPreferencesKey("theme_mode")
    private val BIOMETRIC_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("biometric_enabled")
    private val AUTO_BACKUP_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("auto_backup_enabled")
    private val WIFI_ONLY_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("wifi_only_enabled")
    private val BACKUP_ENCRYPTION_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("backup_encryption_enabled")
    private val BACKUP_FREQUENCY_KEY = stringPreferencesKey("backup_frequency")
    private val AUTO_BACKUP_LOCATION_KEY = stringPreferencesKey("auto_backup_location")
    private val BACKUP_ENCRYPTION_KEY = stringPreferencesKey("backup_encryption_key")
    private val NOTIFICATIONS_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("notifications_enabled")
    private val BACKGROUND_SERVICE_ENABLED_KEY = androidx.datastore.preferences.core.booleanPreferencesKey("background_service_enabled")



    override val currentMode: Flow<AppMode> = context.dataStore.data
        .map { preferences ->
            preferences[MODE_KEY]?.let { AppMode.valueOf(it) } ?: AppMode.NEUTRAL
        }

    override val preferredChartType: Flow<ChartType> = context.dataStore.data
        .map { preferences ->
            preferences[CHART_TYPE_KEY]?.let { ChartType.valueOf(it) } ?: ChartType.LINE
        }

    override suspend fun setChartType(type: ChartType) {
        context.dataStore.edit { preferences ->
            preferences[CHART_TYPE_KEY] = type.name
        }
    }

    override suspend fun setMode(mode: AppMode) {
        context.dataStore.edit { preferences ->
            preferences[MODE_KEY] = mode.name
        }
    }

    override suspend fun getMode(): AppMode {
        return currentMode.firstOrNull() ?: AppMode.NEUTRAL
    }

    override suspend fun setLastManualMode(mode: AppMode) {
        context.dataStore.edit { preferences ->
            preferences[MANUAL_MODE_KEY] = mode.name
        }
    }

    override suspend fun getLastManualMode(): AppMode {
         return context.dataStore.data.map { preferences ->
             preferences[MANUAL_MODE_KEY]?.let { AppMode.valueOf(it) } ?: AppMode.NEUTRAL
        }.firstOrNull() ?: AppMode.NEUTRAL
    }

    override val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] ?: false
        }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED_KEY] = completed
        }
    }

    override val themeMode: Flow<ThemeMode> = context.dataStore.data
        .map { preferences ->
            preferences[THEME_MODE_KEY]?.let { ThemeMode.valueOf(it) } ?: ThemeMode.SYSTEM
        }

    override suspend fun setThemeMode(mode: ThemeMode) {
        context.dataStore.edit { preferences ->
            preferences[THEME_MODE_KEY] = mode.name
        }
    }

    override val isBiometricEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[BIOMETRIC_ENABLED_KEY] ?: false
        }

    override suspend fun setBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED_KEY] = enabled
        }
    }

    override val isAutoBackupEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[AUTO_BACKUP_ENABLED_KEY] ?: false
        }

    override suspend fun setAutoBackupEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_BACKUP_ENABLED_KEY] = enabled
        }
    }

    override val isWifiOnlyEnabled: Flow<Boolean> = context.dataStore.data.map { it[WIFI_ONLY_ENABLED_KEY] ?: true }
    override suspend fun setWifiOnlyEnabled(enabled: Boolean) {
        context.dataStore.edit { it[WIFI_ONLY_ENABLED_KEY] = enabled }
    }

    override val isBackupEncryptionEnabled: Flow<Boolean> = context.dataStore.data.map { it[BACKUP_ENCRYPTION_ENABLED_KEY] ?: false }
    override suspend fun setBackupEncryptionEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BACKUP_ENCRYPTION_ENABLED_KEY] = enabled }
    }

    override val backupFrequency: Flow<String> = context.dataStore.data.map { it[BACKUP_FREQUENCY_KEY] ?: "Daily" }
    override suspend fun setBackupFrequency(frequency: String) {
        context.dataStore.edit { it[BACKUP_FREQUENCY_KEY] = frequency }
    }

    override val autoBackupLocation: Flow<String> = context.dataStore.data.map { it[AUTO_BACKUP_LOCATION_KEY] ?: "Local" }
    override suspend fun setAutoBackupLocation(location: String) {
        context.dataStore.edit { it[AUTO_BACKUP_LOCATION_KEY] = location }
    }

    override val isNotificationsEnabled: Flow<Boolean> = context.dataStore.data.map { it[NOTIFICATIONS_ENABLED_KEY] ?: true }
    override suspend fun setNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { it[NOTIFICATIONS_ENABLED_KEY] = enabled }
    }

    override val isBackgroundServiceEnabled: Flow<Boolean> = context.dataStore.data.map { it[BACKGROUND_SERVICE_ENABLED_KEY] ?: true }
    override suspend fun setBackgroundServiceEnabled(enabled: Boolean) {
        context.dataStore.edit { it[BACKGROUND_SERVICE_ENABLED_KEY] = enabled }
    }

    override val backupEncryptionKey: Flow<String> = context.dataStore.data.map { it[BACKUP_ENCRYPTION_KEY] ?: "" }
    override suspend fun setBackupEncryptionKey(key: String) {
        context.dataStore.edit { it[BACKUP_ENCRYPTION_KEY] = key }
    }


    override suspend fun clearAllPreferences() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}


