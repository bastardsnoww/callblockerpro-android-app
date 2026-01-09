package com.callblockerpro.app.domain.model

import kotlinx.serialization.Serializable

@Serializable
enum class AppMode {
    NEUTRAL,
    WHITELIST,
    BLOCKLIST;

    val displayName: String
        get() = when (this) {
            NEUTRAL -> "NORMAL"
            WHITELIST -> "WHITELIST"
            BLOCKLIST -> "BLOCKLIST"
        }
}
