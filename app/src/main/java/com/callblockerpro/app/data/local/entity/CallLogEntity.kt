package com.callblockerpro.app.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallResult
import java.time.Instant

@Entity(tableName = "call_log")
data class CallLogEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,
    @ColumnInfo(name = "contact_name")
    val contactName: String?,
    val timestamp: Instant = Instant.now(),
    val result: CallResult,
    @ColumnInfo(name = "trigger_mode")
    val triggerMode: AppMode,
    val reason: String?
)
