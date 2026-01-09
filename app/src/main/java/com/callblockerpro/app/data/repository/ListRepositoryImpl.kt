package com.callblockerpro.app.data.repository

import com.callblockerpro.app.data.local.dao.BlocklistDao
import com.callblockerpro.app.data.local.dao.WhitelistDao
import com.callblockerpro.app.data.local.entity.BlocklistEntity
import com.callblockerpro.app.data.local.entity.WhitelistEntity
import com.callblockerpro.app.data.mapper.toDomain
import com.callblockerpro.app.data.mapper.toEntity
import com.callblockerpro.app.domain.exception.ListConflictException
import com.callblockerpro.app.domain.model.BlocklistEntry
import com.callblockerpro.app.domain.model.WhitelistEntry
import com.callblockerpro.app.domain.repository.ListRepository
import com.callblockerpro.app.domain.util.PhoneNumberUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ListRepositoryImpl @Inject constructor(
    private val whitelistDao: WhitelistDao,
    private val blocklistDao: BlocklistDao
) : ListRepository {

    // Memory Cache for O(1) lookups during call screening
    private val whitelistCache = MutableStateFlow<Set<String>>(emptySet())
    private val blocklistCache = MutableStateFlow<Set<String>>(emptySet())
    
    // Scope for background cache updates
    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    init {
        // Hydrate caches on startup
        repositoryScope.launch {
            whitelistDao.getAll().collect { list ->
                whitelistCache.value = list.map { it.phoneNumber }.toSet()
            }
        }
        repositoryScope.launch {
            blocklistDao.getAll().collect { list ->
                blocklistCache.value = list.map { it.phoneNumber }.toSet()
            }
        }
    }

    override fun getWhitelist(): Flow<List<WhitelistEntry>> {
        return whitelistDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    override fun getBlocklist(): Flow<List<BlocklistEntry>> {
        return blocklistDao.getAll().map { list ->
            list.map { it.toDomain() }
        }
    }

    override suspend fun addToWhitelist(entry: WhitelistEntry): Long {
        // Validate and normalize the phone number
        val normalizedNumber = PhoneNumberUtils.normalize(entry.phoneNumber)
            ?: throw IllegalArgumentException(
                "Invalid phone number: '${entry.phoneNumber}'. Please enter a valid phone number."
            )
        
        // Check if already in whitelist (prevents duplicates)
        if (whitelistCache.value.contains(normalizedNumber)) {
            throw ListConflictException(
                "This number is already in your Allowlist."
            )
        }
        
        // Check if in blocklist (prevents conflict)
        if (blocklistCache.value.contains(normalizedNumber)) {
            throw ListConflictException(
                "This number is in your Blocklist. Remove it from Blocklist first."
            )
        }
        
        // Save with normalized number
        val entity = entry.toEntity().copy(phoneNumber = normalizedNumber)
        return whitelistDao.insert(entity)
    }


    override suspend fun addToBlocklist(entry: BlocklistEntry): Long {
        // Validate and normalize the phone number
        val normalizedNumber = PhoneNumberUtils.normalize(entry.phoneNumber)
            ?: throw IllegalArgumentException(
                "Invalid phone number: '${entry.phoneNumber}'. Please enter a valid phone number."
            )

        // Check if already in blocklist (prevents duplicates)
        if (blocklistCache.value.contains(normalizedNumber)) {
            throw ListConflictException(
                "This number is already in your Blocklist."
            )
        }
        
        // Check if in whitelist (prevents conflict)
        if (whitelistCache.value.contains(normalizedNumber)) {
            throw ListConflictException(
                "This number is in your Allowlist. Remove it from Allowlist first."
            )
        }

        // Save with normalized number
        val entity = entry.toEntity().copy(phoneNumber = normalizedNumber)
        return blocklistDao.insert(entity)
    }

    override suspend fun removeFromWhitelist(id: Long) {
        whitelistDao.deleteById(id)
    }

    override suspend fun removeFromBlocklist(id: Long) {
        blocklistDao.deleteById(id)
    }

    override suspend fun removeMultipleFromWhitelist(ids: List<Long>) {
        whitelistDao.deleteByIds(ids)
    }

    override suspend fun removeMultipleFromBlocklist(ids: List<Long>) {
        blocklistDao.deleteByIds(ids)
    }

    override fun isWhitelisted(phoneNumber: String): Boolean {
        // Optimization: Check raw number against cache first if it looks standard (avoid normalization cost)
        if (whitelistCache.value.contains(phoneNumber)) return true
        
        // Fallback: Normalize and check
        val normalized = PhoneNumberUtils.normalize(phoneNumber) ?: return false
        return whitelistCache.value.contains(normalized)
    }

    override fun isBlocked(phoneNumber: String): Boolean {
        // Optimization: Check raw number against cache first
        if (blocklistCache.value.contains(phoneNumber)) return true

        // Fallback: Normalize and check
        val normalized = PhoneNumberUtils.normalize(phoneNumber) ?: return false
        return blocklistCache.value.contains(normalized)
    }

    override suspend fun clearWhitelist() {
        whitelistDao.deleteAll()
    }

    override suspend fun clearBlocklist() {
        blocklistDao.deleteAll()
    }
}

