package com.callblockerpro.app.domain.model

import java.time.Instant

data class BackupMetadata(
    val id: String, // unique filename or UUID
    val timestamp: Instant,
    val version: Int, // Schema version
    val deviceName: String,
    val recordCount: Int,
    val sizeBytes: Long
)
