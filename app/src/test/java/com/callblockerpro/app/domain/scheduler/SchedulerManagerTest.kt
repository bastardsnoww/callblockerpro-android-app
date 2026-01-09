package com.callblockerpro.app.domain.scheduler

import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ModeSchedule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

class SchedulerManagerTest {

    private val manager = SchedulerManager()

    @Test
    fun `evaluateActiveSchedule returns null if no schedule matches`() {
        val now = LocalDateTime.of(2023, 10, 2, 12, 0) // Monday, 12:00
        val schedules = listOf(
            ModeSchedule(
                targetMode = AppMode.WHITELIST,
                startTime = LocalTime.of(13, 0),
                endTime = LocalTime.of(14, 0),
                daysOfWeek = setOf(DayOfWeek.MONDAY)
            )
        )

        val result = manager.evaluateActiveSchedule(schedules, now)
        assertNull(result)
    }

    @Test
    fun `evaluateActiveSchedule returns matching schedule`() {
        val now = LocalDateTime.of(2023, 10, 2, 13, 30) // Monday, 13:30
        val schedule = ModeSchedule(
            targetMode = AppMode.WHITELIST,
            startTime = LocalTime.of(13, 0),
            endTime = LocalTime.of(14, 0),
            daysOfWeek = setOf(DayOfWeek.MONDAY)
        )
        val schedules = listOf(schedule)

        val result = manager.evaluateActiveSchedule(schedules, now)
        assertEquals(schedule, result)
    }

    @Test
    fun `evaluateActiveSchedule resolves overlap by Latest Start Time`() {
        // Schedule A: 12:00 - 14:00 (Starts earlier)
        val scheduleA = ModeSchedule(
            id = 1,
            targetMode = AppMode.WHITELIST,
            startTime = LocalTime.of(12, 0),
            endTime = LocalTime.of(14, 0),
            daysOfWeek = setOf(DayOfWeek.MONDAY)
        )

        // Schedule B: 13:00 - 15:00 (Starts later)
        val scheduleB = ModeSchedule(
            id = 2,
            targetMode = AppMode.BLOCKLIST,
            startTime = LocalTime.of(13, 0),
            endTime = LocalTime.of(15, 0),
            daysOfWeek = setOf(DayOfWeek.MONDAY)
        )

        // At 13:30, both are active. Schedule B should win because it started at 13:00 vs 12:00.
        val now = LocalDateTime.of(2023, 10, 2, 13, 30)
        
        val result = manager.evaluateActiveSchedule(listOf(scheduleA, scheduleB), now)
        assertEquals(scheduleB, result)
    }
}
