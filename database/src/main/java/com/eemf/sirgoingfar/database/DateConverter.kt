package com.eemf.sirgoingfar.database

import android.arch.persistence.room.TypeConverter
import java.util.*

/**
 *
 * The class enables Room to convert Date field(s) in the Entity classes to Long and vice versa
 *
 * */
class DateConverter {

    @TypeConverter
    fun toTimeStamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun toDate(timeStamp: Long?): Date? {
        return if (timeStamp != null) Date(timeStamp) else null
    }
}
