package com.callblockerpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.callblockerpro.app.data.local.entity.CallLogEntity
import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {
    @Query("SELECT * FROM call_log ORDER BY timestamp DESC")
    fun getAll(): PagingSource<Int, CallLogEntity>

    @Query("SELECT * FROM call_log WHERE result = :result ORDER BY timestamp DESC")
    fun getAllFiltered(result: com.callblockerpro.app.domain.model.CallResult): PagingSource<Int, CallLogEntity>


    @Query("SELECT * FROM call_log ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLogs(limit: Int): Flow<List<CallLogEntity>>

    @Query("SELECT * FROM call_log WHERE timestamp >= :timestamp")
    fun getLogsSince(timestamp: java.time.Instant): Flow<List<CallLogEntity>>

    @Query("SELECT * FROM call_log ORDER BY timestamp DESC")
    suspend fun getAllList(): List<CallLogEntity>

    @Insert
    suspend fun insert(entry: CallLogEntity): Long

    @Query("DELETE FROM call_log WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM call_log")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM call_log")
    fun getCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM call_log WHERE result = 'BLOCKED'")
    fun getBlockedCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM call_log WHERE result = 'ALLOWED'")
    fun getAllowedCountFlow(): Flow<Int>

    @Query("SELECT COUNT(*) FROM call_log")
    suspend fun countAll(): Int

    @Query("SELECT COUNT(*) FROM call_log WHERE result = 'BLOCKED'")
    suspend fun countBlocked(): Int

    @Query("SELECT COUNT(*) FROM call_log WHERE result = 'ALLOWED'")
    suspend fun countAllowed(): Int
}
