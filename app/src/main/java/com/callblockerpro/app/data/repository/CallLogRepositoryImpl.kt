package com.callblockerpro.app.data.repository

import com.callblockerpro.app.data.local.dao.CallLogDao
import com.callblockerpro.app.data.local.entity.CallLogEntity
import com.callblockerpro.app.domain.logic.DecisionReason
import com.callblockerpro.app.domain.model.AppMode
import com.callblockerpro.app.domain.model.CallLogEntry
import com.callblockerpro.app.domain.model.CallResult
import com.callblockerpro.app.domain.repository.CallLogRepository
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class CallLogRepositoryImpl @Inject constructor(
    private val callLogDao: CallLogDao
) : CallLogRepository {

    override fun getCallLogs(filter: CallResult?): Flow<PagingData<CallLogEntry>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { 
                if (filter == null) {
                    callLogDao.getAll()
                } else {
                    callLogDao.getAllFiltered(filter)
                }
            }
        ).flow.map { pagingData ->
            pagingData.map { entity ->
                CallLogEntry(
                    id = entity.id,
                    phoneNumber = entity.phoneNumber,
                    contactName = entity.contactName,
                    timestamp = entity.timestamp,
                    result = entity.result,
                    triggerMode = entity.triggerMode,
                    reason = entity.reason
                )
            }
        }
    }

    override fun getRecentLogs(limit: Int): Flow<List<CallLogEntry>> {
        return callLogDao.getRecentLogs(limit).map { list ->
            list.map { entity ->
                CallLogEntry(
                    id = entity.id,
                    phoneNumber = entity.phoneNumber,
                    contactName = entity.contactName,
                    timestamp = entity.timestamp,
                    result = entity.result,
                    triggerMode = entity.triggerMode,
                    reason = entity.reason
                )
            }
        }
    }

    override suspend fun getAllCallLogs(): List<CallLogEntry> {
        return callLogDao.getAllList().map { entity ->
            CallLogEntry(
                id = entity.id,
                phoneNumber = entity.phoneNumber,
                contactName = entity.contactName,
                timestamp = entity.timestamp,
                result = entity.result,
                triggerMode = entity.triggerMode,
                reason = entity.reason
            )
        }
    }

    override suspend fun addLogEntry(entry: CallLogEntry): Long {
        val entity = CallLogEntity(
            phoneNumber = entry.phoneNumber,
            contactName = entry.contactName,
            timestamp = entry.timestamp,
            result = entry.result,
            triggerMode = entry.triggerMode,
            reason = entry.reason
        )
        return callLogDao.insert(entity)
    }

    override suspend fun clearLogs() {
        callLogDao.clearAll()
    }

    override suspend fun deleteLogEntry(id: Long) {
        callLogDao.deleteById(id)
    }

    override suspend fun getStats(): com.callblockerpro.app.domain.model.DashboardStats {
        val total = callLogDao.countAll()
        val blocked = callLogDao.countBlocked()
        val allowed = callLogDao.countAllowed()
        return com.callblockerpro.app.domain.model.DashboardStats(total, blocked, allowed, listOf(0.4f, 0.6f, 0.3f, 0.8f, 0.5f, 0.9f, 0.7f))
    }

    override fun getStatsFlow(): Flow<com.callblockerpro.app.domain.model.DashboardStats> {
        return combine(
            callLogDao.getCountFlow(),
            callLogDao.getBlockedCountFlow(),
            callLogDao.getAllowedCountFlow()
        ) { total, blocked, allowed ->
            // Simulate weekly activity data
            val weeklyData = listOf(0.4f, 0.6f, 0.3f, 0.8f, 0.5f, 0.9f, 0.7f) 
            com.callblockerpro.app.domain.model.DashboardStats(total, blocked, allowed, weeklyData)
        }
    }

    override suspend fun exportLogsToCsv(): String {
        val logs = getAllCallLogs()
        val csv = StringBuilder()
        csv.append("ID,PhoneNumber,ContactName,Timestamp,Result,Mode,Reason\n")
        logs.forEach { log ->
            csv.append("${log.id},${log.phoneNumber},${log.contactName ?: ""},${log.timestamp},${log.result},${log.triggerMode},${log.reason ?: ""}\n")
        }
        return csv.toString()
    }
}
