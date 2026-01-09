package com.callblockerpro.app.domain.model

import java.time.Instant

enum class ChangeSource {
    MANUAL,
    SCHEDULE,
    SYSTEM // e.g. restore from backup
}

data class ModeHistory(
    val id: Long = 0,
    val previousMode: AppMode,
    val newMode: AppMode,
    val timestamp: Instant = Instant.now(),
    val source: ChangeSource,
    val relatedScheduleId: Long? = null // If changed by schedule
)
