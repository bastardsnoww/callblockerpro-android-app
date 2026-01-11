package com.callblockerpro.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

@Singleton
class UserPreferencesRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    private val BIOMETRIC_ENABLED = booleanPreferencesKey("biometric_enabled")
    private val AUTO_BACKUP_ENABLED = booleanPreferencesKey("auto_backup_enabled")
    private val BLOCK_UNKNOWN = booleanPreferencesKey("block_unknown")
    private val SCAM_PROTECTION = booleanPreferencesKey("scam_protection")
    private val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")

    val isOnboardingCompleted: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }

    val isBiometricEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[BIOMETRIC_ENABLED] ?: false
        }

    val isAutoBackupEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[AUTO_BACKUP_ENABLED] ?: false
        }

    val blockUnknown: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[BLOCK_UNKNOWN] ?: false
        }

    val scamProtection: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[SCAM_PROTECTION] ?: true // Default true
        }

    val notificationsEnabled: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[NOTIFICATIONS_ENABLED] ?: true // Default true
        }

    suspend fun saveOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = completed
        }
    }

    suspend fun saveBiometricEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BIOMETRIC_ENABLED] = enabled
        }
    }

    suspend fun saveAutoBackupEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[AUTO_BACKUP_ENABLED] = enabled
        }
    }

    suspend fun saveBlockUnknown(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[BLOCK_UNKNOWN] = enabled
        }
    }

    suspend fun saveScamProtection(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[SCAM_PROTECTION] = enabled
        }
    }

    suspend fun saveNotificationsEnabled(enabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_ENABLED] = enabled
        }
    }
}
