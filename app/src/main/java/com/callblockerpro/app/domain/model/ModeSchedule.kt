package com.callblockerpro.app.domain.model

import java.time.DayOfWeek
import java.time.LocalTime

import kotlinx.serialization.Serializable
import com.callblockerpro.app.data.util.LocalTimeSerializer

@Serializable
data class ModeSchedule(
    val id: Long = 0,
    val targetMode: AppMode,
    @Serializable(with = LocalTimeSerializer::class)
    val startTime: LocalTime,
    @Serializable(with = LocalTimeSerializer::class)
    val endTime: LocalTime,
    val daysOfWeek: Set<DayOfWeek>,
    val isEnabled: Boolean = true
)
