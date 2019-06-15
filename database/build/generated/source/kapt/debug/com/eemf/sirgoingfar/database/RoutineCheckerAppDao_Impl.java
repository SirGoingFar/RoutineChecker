package com.eemf.sirgoingfar.database;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import java.lang.Long;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SuppressWarnings("unchecked")
public class RoutineCheckerAppDao_Impl implements RoutineCheckerAppDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfRoutine;

  private final DateConverter __dateConverter = new DateConverter();

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfRoutine;

  public RoutineCheckerAppDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfRoutine = new EntityInsertionAdapter<Routine>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `Routine`(`id`,`name`,`desc`,`freqId`,`routine_time`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Routine value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDesc() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDesc());
        }
        stmt.bindLong(4, value.getFreqId());
        final Long _tmp;
        _tmp = __dateConverter.toTimeStamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
      }
    };
    this.__updateAdapterOfRoutine = new EntityDeletionOrUpdateAdapter<Routine>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `Routine` SET `id` = ?,`name` = ?,`desc` = ?,`freqId` = ?,`routine_time` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Routine value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getDesc() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDesc());
        }
        stmt.bindLong(4, value.getFreqId());
        final Long _tmp;
        _tmp = __dateConverter.toTimeStamp(value.getDate());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindLong(5, _tmp);
        }
        stmt.bindLong(6, value.getId());
      }
    };
  }

  @Override
  public void addRoutine(Routine routine) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfRoutine.insert(routine);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void editRoutine(Routine routine) {
    __db.beginTransaction();
    try {
      __updateAdapterOfRoutine.handle(routine);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<Routine>> getAllRoutine() {
    final String _sql = "SELECT * FROM routine";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<Routine>>() {
      private Observer _observer;

      @Override
      protected List<Routine> compute() {
        if (_observer == null) {
          _observer = new Observer("routine") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("name");
          final int _cursorIndexOfDesc = _cursor.getColumnIndexOrThrow("desc");
          final int _cursorIndexOfFreqId = _cursor.getColumnIndexOrThrow("freqId");
          final int _cursorIndexOfDate = _cursor.getColumnIndexOrThrow("routine_time");
          final List<Routine> _result = new ArrayList<Routine>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Routine _item;
            _item = new Routine();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final String _tmpName;
            _tmpName = _cursor.getString(_cursorIndexOfName);
            _item.setName(_tmpName);
            final String _tmpDesc;
            _tmpDesc = _cursor.getString(_cursorIndexOfDesc);
            _item.setDesc(_tmpDesc);
            final int _tmpFreqId;
            _tmpFreqId = _cursor.getInt(_cursorIndexOfFreqId);
            _item.setFreqId(_tmpFreqId);
            final Date _tmpDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfDate);
            }
            _tmpDate = __dateConverter.toDate(_tmp);
            _item.setDate(_tmpDate);
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
    }.getLiveData();
  }

  @Override
  public LiveData<List<RoutineOccurrence>> getAllRoutineOccurrences(int id) {
    final String _sql = "SELECT * FROM routine_occurrence WHERE routine_id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    return new ComputableLiveData<List<RoutineOccurrence>>() {
      private Observer _observer;

      @Override
      protected List<RoutineOccurrence> compute() {
        if (_observer == null) {
          _observer = new Observer("routine_occurrence") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfId = _cursor.getColumnIndexOrThrow("id");
          final int _cursorIndexOfRoutineId = _cursor.getColumnIndexOrThrow("routine_id");
          final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("status");
          final int _cursorIndexOfOccurrenceDate = _cursor.getColumnIndexOrThrow("occurrence_date");
          final List<RoutineOccurrence> _result = new ArrayList<RoutineOccurrence>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final RoutineOccurrence _item;
            _item = new RoutineOccurrence();
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            final int _tmpRoutineId;
            _tmpRoutineId = _cursor.getInt(_cursorIndexOfRoutineId);
            _item.setRoutineId(_tmpRoutineId);
            final int _tmpStatus;
            _tmpStatus = _cursor.getInt(_cursorIndexOfStatus);
            _item.setStatus(_tmpStatus);
            final Date _tmpOccurrenceDate;
            final Long _tmp;
            if (_cursor.isNull(_cursorIndexOfOccurrenceDate)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getLong(_cursorIndexOfOccurrenceDate);
            }
            _tmpOccurrenceDate = __dateConverter.toDate(_tmp);
            _item.setOccurrenceDate(_tmpOccurrenceDate);
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
    }.getLiveData();
  }
}
