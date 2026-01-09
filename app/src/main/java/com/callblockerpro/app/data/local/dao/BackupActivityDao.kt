package com.callblockerpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.callblockerpro.app.data.local.entity.BackupActivityEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BackupActivityDao {
    @Query("SELECT * FROM backup_activity ORDER BY timestamp DESC LIMIT 50")
    fun getRecentActivity(): Flow<List<BackupActivityEntity>>

    @Insert
    suspend fun insertActivity(activity: BackupActivityEntity)

    @Query("DELETE FROM backup_activity")
    suspend fun clearHistory()
}
