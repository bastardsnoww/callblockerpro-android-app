package com.callblockerpro.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callblockerpro.app.data.local.dao.ScheduleDao
import com.callblockerpro.app.data.local.entity.ScheduleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    private val scheduleDao: ScheduleDao
) : ViewModel() {

    // Helper state for UI
    private val _workHoursEnabled = MutableStateFlow(false)
    val workHoursEnabled = _workHoursEnabled.asStateFlow()

    private val _startTime = MutableStateFlow(LocalTime.of(9, 0))
    val startTime = _startTime.asStateFlow()

    private val _endTime = MutableStateFlow(LocalTime.of(17, 0))
    val endTime = _endTime.asStateFlow()

    // We manage a single "Work Hours" schedule for this MVP
    private val _workScheduleId = MutableStateFlow<Long?>(null)

    init {
        viewModelScope.launch {
            scheduleDao.getAll().collect { schedules ->
                // For MVP, find the first enabled schedule or default
                val workSchedule = schedules.firstOrNull()
                
                if (workSchedule != null) {
                    _workScheduleId.value = workSchedule.id
                    _workHoursEnabled.value = workSchedule.isEnabled
                    _startTime.value = workSchedule.startTime
                    _endTime.value = workSchedule.endTime
                }
            }
        }
    }

    fun toggleWorkHours(enabled: Boolean) {
        viewModelScope.launch {
            val currentId = _workScheduleId.value
            if (currentId != null) {
                // Update existing
                val existing = scheduleDao.getById(currentId)
                existing?.let {
                    scheduleDao.update(it.copy(isEnabled = enabled))
                }
            } else {
                // Create new default Work Hours (Mon-Fri, 9-5)
                val newSchedule = ScheduleEntity(
                    targetMode = com.callblockerpro.app.domain.model.AppMode.BLOCKLIST, // Default to Blocklist for work
                    startTime = _startTime.value,

                    endTime = _endTime.value,
                    daysOfWeek = java.time.DayOfWeek.values().filter { it != java.time.DayOfWeek.SATURDAY && it != java.time.DayOfWeek.SUNDAY }.toSet(),
                    isEnabled = enabled
                )
                scheduleDao.insert(newSchedule)
            }
            _workHoursEnabled.value = enabled
        }
    }

    fun updateTime(isStart: Boolean, hour: Int, minute: Int) {
        val newTime = LocalTime.of(hour, minute)
        if (isStart) _startTime.value = newTime else _endTime.value = newTime
        
        viewModelScope.launch {
            val currentId = _workScheduleId.value
            if (currentId != null) {
                val existing = scheduleDao.getById(currentId)
                existing?.let {
                    scheduleDao.update(
                        if (isStart) it.copy(startTime = newTime) else it.copy(endTime = newTime)
                    )
                }
            }
        }
    }
}
