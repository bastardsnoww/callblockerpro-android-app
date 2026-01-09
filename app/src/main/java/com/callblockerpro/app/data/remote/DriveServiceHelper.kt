package com.callblockerpro.app.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DriveServiceHelper @Inject constructor() {
    suspend fun uploadFile(filePath: String, mimeType: String): Result<String> {
        return Result.failure(Exception("Drive sync removed"))
    }
    
    suspend fun downloadFile(fileId: String, targetPath: String): Result<Boolean> {
        return Result.failure(Exception("Drive sync removed"))
    }
}
