package com.callblockerpro.app.domain.repository

import com.callblockerpro.app.domain.model.BackupMetadata
import kotlinx.coroutines.flow.Flow
import java.io.InputStream
import java.io.OutputStream

interface BackupRepository {
    fun getBackupHistory(): Flow<List<BackupMetadata>>
    
    suspend fun createBackup(outputStream: OutputStream): Result<BackupMetadata>
    suspend fun restoreBackup(inputStream: InputStream): Result<Unit>
    
    suspend fun deleteBackup(id: String)
    

}
