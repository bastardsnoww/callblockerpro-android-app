package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.domain.model.BlocklistEntry
import com.callblockerpro.app.domain.model.WhitelistEntry
import com.callblockerpro.app.domain.repository.ListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    private val listRepository: ListRepository
) : ViewModel() {

    private val _number = MutableStateFlow("")
    val number: StateFlow<String> = _number.asStateFlow()

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name.asStateFlow()

    // 0 = Whitelist, 1 = Blocklist (Default)
    private val _targetList = MutableStateFlow(1) 
    val targetList: StateFlow<Int> = _targetList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _saveSuccess = MutableStateFlow(false)
    val saveSuccess: StateFlow<Boolean> = _saveSuccess.asStateFlow()

    fun onNumberChanged(newNumber: String) {
        // Simple filter to allow only digits, +, -, ( ) and spaces
        if (newNumber.matches(Regex("[0-9+\\-() ]*"))) {
            _number.value = newNumber
        }
    }

    fun onNameChanged(newName: String) {
        _name.value = newName
    }

    fun onTargetListChanged(index: Int) {
        _targetList.value = index
    }

    fun saveEntry() {
        if (_number.value.isBlank()) {
            _error.value = "Phone number is required"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                if (_targetList.value == 1) {
                    listRepository.addToBlocklist(
                        BlocklistEntry(
                            phoneNumber = _number.value,
                            name = _name.value.takeIf { it.isNotBlank() },
                            reason = "Manual Entry"
                        )
                    )
                } else {
                    listRepository.addToWhitelist(
                        WhitelistEntry(
                            phoneNumber = _number.value,
                            name = _name.value.takeIf { it.isNotBlank() }
                        )
                    )
                }
                _saveSuccess.value = true
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to save entry"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    fun resetSaveSuccess() {
        _saveSuccess.value = false
    }
}
