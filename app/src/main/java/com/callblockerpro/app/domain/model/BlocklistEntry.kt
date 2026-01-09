package com.callblockerpro.app.domain.model

import java.time.Instant

import kotlinx.serialization.Serializable
import com.callblockerpro.app.data.util.InstantSerializer

@Serializable
data class BlocklistEntry(
    val id: Long = 0,
    val phoneNumber: String, // E.164 format preferred
    val name: String?,
    val reason: String? = null,
    @Serializable(with = InstantSerializer::class)
    val createdAt: Instant = Instant.now(),
    @Serializable(with = InstantSerializer::class)
    val updatedAt: Instant = Instant.now()
)
