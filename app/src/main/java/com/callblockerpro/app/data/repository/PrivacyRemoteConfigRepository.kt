package com.callblockerpro.app.data.repository

import com.callblockerpro.app.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Privacy-friendly implementation of [RemoteConfigRepository].
 * This version uses static defaults and performs no network calls,
 * in line with the zero-tracking privacy requirement.
 */
@Singleton
class PrivacyRemoteConfigRepository @Inject constructor() : RemoteConfigRepository {

    private val _switchStyle = MutableStateFlow("minimal")
    override val switchStyle: StateFlow<String> = _switchStyle.asStateFlow()

    private val _statsLayout = MutableStateFlow("grid")
    override val statsLayout: StateFlow<String> = _statsLayout.asStateFlow()

    private val _onboardingAllowSkip = MutableStateFlow(false)
    override val onboardingAllowSkip: StateFlow<Boolean> = _onboardingAllowSkip.asStateFlow()

    private val _emergencyNumbers = MutableStateFlow<Set<String>>(emptySet())
    override val emergencyNumbers: StateFlow<Set<String>> = _emergencyNumbers.asStateFlow()

    override fun fetchAndActivate() {
        // No-Op: We do not fetch data from external servers to preserve user privacy.
    }
}
