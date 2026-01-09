package com.callblockerpro.app.domain.scheduler

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ModeSchedule
import java.time.LocalDateTime

class SchedulerManager {

    /**
     * Determines the active mode based on the current time and list of schedules.
     * Returns the ModeSchedule that should be active, or null if none apply.
     */
    fun evaluateActiveSchedule(
        schedules: List<ModeSchedule>,
        currentTime: LocalDateTime
    ): ModeSchedule? {
        val currentDay = currentTime.dayOfWeek
        val currentTimeOfDay = currentTime.toLocalTime()

        // Filter for applicable schedules
        val activeSchedules = schedules.filter { schedule ->
            schedule.isEnabled &&
            schedule.daysOfWeek.contains(currentDay) &&
            !currentTimeOfDay.isBefore(schedule.startTime) &&
            currentTimeOfDay.isBefore(schedule.endTime)
        }

        if (activeSchedules.isEmpty()) {
            return null
        }

        // Conflict resolution:
        // 1. Latest Start Time wins (closest to now usually implies more specific intent)
        // 2. Tie-break: Whitelist > Blocklist > Neutral
        return activeSchedules.sortedWith(
            compareByDescending<ModeSchedule> { it.startTime }
                .thenComparing { schedule ->
                    when (schedule.targetMode) {
                        AppMode.WHITELIST -> 3
                        AppMode.BLOCKLIST -> 2
                        AppMode.NEUTRAL -> 1
                    }
                }
        ).first()
    }
}
