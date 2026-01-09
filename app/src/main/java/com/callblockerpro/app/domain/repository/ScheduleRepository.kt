package com.callblockerpro.app.domain.repository

import com.callblockerpro.app.domain.model.ModeSchedule
import kotlinx.coroutines.flow.Flow

interface ScheduleRepository {
    fun getAllSchedules(): Flow<List<ModeSchedule>>
    suspend fun getScheduleById(id: Long): ModeSchedule?
    suspend fun addSchedule(schedule: ModeSchedule): Long
    suspend fun updateSchedule(schedule: ModeSchedule)
    suspend fun deleteSchedule(id: Long)
}
