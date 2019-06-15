package com.eemf.sirgoingfar.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Callback;
import android.arch.persistence.db.SupportSQLiteOpenHelper.Configuration;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.RoomOpenHelper;
import android.arch.persistence.room.RoomOpenHelper.Delegate;
import android.arch.persistence.room.util.TableInfo;
import android.arch.persistence.room.util.TableInfo.Column;
import android.arch.persistence.room.util.TableInfo.ForeignKey;
import android.arch.persistence.room.util.TableInfo.Index;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

@SuppressWarnings("unchecked")
public class AppDatabase_Impl extends AppDatabase {
  private volatile RoutineCheckerAppDao _routineCheckerAppDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Routine` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `desc` TEXT, `freqId` INTEGER NOT NULL, `routine_time` INTEGER)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `routine_occurrence` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `routine_id` INTEGER NOT NULL, `status` INTEGER NOT NULL, `occurrence_date` INTEGER, FOREIGN KEY(`routine_id`) REFERENCES `Routine`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
        _db.execSQL("CREATE  INDEX `index_routine_occurrence_routine_id` ON `routine_occurrence` (`routine_id`)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, \"35e7fa536b5de1b0f559413234673c48\")");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `Routine`");
        _db.execSQL("DROP TABLE IF EXISTS `routine_occurrence`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        _db.execSQL("PRAGMA foreign_keys = ON");
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsRoutine = new HashMap<String, TableInfo.Column>(5);
        _columnsRoutine.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRoutine.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsRoutine.put("desc", new TableInfo.Column("desc", "TEXT", false, 0));
        _columnsRoutine.put("freqId", new TableInfo.Column("freqId", "INTEGER", true, 0));
        _columnsRoutine.put("routine_time", new TableInfo.Column("routine_time", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoutine = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesRoutine = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoRoutine = new TableInfo("Routine", _columnsRoutine, _foreignKeysRoutine, _indicesRoutine);
        final TableInfo _existingRoutine = TableInfo.read(_db, "Routine");
        if (! _infoRoutine.equals(_existingRoutine)) {
          throw new IllegalStateException("Migration didn't properly handle Routine(com.eemf.sirgoingfar.database.Routine).\n"
                  + " Expected:\n" + _infoRoutine + "\n"
                  + " Found:\n" + _existingRoutine);
        }
        final HashMap<String, TableInfo.Column> _columnsRoutineOccurrence = new HashMap<String, TableInfo.Column>(4);
        _columnsRoutineOccurrence.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsRoutineOccurrence.put("routine_id", new TableInfo.Column("routine_id", "INTEGER", true, 0));
        _columnsRoutineOccurrence.put("status", new TableInfo.Column("status", "INTEGER", true, 0));
        _columnsRoutineOccurrence.put("occurrence_date", new TableInfo.Column("occurrence_date", "INTEGER", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysRoutineOccurrence = new HashSet<TableInfo.ForeignKey>(1);
        _foreignKeysRoutineOccurrence.add(new TableInfo.ForeignKey("Routine", "CASCADE", "NO ACTION",Arrays.asList("routine_id"), Arrays.asList("id")));
        final HashSet<TableInfo.Index> _indicesRoutineOccurrence = new HashSet<TableInfo.Index>(1);
        _indicesRoutineOccurrence.add(new TableInfo.Index("index_routine_occurrence_routine_id", false, Arrays.asList("routine_id")));
        final TableInfo _infoRoutineOccurrence = new TableInfo("routine_occurrence", _columnsRoutineOccurrence, _foreignKeysRoutineOccurrence, _indicesRoutineOccurrence);
        final TableInfo _existingRoutineOccurrence = TableInfo.read(_db, "routine_occurrence");
        if (! _infoRoutineOccurrence.equals(_existingRoutineOccurrence)) {
          throw new IllegalStateException("Migration didn't properly handle routine_occurrence(com.eemf.sirgoingfar.database.RoutineOccurrence).\n"
                  + " Expected:\n" + _infoRoutineOccurrence + "\n"
                  + " Found:\n" + _existingRoutineOccurrence);
        }
      }
    }, "35e7fa536b5de1b0f559413234673c48", "3197f9aa3ecaf4397af59f25c55e8541");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    return new InvalidationTracker(this, "Routine","routine_occurrence");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    boolean _supportsDeferForeignKeys = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP;
    try {
      if (!_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA foreign_keys = FALSE");
      }
      super.beginTransaction();
      if (_supportsDeferForeignKeys) {
        _db.execSQL("PRAGMA defer_foreign_keys = TRUE");
      }
      _db.execSQL("DELETE FROM `Routine`");
      _db.execSQL("DELETE FROM `routine_occurrence`");
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
  public RoutineCheckerAppDao getDao() {
    if (_routineCheckerAppDao != null) {
      return _routineCheckerAppDao;
    } else {
      synchronized(this) {
        if(_routineCheckerAppDao == null) {
          _routineCheckerAppDao = new RoutineCheckerAppDao_Impl(this);
        }
        return _routineCheckerAppDao;
      }
    }
  }
}
