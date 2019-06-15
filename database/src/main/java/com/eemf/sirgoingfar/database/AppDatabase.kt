package com.eemf.sirgoingfar.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context

@Database(entities = [Routine::class, RoutineOccurrence::class], version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val dao: RoutineCheckerAppDao

    companion object {
        private val LOCK = Any()
        private val DATABASE_NAME = "routine_app_db"
        private var sInstance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (sInstance == null) {
                synchronized(LOCK) {
                    sInstance = Room.databaseBuilder<AppDatabase>(context.applicationContext,
                            AppDatabase::class.java, DATABASE_NAME)
                            .build()
                }
            }

            return sInstance
        }
    }
}
