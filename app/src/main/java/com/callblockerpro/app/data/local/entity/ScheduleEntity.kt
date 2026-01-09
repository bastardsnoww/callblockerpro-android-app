package com.callblockerpro.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.callblockerpro.app.domain.model.AppMode
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(tableName = "schedules")
data class ScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "target_mode")
    val targetMode: AppMode,
    @ColumnInfo(name = "start_time")
    val startTime: LocalTime,
    @ColumnInfo(name = "end_time")
    val endTime: LocalTime,
    @ColumnInfo(name = "days_of_week")
    val daysOfWeek: Set<DayOfWeek>,
    @ColumnInfo(name = "is_enabled")
    val isEnabled: Boolean = true
)
