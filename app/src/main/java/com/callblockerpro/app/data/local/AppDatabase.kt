package com.callblockerpro.app.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.callblockerpro.app.data.local.dao.*
import com.callblockerpro.app.data.local.entity.*

@Database(
    entities = [
        WhitelistEntity::class,
        BlocklistEntity::class,
        CallLogEntity::class,
        ScheduleEntity::class,
        ModeHistoryEntity::class,
        BackupActivityEntity::class
    ],
    version = 4,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun whitelistDao(): WhitelistDao
    abstract fun blocklistDao(): BlocklistDao
    abstract fun callLogDao(): CallLogDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun modeHistoryDao(): ModeHistoryDao
    abstract fun backupActivityDao(): BackupActivityDao
}
