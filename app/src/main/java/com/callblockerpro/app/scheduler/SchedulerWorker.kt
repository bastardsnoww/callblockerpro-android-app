package com.callblockerpro.app.scheduler

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.callblockerpro.app.data.local.PreferenceManager
import com.callblockerpro.app.domain.model.ChangeSource
import com.callblockerpro.app.domain.repository.ModeRepository
import com.callblockerpro.app.domain.repository.ScheduleRepository
import com.callblockerpro.app.domain.scheduler.SchedulerManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime

@HiltWorker
class SchedulerWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val scheduleRepository: ScheduleRepository,
    private val modeRepository: ModeRepository,
    private val preferenceManager: PreferenceManager,
    private val schedulerManager: SchedulerManager
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val schedules = scheduleRepository.getAllSchedules().first()
            val now = LocalDateTime.now()

            val activeSchedule = schedulerManager.evaluateActiveSchedule(schedules, now)

            if (activeSchedule != null) {
                // Apply scheduled mode
                modeRepository.setMode(
                    mode = activeSchedule.targetMode,
                    source = ChangeSource.SCHEDULE,
                    scheduleId = activeSchedule.id
                )
            } else {
                // No schedule active -> Revert to manual
                val lastManual = preferenceManager.getLastManualMode()
                modeRepository.setMode(
                    mode = lastManual,
                    source = ChangeSource.SYSTEM, // Or SYSTEM to indicate it wasn't a direct user action?
                    scheduleId = null
                )
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.retry()
        }
    }
}
