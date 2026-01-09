package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChangeSource
import com.callblockerpro.app.domain.repository.CallLogRepository
import com.callblockerpro.app.domain.repository.ModeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val modeRepository: ModeRepository,
    private val callLogRepository: CallLogRepository
) : ViewModel() {

    // Map Domain AppMode to UI Index
    // 0 = Normal/Neutral, 1 = Whitelist, 2 = Blocklist
    val selectedMode: StateFlow<Int> = modeRepository.currentMode.map { mode ->
        when (mode) {
            AppMode.NEUTRAL -> 0
            AppMode.WHITELIST -> 1
            AppMode.BLOCKLIST -> 2
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 2)

    private val statsFlow = callLogRepository.getStatsFlow()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val blockedToday: StateFlow<Int> = statsFlow
        .map { it?.spamBlocked ?: 0 }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val totalThreats: StateFlow<Int> = statsFlow
        .map { it?.callsScreened ?: 0 } // Using screened count as proxy for activity
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val weeklyActivity: StateFlow<List<Float>> = statsFlow
        .map { it?.weeklyActivity ?: emptyList() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onModeSelected(index: Int) {
        val newMode = when (index) {
            0 -> AppMode.NEUTRAL
            1 -> AppMode.WHITELIST
            else -> AppMode.BLOCKLIST
        }
        
        viewModelScope.launch {
            modeRepository.setMode(newMode, ChangeSource.USER)
        }
    }
}
