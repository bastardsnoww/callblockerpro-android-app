package com.callblockerpro.app.domain.backup

import android.content.Context
import android.net.Uri
import com.callblockerpro.app.domain.repository.BackupRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val backupRepository: BackupRepository,
    private val preferenceManager: com.callblockerpro.app.data.local.PreferenceManager,
    private val cryptographyManager: com.callblockerpro.app.domain.util.CryptographyManager,
    private val driveServiceHelper: com.callblockerpro.app.data.remote.DriveServiceHelper
) {

    suspend fun exportBackup(uri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val encrypt = preferenceManager.isBackupEncryptionEnabled.first()
            context.contentResolver.openOutputStream(uri)?.use { rawOutputStream ->
                val outputStream = if (encrypt) {
                    cryptographyManager.encrypt(rawOutputStream)
                } else {
                    rawOutputStream
                }
                
                val result = backupRepository.createBackup(outputStream)
                if (result.isFailure) {
                    throw result.exceptionOrNull() ?: Exception("Backup failed")
                }
                outputStream.flush()
            } ?: throw Exception("Failed to open output stream")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun importBackup(uri: Uri): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { rawInputStream ->
                val bis = java.io.BufferedInputStream(rawInputStream)
                val inputStream = if (cryptographyManager.isEncrypted(bis)) {
                    cryptographyManager.decrypt(bis)
                } else {
                    bis
                }
                
                val result = backupRepository.restoreBackup(inputStream)
                if (result.isFailure) {
                    throw result.exceptionOrNull() ?: Exception("Restore failed")
                }
            } ?: throw Exception("Failed to open input stream")
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    suspend fun exportToFile(file: java.io.File): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val encrypt = preferenceManager.isBackupEncryptionEnabled.first()
            file.outputStream().use { rawOutputStream ->
                 val outputStream = if (encrypt) {
                    cryptographyManager.encrypt(rawOutputStream)
                } else {
                    rawOutputStream
                }

                val result = backupRepository.createBackup(outputStream)
                if (result.isFailure) {
                    throw result.exceptionOrNull() ?: Exception("Backup failed")
                }
                outputStream.flush()
            }
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getStandardBackupFile(isEncrypted: Boolean): java.io.File {
        val rootDir = context.getExternalFilesDir(null) ?: context.filesDir
        val subFolder = if (isEncrypted) "Encrypted" else "Unencrypted"
        val fileName = if (isEncrypted) "cbp_backup_encrypted.cbp" else "cbp_backup_unencrypted.json"
        
        val dir = java.io.File(java.io.File(rootDir, "CallBlockerPro"), subFolder)
        if (!dir.exists()) dir.mkdirs()
        
        return java.io.File(dir, fileName)
    }

    suspend fun backupToCloud(tempFile: java.io.File, mimeType: String): Result<String> {
        return Result.failure(Exception("Cloud backup removed in Lite version"))
    }
}
