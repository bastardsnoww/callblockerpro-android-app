package com.callblockerpro.app.data.local.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.paging.PagingSource;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.paging.LimitOffsetPagingSource;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.callblockerpro.app.data.local.Converters;
import com.callblockerpro.app.data.local.entity.CallLogEntity;
import com.callblockerpro.app.domain.model.AppMode;
import com.callblockerpro.app.domain.model.CallResult;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class CallLogDao_Impl implements CallLogDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<CallLogEntity> __insertionAdapterOfCallLogEntity;

  private final Converters __converters = new Converters();

  private final SharedSQLiteStatement __preparedStmtOfDeleteById;

  private final SharedSQLiteStatement __preparedStmtOfClearAll;

  public CallLogDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfCallLogEntity = new EntityInsertionAdapter<CallLogEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `call_log` (`id`,`phone_number`,`contact_name`,`timestamp`,`result`,`trigger_mode`,`reason`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final CallLogEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getPhoneNumber());
        if (entity.getContactName() == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.getContactName());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp);
        }
        final String _tmp_1 = __converters.callResultToString(entity.getResult());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        final String _tmp_2 = __converters.appModeToString(entity.getTriggerMode());
        if (_tmp_2 == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, _tmp_2);
        }
        if (entity.getReason() == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.getReason());
        }
      }
    };
    this.__preparedStmtOfDeleteById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM call_log WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfClearAll = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM call_log";
        return _query;
      }
    };
  }

  @Override
  public Object insert(final CallLogEntity entry, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfCallLogEntity.insertAndReturnId(entry);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object clearAll(final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfClearAll.acquire();
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfClearAll.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public PagingSource<Integer, CallLogEntity> getAll() {
    final String _sql = "SELECT * FROM call_log ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new LimitOffsetPagingSource<CallLogEntity>(_statement, __db, "call_log") {
      @Override
      @NonNull
      protected List<CallLogEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(cursor, "phone_number");
        final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(cursor, "contact_name");
        final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
        final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(cursor, "result");
        final int _cursorIndexOfTriggerMode = CursorUtil.getColumnIndexOrThrow(cursor, "trigger_mode");
        final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(cursor, "reason");
        final List<CallLogEntity> _result = new ArrayList<CallLogEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final CallLogEntity _item;
          final long _tmpId;
          _tmpId = cursor.getLong(_cursorIndexOfId);
          final String _tmpPhoneNumber;
          _tmpPhoneNumber = cursor.getString(_cursorIndexOfPhoneNumber);
          final String _tmpContactName;
          if (cursor.isNull(_cursorIndexOfContactName)) {
            _tmpContactName = null;
          } else {
            _tmpContactName = cursor.getString(_cursorIndexOfContactName);
          }
          final Instant _tmpTimestamp;
          final Long _tmp;
          if (cursor.isNull(_cursorIndexOfTimestamp)) {
            _tmp = null;
          } else {
            _tmp = cursor.getLong(_cursorIndexOfTimestamp);
          }
          final Instant _tmp_1 = __converters.fromTimestamp(_tmp);
          if (_tmp_1 == null) {
            throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
          } else {
            _tmpTimestamp = _tmp_1;
          }
          final CallResult _tmpResult;
          final String _tmp_2;
          if (cursor.isNull(_cursorIndexOfResult)) {
            _tmp_2 = null;
          } else {
            _tmp_2 = cursor.getString(_cursorIndexOfResult);
          }
          final CallResult _tmp_3 = __converters.fromCallResult(_tmp_2);
          if (_tmp_3 == null) {
            throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.CallResult', but it was NULL.");
          } else {
            _tmpResult = _tmp_3;
          }
          final AppMode _tmpTriggerMode;
          final String _tmp_4;
          if (cursor.isNull(_cursorIndexOfTriggerMode)) {
            _tmp_4 = null;
          } else {
            _tmp_4 = cursor.getString(_cursorIndexOfTriggerMode);
          }
          final AppMode _tmp_5 = __converters.fromAppMode(_tmp_4);
          if (_tmp_5 == null) {
            throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
          } else {
            _tmpTriggerMode = _tmp_5;
          }
          final String _tmpReason;
          if (cursor.isNull(_cursorIndexOfReason)) {
            _tmpReason = null;
          } else {
            _tmpReason = cursor.getString(_cursorIndexOfReason);
          }
          _item = new CallLogEntity(_tmpId,_tmpPhoneNumber,_tmpContactName,_tmpTimestamp,_tmpResult,_tmpTriggerMode,_tmpReason);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public PagingSource<Integer, CallLogEntity> getAllFiltered(final CallResult result) {
    final String _sql = "SELECT * FROM call_log WHERE result = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.callResultToString(result);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return new LimitOffsetPagingSource<CallLogEntity>(_statement, __db, "call_log") {
      @Override
      @NonNull
      protected List<CallLogEntity> convertRows(@NonNull final Cursor cursor) {
        final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(cursor, "id");
        final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(cursor, "phone_number");
        final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(cursor, "contact_name");
        final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(cursor, "timestamp");
        final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(cursor, "result");
        final int _cursorIndexOfTriggerMode = CursorUtil.getColumnIndexOrThrow(cursor, "trigger_mode");
        final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(cursor, "reason");
        final List<CallLogEntity> _result = new ArrayList<CallLogEntity>(cursor.getCount());
        while (cursor.moveToNext()) {
          final CallLogEntity _item;
          final long _tmpId;
          _tmpId = cursor.getLong(_cursorIndexOfId);
          final String _tmpPhoneNumber;
          _tmpPhoneNumber = cursor.getString(_cursorIndexOfPhoneNumber);
          final String _tmpContactName;
          if (cursor.isNull(_cursorIndexOfContactName)) {
            _tmpContactName = null;
          } else {
            _tmpContactName = cursor.getString(_cursorIndexOfContactName);
          }
          final Instant _tmpTimestamp;
          final Long _tmp_1;
          if (cursor.isNull(_cursorIndexOfTimestamp)) {
            _tmp_1 = null;
          } else {
            _tmp_1 = cursor.getLong(_cursorIndexOfTimestamp);
          }
          final Instant _tmp_2 = __converters.fromTimestamp(_tmp_1);
          if (_tmp_2 == null) {
            throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
          } else {
            _tmpTimestamp = _tmp_2;
          }
          final CallResult _tmpResult;
          final String _tmp_3;
          if (cursor.isNull(_cursorIndexOfResult)) {
            _tmp_3 = null;
          } else {
            _tmp_3 = cursor.getString(_cursorIndexOfResult);
          }
          final CallResult _tmp_4 = __converters.fromCallResult(_tmp_3);
          if (_tmp_4 == null) {
            throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.CallResult', but it was NULL.");
          } else {
            _tmpResult = _tmp_4;
          }
          final AppMode _tmpTriggerMode;
          final String _tmp_5;
          if (cursor.isNull(_cursorIndexOfTriggerMode)) {
            _tmp_5 = null;
          } else {
            _tmp_5 = cursor.getString(_cursorIndexOfTriggerMode);
          }
          final AppMode _tmp_6 = __converters.fromAppMode(_tmp_5);
          if (_tmp_6 == null) {
            throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
          } else {
            _tmpTriggerMode = _tmp_6;
          }
          final String _tmpReason;
          if (cursor.isNull(_cursorIndexOfReason)) {
            _tmpReason = null;
          } else {
            _tmpReason = cursor.getString(_cursorIndexOfReason);
          }
          _item = new CallLogEntity(_tmpId,_tmpPhoneNumber,_tmpContactName,_tmpTimestamp,_tmpResult,_tmpTriggerMode,_tmpReason);
          _result.add(_item);
        }
        return _result;
      }
    };
  }

  @Override
  public Flow<List<CallLogEntity>> getRecentLogs(final int limit) {
    final String _sql = "SELECT * FROM call_log ORDER BY timestamp DESC LIMIT ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, limit);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_log"}, new Callable<List<CallLogEntity>>() {
      @Override
      @NonNull
      public List<CallLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phone_number");
          final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(_cursor, "contact_name");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfTriggerMode = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_mode");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final List<CallLogEntity> _result = new ArrayList<CallLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpContactName;
            if (_cursor.isNull(_cursorIndexOfContactName)) {
              _tmpContactName = null;
            } else {
              _tmpContactName = _cursor.getString(_cursorIndexOfContactName);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Instant _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final CallResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            final CallResult _tmp_3 = __converters.fromCallResult(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.CallResult', but it was NULL.");
            } else {
              _tmpResult = _tmp_3;
            }
            final AppMode _tmpTriggerMode;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfTriggerMode)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfTriggerMode);
            }
            final AppMode _tmp_5 = __converters.fromAppMode(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
            } else {
              _tmpTriggerMode = _tmp_5;
            }
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            _item = new CallLogEntity(_tmpId,_tmpPhoneNumber,_tmpContactName,_tmpTimestamp,_tmpResult,_tmpTriggerMode,_tmpReason);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getAllList(final Continuation<? super List<CallLogEntity>> $completion) {
    final String _sql = "SELECT * FROM call_log ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<CallLogEntity>>() {
      @Override
      @NonNull
      public List<CallLogEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPhoneNumber = CursorUtil.getColumnIndexOrThrow(_cursor, "phone_number");
          final int _cursorIndexOfContactName = CursorUtil.getColumnIndexOrThrow(_cursor, "contact_name");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfResult = CursorUtil.getColumnIndexOrThrow(_cursor, "result");
          final int _cursorIndexOfTriggerMode = CursorUtil.getColumnIndexOrThrow(_cursor, "trigger_mode");
          final int _cursorIndexOfReason = CursorUtil.getColumnIndexOrThrow(_cursor, "reason");
          final List<CallLogEntity> _result = new ArrayList<CallLogEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final CallLogEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpPhoneNumber;
            _tmpPhoneNumber = _cursor.getString(_cursorIndexOfPhoneNumber);
            final String _tmpContactName;
            if (_cursor.isNull(_cursorIndexOfContactName)) {
              _tmpContactName = null;
            } else {
              _tmpContactName = _cursor.getString(_cursorIndexOfContactName);
            }
            final Instant _tmpTimestamp;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Instant _tmp_1 = __converters.fromTimestamp(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_1;
            }
            final CallResult _tmpResult;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfResult)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfResult);
            }
            final CallResult _tmp_3 = __converters.fromCallResult(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.CallResult', but it was NULL.");
            } else {
              _tmpResult = _tmp_3;
            }
            final AppMode _tmpTriggerMode;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfTriggerMode)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfTriggerMode);
            }
            final AppMode _tmp_5 = __converters.fromAppMode(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
            } else {
              _tmpTriggerMode = _tmp_5;
            }
            final String _tmpReason;
            if (_cursor.isNull(_cursorIndexOfReason)) {
              _tmpReason = null;
            } else {
              _tmpReason = _cursor.getString(_cursorIndexOfReason);
            }
            _item = new CallLogEntity(_tmpId,_tmpPhoneNumber,_tmpContactName,_tmpTimestamp,_tmpResult,_tmpTriggerMode,_tmpReason);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Integer> getCountFlow() {
    final String _sql = "SELECT COUNT(*) FROM call_log";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_log"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getBlockedCountFlow() {
    final String _sql = "SELECT COUNT(*) FROM call_log WHERE result = 'BLOCKED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_log"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<Integer> getAllowedCountFlow() {
    final String _sql = "SELECT COUNT(*) FROM call_log WHERE result = 'ALLOWED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"call_log"}, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object countAll(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM call_log";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countBlocked(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM call_log WHERE result = 'BLOCKED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object countAllowed(final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT COUNT(*) FROM call_log WHERE result = 'ALLOWED'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @NonNull
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final Integer _result;
          if (_cursor.moveToFirst()) {
            final int _tmp;
            _tmp = _cursor.getInt(0);
            _result = _tmp;
          } else {
            _result = 0;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
