package com.callblockerpro.app.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Database migrations for CallBlockerPro.
 *
 * Each migration handles schema changes between versions to preserve user data.
 */

/**
 * Migration from version 1 to version 2.
 *
 * Changes:
 * - Adds 'source' column to blocklist table (manual, auto, community)
 * - Adds 'last_contacted' column to whitelist table
 * - Adds index on phone_number for faster lookups
 */
val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Add source column to blocklist
        database.execSQL(
            "ALTER TABLE blocklist ADD COLUMN source TEXT NOT NULL DEFAULT 'manual'"
        )
        
        // Add last_contacted to whitelist
        database.execSQL(
            "ALTER TABLE whitelist ADD COLUMN last_contacted INTEGER"
        )
        
        // Create indexes for performance
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS index_blocklist_phone_number ON blocklist(phone_number)"
        )
        database.execSQL(
            "CREATE INDEX IF NOT EXISTS index_whitelist_phone_number ON whitelist(phone_number)"
        )
    }
}

/**
 * Array of all migrations in order.
 * Add new migrations here as the app evolves.
 */
val ALL_MIGRATIONS = arrayOf(
    MIGRATION_1_2
)
