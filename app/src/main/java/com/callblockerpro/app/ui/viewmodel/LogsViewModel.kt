package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallLogEntry
import com.callblockerpro.app.domain.model.CallResult
import com.callblockerpro.app.domain.repository.CallLogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogsViewModel @Inject constructor(
    private val callLogRepository: CallLogRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _filter = MutableStateFlow<CallResult?>(null)
    val filter = _filter.asStateFlow()

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

    // Combined Flow for Logs
    val logs = combine(_searchQuery, _filter) { query, filterType ->
        Pair(query, filterType)
    }.flatMapLatest { (query, filterType) ->
        callLogRepository.getRecentLogs(100)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // We need a more robust way to filter locally if the repo returns a list flow
    // Re-implementing correctly:
    
    val uiState = combine(
        // Mock Logs Data
        MutableStateFlow(
            listOf(
                CallLogEntry(1, "+1 (555) 012-3456", null, java.time.Instant.now().minusSeconds(120), CallResult.BLOCKED, AppMode.BLOCKLIST, "Spam Risk"),
                CallLogEntry(2, "Mom", "Contact", java.time.Instant.now().minusSeconds(3600), CallResult.ALLOWED, AppMode.BLOCKLIST, "Whitelist"),
                CallLogEntry(3, "Unknown Private", null, java.time.Instant.now().minusSeconds(7200), CallResult.BLOCKED, AppMode.BLOCKLIST, "Hidden ID"),
                CallLogEntry(4, "+1 (800) 123-4567", "Bank", java.time.Instant.now().minusSeconds(18000), CallResult.ALLOWED, AppMode.NEUTRAL, "Normal Mode"),
                CallLogEntry(5, "Telemarketers Inc.", "Business", java.time.Instant.now().minusSeconds(86400), CallResult.BLOCKED, AppMode.BLOCKLIST, "Community Report"),
                CallLogEntry(6, "+44 7700 900077", null, java.time.Instant.now().minusSeconds(90000), CallResult.BLOCKED, AppMode.BLOCKLIST, "International Spam"),
                CallLogEntry(7, "Pizza Delivery", "Verified Business", java.time.Instant.now().minusSeconds(100000), CallResult.ALLOWED, AppMode.WHITELIST, "Whitelist Mode"),
                CallLogEntry(8, "+1 (555) 999-8888", null, java.time.Instant.now().minusSeconds(150000), CallResult.BLOCKED, AppMode.BLOCKLIST, "User Block"),
                CallLogEntry(9, "Emergency Service", "System", java.time.Instant.now().minusSeconds(200000), CallResult.ALLOWED, AppMode.BLOCKLIST, "Emergency"),
                CallLogEntry(10, "Political Survey", null, java.time.Instant.now().minusSeconds(250000), CallResult.BLOCKED, AppMode.BLOCKLIST, "Robocall")
            )
        ),
        _searchQuery,
        _filter
    ) { logs, query, filterType ->
        logs.filter { log ->
            (filterType == null || log.result == filterType) &&
            (query.isBlank() || log.phoneNumber.contains(query, ignoreCase = true))
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onFilterSelected(filter: CallResult?) {
        _filter.value = filter
    }

    fun deleteLog(id: Long) {
        viewModelScope.launch {
            callLogRepository.deleteLogEntry(id)
        }
    }
    
    // Stub for future "Block/Undo" logic if Repository supports updating rules
    fun blockNumber(log: CallLogEntry) {
        // In a real app, this would talk to a BlockingRepository
    }
}
