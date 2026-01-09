package com.callblockerpro.app.data.local.dao;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.callblockerpro.app.data.local.Converters;
import com.callblockerpro.app.data.local.entity.ModeHistoryEntity;
import com.callblockerpro.app.domain.model.AppMode;
import com.callblockerpro.app.domain.model.ChangeSource;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
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
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class ModeHistoryDao_Impl implements ModeHistoryDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<ModeHistoryEntity> __insertionAdapterOfModeHistoryEntity;

  private final Converters __converters = new Converters();

  public ModeHistoryDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfModeHistoryEntity = new EntityInsertionAdapter<ModeHistoryEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `mode_history` (`id`,`previous_mode`,`new_mode`,`timestamp`,`source`,`related_schedule_id`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final ModeHistoryEntity entity) {
        statement.bindLong(1, entity.getId());
        final String _tmp = __converters.appModeToString(entity.getPreviousMode());
        if (_tmp == null) {
          statement.bindNull(2);
        } else {
          statement.bindString(2, _tmp);
        }
        final String _tmp_1 = __converters.appModeToString(entity.getNewMode());
        if (_tmp_1 == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getTimestamp());
        if (_tmp_2 == null) {
          statement.bindNull(4);
        } else {
          statement.bindLong(4, _tmp_2);
        }
        final String _tmp_3 = __converters.changeSourceToString(entity.getSource());
        if (_tmp_3 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_3);
        }
        if (entity.getRelatedScheduleId() == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, entity.getRelatedScheduleId());
        }
      }
    };
  }

  @Override
  public Object insert(final ModeHistoryEntity history,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfModeHistoryEntity.insertAndReturnId(history);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<ModeHistoryEntity>> getAll() {
    final String _sql = "SELECT * FROM mode_history ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"mode_history"}, new Callable<List<ModeHistoryEntity>>() {
      @Override
      @NonNull
      public List<ModeHistoryEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfPreviousMode = CursorUtil.getColumnIndexOrThrow(_cursor, "previous_mode");
          final int _cursorIndexOfNewMode = CursorUtil.getColumnIndexOrThrow(_cursor, "new_mode");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfSource = CursorUtil.getColumnIndexOrThrow(_cursor, "source");
          final int _cursorIndexOfRelatedScheduleId = CursorUtil.getColumnIndexOrThrow(_cursor, "related_schedule_id");
          final List<ModeHistoryEntity> _result = new ArrayList<ModeHistoryEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final ModeHistoryEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final AppMode _tmpPreviousMode;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfPreviousMode)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfPreviousMode);
            }
            final AppMode _tmp_1 = __converters.fromAppMode(_tmp);
            if (_tmp_1 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
            } else {
              _tmpPreviousMode = _tmp_1;
            }
            final AppMode _tmpNewMode;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfNewMode)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfNewMode);
            }
            final AppMode _tmp_3 = __converters.fromAppMode(_tmp_2);
            if (_tmp_3 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.AppMode', but it was NULL.");
            } else {
              _tmpNewMode = _tmp_3;
            }
            final Instant _tmpTimestamp;
            final Long _tmp_4;
            if (_cursor.isNull(_cursorIndexOfTimestamp)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getLong(_cursorIndexOfTimestamp);
            }
            final Instant _tmp_5 = __converters.fromTimestamp(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.Instant', but it was NULL.");
            } else {
              _tmpTimestamp = _tmp_5;
            }
            final ChangeSource _tmpSource;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfSource)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfSource);
            }
            final ChangeSource _tmp_7 = __converters.fromChangeSource(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'com.callblockerpro.app.domain.model.ChangeSource', but it was NULL.");
            } else {
              _tmpSource = _tmp_7;
            }
            final Long _tmpRelatedScheduleId;
            if (_cursor.isNull(_cursorIndexOfRelatedScheduleId)) {
              _tmpRelatedScheduleId = null;
            } else {
              _tmpRelatedScheduleId = _cursor.getLong(_cursorIndexOfRelatedScheduleId);
            }
            _item = new ModeHistoryEntity(_tmpId,_tmpPreviousMode,_tmpNewMode,_tmpTimestamp,_tmpSource,_tmpRelatedScheduleId);
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

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
