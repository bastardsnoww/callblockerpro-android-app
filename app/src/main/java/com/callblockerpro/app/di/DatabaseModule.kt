package com.callblockerpro.app.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.callblockerpro.app.data.local.AppDatabase
import com.callblockerpro.app.data.local.dao.*
import com.callblockerpro.app.data.local.Migrations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import java.security.SecureRandom
import android.util.Base64
import javax.inject.Singleton
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        // Generate or retrieve secure passphrase for encryption
        val passphrase = getOrCreateDatabasePassphrase(context)
        
        return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "call_blocker_db"
            )
            // CRITICAL: SQLCipher encryption for call data privacy
            .openHelperFactory(SupportFactory(passphrase))
            
            // Integrate migrations for schema updates without data loss
            .addMigrations(
                Migrations.MIGRATION_1_2,
                Migrations.MIGRATION_2_3
            )
            
            // Database validation callback
            .addCallback(object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    super.onOpen(db)
                    // Validate database integrity
                    if (!Migrations.validateDatabase(db)) {
                        Log.e("Database", "Database integrity check failed!")
                    }
                }
            })
            
            // NOTE: Removed fallbackToDestructiveMigration for production safety
            // Migrations will preserve user data
            .build()
    }
    
    /**
     * Generate or retrieve database encryption passphrase
     * Stored in EncryptedSharedPreferences for security
     */
    private fun getOrCreateDatabasePassphrase(context: Context): ByteArray {
        val prefs = EncryptedSharedPreferences.create(
            "secure_prefs",
            "callblocker_master_key",
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        
        var passphrase = prefs.getString("db_passphrase", null)
        
        if (passphrase == null) {
            // Generate cryptographically secure random passphrase
            val random = SecureRandom()
            val passphraseBytes = ByteArray(32) // 256-bit key
            random.nextBytes(passphraseBytes)
            
            passphrase = Base64.encodeToString(passphraseBytes, Base64.NO_WRAP)
            
            prefs.edit()
                .putString("db_passphrase", passphrase)
                .apply()
        }
        
        return Base64.decode(passphrase, Base64.NO_WRAP)
    }

    @Provides
    fun provideWhitelistDao(db: AppDatabase): WhitelistDao = db.whitelistDao()

    @Provides
    fun provideBlocklistDao(db: AppDatabase): BlocklistDao = db.blocklistDao()

    @Provides
    fun provideCallLogDao(db: AppDatabase): CallLogDao = db.callLogDao()

    @Provides
    fun provideScheduleDao(db: AppDatabase): ScheduleDao = db.scheduleDao()

    @Provides
    fun provideModeHistoryDao(db: AppDatabase): ModeHistoryDao = db.modeHistoryDao()

    @Provides
    fun provideBackupActivityDao(db: AppDatabase): BackupActivityDao = db.backupActivityDao()
}
