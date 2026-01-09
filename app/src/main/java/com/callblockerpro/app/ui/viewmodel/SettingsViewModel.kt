package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Protection Preferences
    private val _blockUnknown = MutableStateFlow(false)
    val blockUnknown: StateFlow<Boolean> = _blockUnknown.asStateFlow()

    private val _scamProtection = MutableStateFlow(true)
    val scamProtection: StateFlow<Boolean> = _scamProtection.asStateFlow()

    // General Preferences
    private val _notifications = MutableStateFlow(true)
    val notifications: StateFlow<Boolean> = _notifications.asStateFlow()

    private val _faceId = MutableStateFlow(false)
    val faceId: StateFlow<Boolean> = _faceId.asStateFlow()


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun toggleBlockUnknown() {
        _blockUnknown.update { !it }
    }

    fun toggleScamProtection() {
        _scamProtection.update { !it }
    }

    fun toggleNotifications() {
        _notifications.update { !it }
    }

    fun toggleFaceId() {
        _faceId.update { !it }
    }
}
