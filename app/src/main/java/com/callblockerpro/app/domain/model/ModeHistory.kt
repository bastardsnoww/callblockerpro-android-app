package com.callblockerpro.app.domain.model

import java.time.Instant



data class ModeHistory(
    val id: Long = 0,
    val previousMode: AppMode,
    val newMode: AppMode,
    val timestamp: Instant = Instant.now(),
    val source: ChangeSource,
    val relatedScheduleId: Long? = null // If changed by schedule
)
