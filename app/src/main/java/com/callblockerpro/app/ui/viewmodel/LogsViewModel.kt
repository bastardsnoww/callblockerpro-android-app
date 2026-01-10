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
        callLogRepository.getRecentLogs(200),
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
