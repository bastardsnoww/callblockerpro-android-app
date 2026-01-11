package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.data.repository.UserPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Protection Preferences
    val blockUnknown: StateFlow<Boolean> = userPreferencesRepository.blockUnknown
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    val scamProtection: StateFlow<Boolean> = userPreferencesRepository.scamProtection
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    // General Preferences
    val notifications: StateFlow<Boolean> = userPreferencesRepository.notificationsEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    val faceId: StateFlow<Boolean> = userPreferencesRepository.isBiometricEnabled
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleBlockUnknown() {
        viewModelScope.launch {
            userPreferencesRepository.saveBlockUnknown(!blockUnknown.value)
        }
    }

    fun toggleScamProtection() {
        viewModelScope.launch {
            userPreferencesRepository.saveScamProtection(!scamProtection.value)
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            userPreferencesRepository.saveNotificationsEnabled(!notifications.value)
        }
    }

    fun toggleFaceId() {
        viewModelScope.launch {
            userPreferencesRepository.saveBiometricEnabled(!faceId.value)
        }
    }
}
