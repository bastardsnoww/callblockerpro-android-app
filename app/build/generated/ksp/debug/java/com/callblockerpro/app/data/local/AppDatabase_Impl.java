package com.callblockerpro.app.data.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import com.callblockerpro.app.data.local.dao.BackupActivityDao;
import com.callblockerpro.app.data.local.dao.BackupActivityDao_Impl;
import com.callblockerpro.app.data.local.dao.BlocklistDao;
import com.callblockerpro.app.data.local.dao.BlocklistDao_Impl;
import com.callblockerpro.app.data.local.dao.CallLogDao;
import com.callblockerpro.app.data.local.dao.CallLogDao_Impl;
import com.callblockerpro.app.data.local.dao.ModeHistoryDao;
import com.callblockerpro.app.data.local.dao.ModeHistoryDao_Impl;
import com.callblockerpro.app.data.local.dao.ScheduleDao;
import com.callblockerpro.app.data.local.dao.ScheduleDao_Impl;
import com.callblockerpro.app.data.local.dao.WhitelistDao;
import com.callblockerpro.app.data.local.dao.WhitelistDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDatabase_Impl extends AppDatabase {
  private volatile WhitelistDao _whitelistDao;

  private volatile BlocklistDao _blocklistDao;

  private volatile CallLogDao _callLogDao;

  private volatile ScheduleDao _scheduleDao;

  private volatile ModeHistoryDao _modeHistoryDao;

  private volatile BackupActivityDao _backupActivityDao;

  @Override
  @NonNull
  protected SupportSQLiteOpenHelper createOpenHelper(@NonNull final DatabaseConfiguration config) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(config, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS `whitelist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phone_number` TEXT NOT NULL, `name` TEXT, `notes` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_whitelist_phone_number` ON `whitelist` (`phone_number`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `blocklist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phone_number` TEXT NOT NULL, `name` TEXT, `reason` TEXT, `created_at` INTEGER NOT NULL, `updated_at` INTEGER NOT NULL)");
        db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS `index_blocklist_phone_number` ON `blocklist` (`phone_number`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `call_log` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `phone_number` TEXT NOT NULL, `contact_name` TEXT, `timestamp` INTEGER NOT NULL, `result` TEXT NOT NULL, `trigger_mode` TEXT NOT NULL, `reason` TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `schedules` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `target_mode` TEXT NOT NULL, `start_time` TEXT NOT NULL, `end_time` TEXT NOT NULL, `days_of_week` TEXT NOT NULL, `is_enabled` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `mode_history` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `previous_mode` TEXT NOT NULL, `new_mode` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `source` TEXT NOT NULL, `related_schedule_id` INTEGER, FOREIGN KEY(`related_schedule_id`) REFERENCES `schedules`(`id`) ON UPDATE NO ACTION ON DELETE SET NULL )");
        db.execSQL("CREATE INDEX IF NOT EXISTS `index_mode_history_related_schedule_id` ON `mode_history` (`related_schedule_id`)");
        db.execSQL("CREATE TABLE IF NOT EXISTS `backup_activity` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `title` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, `size` TEXT NOT NULL, `isAuto` INTEGER NOT NULL, `isSuccess` INTEGER NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'fd53afed044d3b4623284ca24d1b5886')");
      }

      @Override
      public void dropAllTables(@NonNull final SupportSQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS `whitelist`");
        db.execSQL("DROP TABLE IF EXISTS `blocklist`");
        db.execSQL("DROP TABLE IF EXISTS `call_log`");
        db.execSQL("DROP TABLE IF EXISTS `schedules`");
        db.execSQL("DROP TABLE IF EXISTS `mode_history`");
        db.execSQL("DROP TABLE IF EXISTS `backup_activity`");
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onDestructiveMigration(db);
          }
        }
      }

      @Override
      public void onCreate(@NonNull final SupportSQLiteDatabase db) {
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onCreate(db);
          }
        }
      }

      @Override
      public void onOpen(@NonNull final SupportSQLiteDatabase db) {
        mDatabase = db;
        db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(db);
        final List<? extends RoomDatabase.Callback> _callbacks = mCallbacks;
        if (_callbacks != null) {
          for (RoomDatabase.Callback _callback : _callbacks) {
            _callback.onOpen(db);
          }
        }
      }

      @Override
      public void onPreMigrate(@NonNull final SupportSQLiteDatabase db) {
        DBUtil.dropFtsSyncTriggers(db);
      }

      @Override
      public void onPostMigrate(@NonNull final SupportSQLiteDatabase db) {
      }

      @Override
      @NonNull
      public RoomOpenHelper.ValidationResult onValidateSchema(
          @NonNull final SupportSQLiteDatabase db) {
        final HashMap<String, TableInfo.Column> _columnsWhitelist = new HashMap<String, TableInfo.Column>(6);
        _columnsWhitelist.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelist.put("phone_number", new TableInfo.Column("phone_number", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelist.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelist.put("notes", new TableInfo.Column("notes", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelist.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsWhitelist.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysWhitelist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesWhitelist = new HashSet<TableInfo.Index>(1);
        _indicesWhitelist.add(new TableInfo.Index("index_whitelist_phone_number", true, Arrays.asList("phone_number"), Arrays.asList("ASC")));
        final TableInfo _infoWhitelist = new TableInfo("whitelist", _columnsWhitelist, _foreignKeysWhitelist, _indicesWhitelist);
        final TableInfo _existingWhitelist = TableInfo.read(db, "whitelist");
        if (!_infoWhitelist.equals(_existingWhitelist)) {
          return new RoomOpenHelper.ValidationResult(false, "whitelist(com.callblockerpro.app.data.local.entity.WhitelistEntity).\n"
                  + " Expected:\n" + _infoWhitelist + "\n"
                  + " Found:\n" + _existingWhitelist);
        }
        final HashMap<String, TableInfo.Column> _columnsBlocklist = new HashMap<String, TableInfo.Column>(6);
        _columnsBlocklist.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklist.put("phone_number", new TableInfo.Column("phone_number", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklist.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklist.put("reason", new TableInfo.Column("reason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklist.put("created_at", new TableInfo.Column("created_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBlocklist.put("updated_at", new TableInfo.Column("updated_at", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBlocklist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBlocklist = new HashSet<TableInfo.Index>(1);
        _indicesBlocklist.add(new TableInfo.Index("index_blocklist_phone_number", true, Arrays.asList("phone_number"), Arrays.asList("ASC")));
        final TableInfo _infoBlocklist = new TableInfo("blocklist", _columnsBlocklist, _foreignKeysBlocklist, _indicesBlocklist);
        final TableInfo _existingBlocklist = TableInfo.read(db, "blocklist");
        if (!_infoBlocklist.equals(_existingBlocklist)) {
          return new RoomOpenHelper.ValidationResult(false, "blocklist(com.callblockerpro.app.data.local.entity.BlocklistEntity).\n"
                  + " Expected:\n" + _infoBlocklist + "\n"
                  + " Found:\n" + _existingBlocklist);
        }
        final HashMap<String, TableInfo.Column> _columnsCallLog = new HashMap<String, TableInfo.Column>(7);
        _columnsCallLog.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("phone_number", new TableInfo.Column("phone_number", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("contact_name", new TableInfo.Column("contact_name", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("result", new TableInfo.Column("result", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("trigger_mode", new TableInfo.Column("trigger_mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsCallLog.put("reason", new TableInfo.Column("reason", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysCallLog = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesCallLog = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoCallLog = new TableInfo("call_log", _columnsCallLog, _foreignKeysCallLog, _indicesCallLog);
        final TableInfo _existingCallLog = TableInfo.read(db, "call_log");
        if (!_infoCallLog.equals(_existingCallLog)) {
          return new RoomOpenHelper.ValidationResult(false, "call_log(com.callblockerpro.app.data.local.entity.CallLogEntity).\n"
                  + " Expected:\n" + _infoCallLog + "\n"
                  + " Found:\n" + _existingCallLog);
        }
        final HashMap<String, TableInfo.Column> _columnsSchedules = new HashMap<String, TableInfo.Column>(6);
        _columnsSchedules.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("target_mode", new TableInfo.Column("target_mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("start_time", new TableInfo.Column("start_time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("end_time", new TableInfo.Column("end_time", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("days_of_week", new TableInfo.Column("days_of_week", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSchedules.put("is_enabled", new TableInfo.Column("is_enabled", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSchedules = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSchedules = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSchedules = new TableInfo("schedules", _columnsSchedules, _foreignKeysSchedules, _indicesSchedules);
        final TableInfo _existingSchedules = TableInfo.read(db, "schedules");
        if (!_infoSchedules.equals(_existingSchedules)) {
          return new RoomOpenHelper.ValidationResult(false, "schedules(com.callblockerpro.app.data.local.entity.ScheduleEntity).\n"
                  + " Expected:\n" + _infoSchedules + "\n"
                  + " Found:\n" + _existingSchedules);
        }
        final HashMap<String, TableInfo.Column> _columnsModeHistory = new HashMap<String, TableInfo.Column>(6);
        _columnsModeHistory.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsModeHistory.put("previous_mode", new TableInfo.Column("previous_mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsModeHistory.put("new_mode", new TableInfo.Column("new_mode", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsModeHistory.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsModeHistory.put("source", new TableInfo.Column("source", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsModeHistory.put("related_schedule_id", new TableInfo.Column("related_schedule_id", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysModeHistory = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysModeHistory.add(new TableInfo.ForeignKey("schedules", "SET NULL", "NO ACTION", Arrays.asList("related_schedule_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesModeHistory = new HashSet<TableInfo.Index>(1);
        _indicesModeHistory.add(new TableInfo.Index("index_mode_history_related_schedule_id", false, Arrays.asList("related_schedule_id"), Arrays.asList("ASC")));
        final TableInfo _infoModeHistory = new TableInfo("mode_history", _columnsModeHistory, _foreignKeysModeHistory, _indicesModeHistory);
        final TableInfo _existingModeHistory = TableInfo.read(db, "mode_history");
        if (!_infoModeHistory.equals(_existingModeHistory)) {
          return new RoomOpenHelper.ValidationResult(false, "mode_history(com.callblockerpro.app.data.local.entity.ModeHistoryEntity).\n"
                  + " Expected:\n" + _infoModeHistory + "\n"
                  + " Found:\n" + _existingModeHistory);
        }
        final HashMap<String, TableInfo.Column> _columnsBackupActivity = new HashMap<String, TableInfo.Column>(6);
        _columnsBackupActivity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackupActivity.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackupActivity.put("timestamp", new TableInfo.Column("timestamp", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackupActivity.put("size", new TableInfo.Column("size", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackupActivity.put("isAuto", new TableInfo.Column("isAuto", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsBackupActivity.put("isSuccess", new TableInfo.Column("isSuccess", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysBackupActivity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesBackupActivity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoBackupActivity = new TableInfo("backup_activity", _columnsBackupActivity, _foreignKeysBackupActivity, _indicesBackupActivity);
        final TableInfo _existingBackupActivity = TableInfo.read(db, "backup_activity");
        if (!_infoBackupActivity.equals(_existingBackupActivity)) {
          return new RoomOpenHelper.ValidationResult(false, "backup_activity(com.callblockerpro.app.data.local.entity.BackupActivityEntity).\n"
                  + " Expected:\n" + _infoBackupActivity + "\n"
                  + " Found:\n" + _existingBackupActivity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "fd53afed044d3b4623284ca24d1b5886", "6d468f4ee258c63d4f3e02ee46df0cd4");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(_openCallback).build();
    final SupportSQLiteOpenHelper _helper = config.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  @NonNull
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    final HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "whitelist","blocklist","call_log","schedules","mode_history","backup_activity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    final boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `whitelist`");
      _db.execSQL("DELETE FROM `blocklist`");
      _db.execSQL("DELETE FROM `call_log`");
      _db.execSQL("DELETE FROM `schedules`");
      _db.execSQL("DELETE FROM `mode_history`");
      _db.execSQL("DELETE FROM `backup_activity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = TRUE");
      }
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  @NonNull
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(WhitelistDao.class, WhitelistDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BlocklistDao.class, BlocklistDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(CallLogDao.class, CallLogDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ScheduleDao.class, ScheduleDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(ModeHistoryDao.class, ModeHistoryDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(BackupActivityDao.class, BackupActivityDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  @NonNull
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  @NonNull
  public List<Migration> getAutoMigrations(
      @NonNull final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
    final List<Migration> _autoMigrations = new ArrayList<Migration>();
    return _autoMigrations;
  }

  @Override
  public WhitelistDao whitelistDao() {
    if (_whitelistDao != null) {
      return _whitelistDao;
    } else {
      synchronized(this) {
        if(_whitelistDao == null) {
          _whitelistDao = new WhitelistDao_Impl(this);
        }
        return _whitelistDao;
      }
    }
  }

  @Override
  public BlocklistDao blocklistDao() {
    if (_blocklistDao != null) {
      return _blocklistDao;
    } else {
      synchronized(this) {
        if(_blocklistDao == null) {
          _blocklistDao = new BlocklistDao_Impl(this);
        }
        return _blocklistDao;
      }
    }
  }

  @Override
  public CallLogDao callLogDao() {
    if (_callLogDao != null) {
      return _callLogDao;
    } else {
      synchronized(this) {
        if(_callLogDao == null) {
          _callLogDao = new CallLogDao_Impl(this);
        }
        return _callLogDao;
      }
    }
  }

  @Override
  public ScheduleDao scheduleDao() {
    if (_scheduleDao != null) {
      return _scheduleDao;
    } else {
      synchronized(this) {
        if(_scheduleDao == null) {
          _scheduleDao = new ScheduleDao_Impl(this);
        }
        return _scheduleDao;
      }
    }
  }

  @Override
  public ModeHistoryDao modeHistoryDao() {
    if (_modeHistoryDao != null) {
      return _modeHistoryDao;
    } else {
      synchronized(this) {
        if(_modeHistoryDao == null) {
          _modeHistoryDao = new ModeHistoryDao_Impl(this);
        }
        return _modeHistoryDao;
      }
    }
  }

  @Override
  public BackupActivityDao backupActivityDao() {
    if (_backupActivityDao != null) {
      return _backupActivityDao;
    } else {
      synchronized(this) {
        if(_backupActivityDao == null) {
          _backupActivityDao = new BackupActivityDao_Impl(this);
        }
        return _backupActivityDao;
      }
    }
  }
}
