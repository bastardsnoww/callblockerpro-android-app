package com.callblockerpro.app.domain.repository

import com.callblockerpro.app.domain.model.BlocklistEntry
import com.callblockerpro.app.domain.model.WhitelistEntry
import kotlinx.coroutines.flow.Flow

interface ListRepository {
    fun getWhitelist(): Flow<List<WhitelistEntry>>
    fun getBlocklist(): Flow<List<BlocklistEntry>>

    suspend fun addToWhitelist(entry: WhitelistEntry): Long
    suspend fun addToBlocklist(entry: BlocklistEntry): Long

    suspend fun removeFromWhitelist(id: Long)
    suspend fun removeFromBlocklist(id: Long)

    suspend fun removeMultipleFromWhitelist(ids: List<Long>)
    suspend fun removeMultipleFromBlocklist(ids: List<Long>)

    fun isWhitelisted(phoneNumber: String): Boolean
    fun isBlocked(phoneNumber: String): Boolean

    suspend fun clearWhitelist()
    suspend fun clearBlocklist()
}
