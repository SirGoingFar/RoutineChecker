package com.eemf.sirgoingfar.database

import android.arch.persistence.room.TypeConverter

import java.util.Date

object DateConverter {

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return if (timeStamp != null) Date(timeStamp) else null
    }
}
