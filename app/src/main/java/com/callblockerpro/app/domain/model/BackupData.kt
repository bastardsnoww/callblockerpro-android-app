package com.callblockerpro.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BackupData(
    val version: Int = 1,
    val timestamp: Long = System.currentTimeMillis(),
    val whitelist: List<WhitelistEntry> = emptyList(),
    val blocklist: List<BlocklistEntry> = emptyList(),
    val callLogs: List<CallLogEntry> = emptyList(),
    val schedules: List<ModeSchedule> = emptyList()
)
