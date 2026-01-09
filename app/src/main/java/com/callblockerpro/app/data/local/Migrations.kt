package com.callblockerpro.app.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import java.io.File

/**
 * Database migration framework for CallBlockerPro
 * Handles schema changes across app versions without data loss
 */
object Migrations {
    
    /**
     * Migration from version 1 to version 2
     * Example: Adding timestamp columns to lists
     */
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add created_at timestamp to blocklist_entries
            database.execSQL(
                "ALTER TABLE blocklist_entries ADD COLUMN created_at INTEGER NOT NULL DEFAULT 0"
            )
            
            // Add created_at timestamp to whitelist_entries
            database.execSQL(
                "ALTER TABLE whitelist_entries ADD COLUMN created_at INTEGER NOT NULL DEFAULT 0"
            )
        }
    }
    
    /**
     * Migration from version 2 to version 3
     * Example: Adding notes field to lists
     */
    val MIGRATION_2_3 = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // Add notes column to blocklist_entries
            database.execSQL(
                "ALTER TABLE blocklist_entries ADD COLUMN notes TEXT"
            )
            
            // Add notes column to whitelist_entries
            database.execSQL(
                "ALTER TABLE whitelist_entries ADD COLUMN notes TEXT"
            )
        }
    }
    
    /**
     * Backup the database before migration
     * Called automatically before applying migrations
     * 
     * @param context Application context
     * @param currentVersion Current database version
     */
    fun backupDatabase(context: Context, currentVersion: Int) {
        try {
            val dbPath = context.getDatabasePath("call_blocker_db")
            if (dbPath.exists()) {
                val backupDir = File(context.filesDir, "db_backups")
                backupDir.mkdirs()
                
                val timestamp = System.currentTimeMillis()
                val backupPath = File(backupDir, "backup_v${currentVersion}_${timestamp}.db")
                
                dbPath.copyTo(backupPath, overwrite = true)
                
                // Keep only last 3 backups
                backupDir.listFiles()
                    ?.sortedByDescending { it.lastModified() }
                    ?.drop(3)
                    ?.forEach { it.delete() }
            }
        } catch (e: Exception) {
            // Log error but don't crash - migration will still proceed
            android.util.Log.e("Migrations", "Failed to backup database", e)
        }
    }
    
    /**
     * Validate database integrity after migration
     * 
     * @param database The database to validate
     * @return True if database is valid, false otherwise
     */
    fun validateDatabase(database: SupportSQLiteDatabase): Boolean {
        return try {
            // Run PRAGMA integrity_check
            val cursor = database.query("PRAGMA integrity_check")
            cursor.use {
                if (it.moveToFirst()) {
                    val result = it.getString(0)
                    result == "ok"
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("Migrations", "Database validation failed", e)
            false
        }
    }
}
