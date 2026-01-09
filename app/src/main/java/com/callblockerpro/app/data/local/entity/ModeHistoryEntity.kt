package com.callblockerpro.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.ChangeSource
import java.time.Instant

@Entity(
    tableName = "mode_history",
    foreignKeys = [
        ForeignKey(
            entity = ScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["related_schedule_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class ModeHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "previous_mode")
    val previousMode: AppMode,
    @ColumnInfo(name = "new_mode")
    val newMode: AppMode,
    val timestamp: Instant = Instant.now(),
    val source: ChangeSource,
    @ColumnInfo(name = "related_schedule_id", index = true)
    val relatedScheduleId: Long?
)
