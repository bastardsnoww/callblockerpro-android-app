package com.callblockerpro.app.ui.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.domain.repository.ListRepository
import com.callblockerpro.app.ui.theme.Emerald
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

data class ListItem(
    val id: Long,
    val title: String,
    val subtitle: String,
    val color: Color,
    val isBlocked: Boolean
)

@HiltViewModel
class ListsViewModel @Inject constructor(
    private val listRepository: ListRepository
) : ViewModel() {

    // 0 = Whitelist, 1 = Blocklist
    private val _listType = MutableStateFlow(1) 
    val listType: StateFlow<Int> = _listType.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    // Real Data Streams
    private val _blocklistFlow = listRepository.getBlocklist().map { entries ->
        entries.map { entry ->
            ListItem(
                id = entry.id,
                title = entry.phoneNumber,
                subtitle = entry.reason ?: "No reason provided",
                color = Color(0xFFEF4444), // Red for Blocked
                isBlocked = true
            )
        }
    }

    private val _whitelistFlow = listRepository.getWhitelist().map { entries ->
        entries.map { entry ->
            ListItem(
                id = entry.id,
                title = entry.phoneNumber,
                subtitle = entry.name ?: "Trusted Contact",
                color = Emerald, // Green for Allowed
                isBlocked = false
            )
        }
    }

    // Derived State
    val currentItems = combine(
        _listType,
        _searchQuery,
        _blocklistFlow,
        _whitelistFlow
    ) { type, query, blocked, allowed ->
        val items = if (type == 1) blocked else allowed
        if (query.isBlank()) {
            items
        } else {
            items.filter { 
                it.title.contains(query, ignoreCase = true) || 
                it.subtitle.contains(query, ignoreCase = true) 
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onListTypeChanged(index: Int) {
        _listType.value = index
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }
}
