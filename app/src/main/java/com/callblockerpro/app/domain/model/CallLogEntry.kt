package com.callblockerpro.app.domain.model

import java.time.Instant

import kotlinx.serialization.Serializable
import com.callblockerpro.app.data.util.InstantSerializer

@Serializable
enum class CallResult {
    ALLOWED,
    BLOCKED,
    SILENCED,
    MISSED,
    OUTGOING
}

@androidx.compose.runtime.Immutable
@Serializable
data class CallLogEntry(
    val id: Long = 0,
    val phoneNumber: String,
    val contactName: String?,
    @Serializable(with = InstantSerializer::class)
    val timestamp: Instant = Instant.now(),
    val result: CallResult,
    val triggerMode: AppMode, // What mode was active when this call happened
    val reason: String? = null // e.g. "Matched Blocklist", "Not in Whitelist"
)
