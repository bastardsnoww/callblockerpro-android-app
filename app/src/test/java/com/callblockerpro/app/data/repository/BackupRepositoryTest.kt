package com.callblockerpro.app.data.repository

import com.callblockerpro.app.domain.model.*
import com.callblockerpro.app.domain.repository.CallLogRepository
import com.callblockerpro.app.domain.repository.ListRepository
import com.callblockerpro.app.domain.repository.ScheduleRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class BackupRepositoryTest {

    private val listRepository: ListRepository = mockk(relaxed = true)
    private val callLogRepository: CallLogRepository = mockk(relaxed = true)
    private val scheduleRepository: ScheduleRepository = mockk(relaxed = true)
    private val backupActivityDao: com.callblockerpro.app.data.local.dao.BackupActivityDao = mockk(relaxed = true)
    private lateinit var backupRepository: BackupRepositoryImpl

    @Before
    fun setup() {
        backupRepository = BackupRepositoryImpl(listRepository, callLogRepository, scheduleRepository, backupActivityDao)
        // Mock default flows
        coEvery { listRepository.getWhitelist() } returns flowOf(emptyList())
        coEvery { listRepository.getBlocklist() } returns flowOf(emptyList())
        coEvery { callLogRepository.getAllCallLogs() } returns emptyList()
        coEvery { scheduleRepository.getAllSchedules() } returns flowOf(emptyList())
    }

    @Test
    fun `exportBackup generates valid JSON`() = runBlocking {
        // Arrange
        val whitelist = listOf(WhitelistEntry(phoneNumber = "123", name = "Test"))
        coEvery { listRepository.getWhitelist() } returns flowOf(whitelist)

        val outputStream = ByteArrayOutputStream()

        // Act
        val result = backupRepository.createBackup(outputStream)

        // Assert
        assertTrue(result.isSuccess)
        val json = outputStream.toString()
        assertTrue(json.contains("123"))
        assertTrue(json.contains("Test"))
    }

    @Test
    fun `restoreBackup respects conflicts (Blocklist wins if local)`() = runBlocking {
        // Arrange: valid JSON with a whitelist entry
        val json = """
            {
              "version": 1,
              "timestamp": 123456789,
              "whitelist": [ { "phoneNumber": "999", "name": "Conflict", "id": 0, "notes": null, "createdAt": 123456789, "updatedAt": 123456789 } ],
              "blocklist": [],
              "callLogs": [],
              "schedules": []
            }
        """.trimIndent()
        val inputStream = ByteArrayInputStream(json.toByteArray())

        // Mock: "999" is ALREADY in local blocklist
        coEvery { listRepository.isBlocked("999") } returns true

        // Act
        val result = backupRepository.restoreBackup(inputStream)

        // Assert
        assertTrue(result.isSuccess)
        // verify we DO call it (repository is responsible for rejection)
        coVerify(exactly = 1) { listRepository.addToWhitelist(any()) }
    }

    @Test
    fun `restoreBackup adds non-conflicting entries`() = runBlocking {
        // Arrange
        val json = """
            {
              "version": 1,
              "timestamp": 123456789,
              "whitelist": [ { "phoneNumber": "111", "name": "Safe", "id": 0, "notes": null, "createdAt": 123456789, "updatedAt": 123456789 } ],
              "blocklist": [],
              "callLogs": [],
              "schedules": []
            }
        """.trimIndent()
        val inputStream = ByteArrayInputStream(json.toByteArray())

        coEvery { listRepository.isBlocked("111") } returns false

        // Act
        val result = backupRepository.restoreBackup(inputStream)

        // Assert
        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { listRepository.addToWhitelist(match { it.phoneNumber == "111" && it.name == "Safe" }) }
    }
}
