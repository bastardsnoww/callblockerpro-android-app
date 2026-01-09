package com.callblockerpro.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.callblockerpro.app.data.local.entity.ModeHistoryEntity
import com.callblockerpro.app.data.local.entity.ScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Query("SELECT * FROM schedules")
    fun getAll(): Flow<List<ScheduleEntity>>

    @Query("SELECT * FROM schedules WHERE id = :id")
    suspend fun getById(id: Long): ScheduleEntity?

    @Insert
    suspend fun insert(schedule: ScheduleEntity): Long

    @Update
    suspend fun update(schedule: ScheduleEntity)

    @Query("DELETE FROM schedules WHERE id = :id")
    suspend fun deleteById(id: Long)
}

@Dao
interface ModeHistoryDao {
    @Query("SELECT * FROM mode_history ORDER BY timestamp DESC")
    fun getAll(): Flow<List<ModeHistoryEntity>>

    @Insert
    suspend fun insert(history: ModeHistoryEntity): Long
}
