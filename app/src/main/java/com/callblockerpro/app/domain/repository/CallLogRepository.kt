package com.callblockerpro.app.domain.repository

import com.callblockerpro.app.domain.model.CallLogEntry
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import com.callblockerpro.app.domain.model.DashboardStats

interface CallLogRepository {
    fun getCallLogs(filter: com.callblockerpro.app.domain.model.CallResult? = null): Flow<PagingData<CallLogEntry>>
    fun getRecentLogs(limit: Int): Flow<List<CallLogEntry>>
    suspend fun getAllCallLogs(): List<CallLogEntry>
    suspend fun addLogEntry(entry: CallLogEntry): Long
    suspend fun clearLogs()
    suspend fun deleteLogEntry(id: Long)
    suspend fun getStats(): DashboardStats
    fun getStatsFlow(): Flow<DashboardStats>
    suspend fun exportLogsToCsv(): String
}
