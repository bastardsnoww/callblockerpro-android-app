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

    // Mock Data for UI Verification
    private val _blocklistFlow = MutableStateFlow(
        listOf(
            ListItem(1, "+1 (555) 012-3456", "Spam Risk", Color(0xFFEF4444), true),
            ListItem(2, "Unknown Private", "Hidden ID", Color(0xFFEF4444), true),
            ListItem(3, "Telemarketers Inc.", "Community Reported", Color(0xFFEF4444), true),
            ListItem(4, "+44 20 7946 0123", "Robocall", Color(0xFFEF4444), true),
            ListItem(5, "Ping Call", "Wangiri Fraud", Color(0xFFEF4444), true),
            ListItem(6, "+1 (800) 555-0199", "Fake Tech Support", Color(0xFFEF4444), true)
        )
    )

    private val _whitelistFlow = MutableStateFlow(
        listOf(
            ListItem(10, "Mom", "My Contacts", Emerald, false),
            ListItem(11, "Office Main Line", "Work", Emerald, false),
            ListItem(12, "+1 (555) 012-9999", "Doctor's Office", Emerald, false),
            ListItem(13, "Alice Smith", "VIP", Emerald, false),
            ListItem(14, "Bob Jones", "Symphony Tickets", Emerald, false)
        )
    )

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
