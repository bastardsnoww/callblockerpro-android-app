package com.callblockerpro.app.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "backup_activity")
data class BackupActivityEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String, // e.g., "Cloud Backup", "Local Restore"
    val timestamp: Instant,
    val size: String, // e.g., "124 KB"
    val isAuto: Boolean,
    val isSuccess: Boolean = true
)
