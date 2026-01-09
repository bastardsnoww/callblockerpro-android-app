package com.callblockerpro.app.data.repository

import com.callblockerpro.app.domain.model.*
import com.callblockerpro.app.domain.repository.*
import com.callblockerpro.app.domain.util.PhoneNumberUtils
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.time.Instant
import java.time.LocalTime
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BackupRepositoryImpl @Inject constructor(
    private val listRepository: ListRepository,
    private val callLogRepository: CallLogRepository,
    private val scheduleRepository: ScheduleRepository,
    private val backupActivityDao: com.callblockerpro.app.data.local.dao.BackupActivityDao
) : BackupRepository {

    // Configure Kotlinx Serialization
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    override fun getBackupHistory(): kotlinx.coroutines.flow.Flow<List<BackupMetadata>> {
        // Not implementing file history scanning for this scope, returning empty
        return kotlinx.coroutines.flow.flowOf(emptyList())
    }

    override suspend fun createBackup(outputStream: OutputStream): Result<BackupMetadata> {
        return try {
            val whitelist = listRepository.getWhitelist().first()
            val blocklist = listRepository.getBlocklist().first()
            val logs = callLogRepository.getAllCallLogs()
            val schedules = scheduleRepository.getAllSchedules().first()

            val backupData = BackupData(
                whitelist = whitelist,
                blocklist = blocklist,
                callLogs = logs,
                schedules = schedules
            )

            withContext(Dispatchers.IO) {
                OutputStreamWriter(outputStream).use { writer ->
                    val jsonString = json.encodeToString(backupData)
                    writer.write(jsonString)
                }
            }

            Result.success(
                BackupMetadata(
                    id = "new",
                    timestamp = Instant.now(),
                    version = 1,
                    deviceName = "Local",
                    recordCount = whitelist.size + blocklist.size,
                    sizeBytes = 0 // can't easily calculate without counting bytes
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun restoreBackup(inputStream: InputStream): Result<Unit> {
        return try {
            val backupData = withContext(Dispatchers.IO) {
                InputStreamReader(inputStream).use { reader ->
                    val jsonString = reader.readText()
                    json.decodeFromString<BackupData>(jsonString)
                }
            }

            // Fetch existing data for de-duplication
            val existingLogs = callLogRepository.getAllCallLogs()
            val existingSchedules = scheduleRepository.getAllSchedules().first()

            // Restore Whitelist
            backupData.whitelist.forEach { entry ->
                try {
                    listRepository.addToWhitelist(entry)
                } catch (e: Exception) { /* Skip duplicates/conflicts */ }
            }

            // Restore Blocklist
            backupData.blocklist.forEach { entry ->
                try {
                    listRepository.addToBlocklist(entry)
                } catch (e: Exception) { /* Skip duplicates/conflicts */ }
            }

            // Restore Logs - Merge with De-duplication
            backupData.callLogs.forEach { log ->
                val isDuplicate = existingLogs.any { existing ->
                    existing.phoneNumber == log.phoneNumber &&
                    existing.timestamp == log.timestamp &&
                    existing.result == log.result
                }
                if (!isDuplicate) {
                    callLogRepository.addLogEntry(log.copy(id = 0))
                }
            }

            // Restore Schedules - Merge with De-duplication
            backupData.schedules.forEach { schedule ->
                val isDuplicate = existingSchedules.any { existing ->
                    existing.startTime == schedule.startTime &&
                    existing.endTime == schedule.endTime &&
                    existing.daysOfWeek == schedule.daysOfWeek &&
                    existing.targetMode == schedule.targetMode
                }
                if (!isDuplicate) {
                    scheduleRepository.addSchedule(schedule.copy(id = 0))
                }
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteBackup(id: String) {
        // No-op
    }



    private fun formatTimestamp(instant: Instant): String {
        val now = Instant.now()
        val duration = java.time.Duration.between(instant, now)
        return when {
            duration.toMinutes() < 1 -> "Just now"
            duration.toHours() < 1 -> "${duration.toMinutes()}m ago"
            duration.toDays() < 1 -> "${duration.toHours()}h ago"
            else -> java.time.format.DateTimeFormatter.ofPattern("MMM d, h:mm a")
                .withZone(java.time.ZoneId.systemDefault())
                .format(instant)
        }
    }
}
