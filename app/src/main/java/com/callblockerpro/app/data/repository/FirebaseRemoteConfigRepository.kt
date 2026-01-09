package com.callblockerpro.app.data.repository

import com.callblockerpro.app.domain.repository.RemoteConfigRepository
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRemoteConfigRepository @Inject constructor() : RemoteConfigRepository {

    private val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig

    private val _switchStyle = MutableStateFlow("minimal")
    override val switchStyle: StateFlow<String> = _switchStyle.asStateFlow()

    private val _statsLayout = MutableStateFlow("grid")
    override val statsLayout: StateFlow<String> = _statsLayout.asStateFlow()

    private val _onboardingAllowSkip = MutableStateFlow(false)
    override val onboardingAllowSkip: StateFlow<Boolean> = _onboardingAllowSkip.asStateFlow()

    private val _emergencyNumbers = MutableStateFlow<Set<String>>(emptySet())
    override val emergencyNumbers: StateFlow<Set<String>> = _emergencyNumbers.asStateFlow()

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        
        // precise defaults
        val defaults = mapOf(
            "switch_style" to "minimal",
            "stats_layout" to "grid",
            "onboarding_skip_enabled" to false,
            "emergency_numbers" to ""
        )
        remoteConfig.setDefaultsAsync(defaults)
        
        // Initial load from cached/defaults
        updateValues()
    }

    override fun fetchAndActivate() {
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    updateValues()
                }
            }
    }

    private fun updateValues() {
        _switchStyle.value = remoteConfig.getString("switch_style")
        _statsLayout.value = remoteConfig.getString("stats_layout")
        _onboardingAllowSkip.value = remoteConfig.getBoolean("onboarding_skip_enabled")
        
        val numbersString = remoteConfig.getString("emergency_numbers")
        _emergencyNumbers.value = numbersString.split(",")
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .toSet()
    }
}
