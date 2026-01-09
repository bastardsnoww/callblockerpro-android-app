package com.callblockerpro.app.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface RemoteConfigRepository {
    val switchStyle: StateFlow<String>
    val statsLayout: StateFlow<String>
    val onboardingAllowSkip: StateFlow<Boolean>
    val emergencyNumbers: StateFlow<Set<String>>

    fun fetchAndActivate()
}
