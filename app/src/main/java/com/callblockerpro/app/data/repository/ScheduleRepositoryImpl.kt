package com.callblockerpro.app.data.repository

import com.callblockerpro.app.data.local.dao.ScheduleDao
import com.callblockerpro.app.data.local.entity.ScheduleEntity
import com.callblockerpro.app.domain.model.ModeSchedule
import com.callblockerpro.app.domain.repository.ScheduleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleDao: ScheduleDao
) : ScheduleRepository {

    override fun getAllSchedules(): Flow<List<ModeSchedule>> {
        return scheduleDao.getAll().map { entities ->
            entities.map { entity ->
                ModeSchedule(
                    id = entity.id,
                    targetMode = entity.targetMode,
                    startTime = entity.startTime,
                    endTime = entity.endTime,
                    daysOfWeek = entity.daysOfWeek,
                    isEnabled = entity.isEnabled
                )
            }
        }
    }

    override suspend fun getScheduleById(id: Long): ModeSchedule? {
        return scheduleDao.getById(id)?.let { entity ->
            ModeSchedule(
                id = entity.id,
                targetMode = entity.targetMode,
                startTime = entity.startTime,
                endTime = entity.endTime,
                daysOfWeek = entity.daysOfWeek,
                isEnabled = entity.isEnabled
            )
        }
    }

    override suspend fun addSchedule(schedule: ModeSchedule): Long {
        val entity = ScheduleEntity(
            targetMode = schedule.targetMode,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            daysOfWeek = schedule.daysOfWeek,
            isEnabled = schedule.isEnabled
        )
        return scheduleDao.insert(entity)
    }

    override suspend fun updateSchedule(schedule: ModeSchedule) {
        val entity = ScheduleEntity(
            id = schedule.id,
            targetMode = schedule.targetMode,
            startTime = schedule.startTime,
            endTime = schedule.endTime,
            daysOfWeek = schedule.daysOfWeek,
            isEnabled = schedule.isEnabled
        )
        scheduleDao.update(entity)
    }

    override suspend fun deleteSchedule(id: Long) {
        scheduleDao.deleteById(id)
    }
}
