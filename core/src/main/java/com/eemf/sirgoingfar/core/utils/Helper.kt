package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.content.res.TypedArray
import android.text.TextUtils
import com.eemf.sirgoingfar.core.R
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun convertTypedArrayToIntegerArrayList(typedArray: TypedArray): ArrayList<Int> {
        val list = ArrayList<Int>()
        for (i in 0 until typedArray.length()) {
            list.add(typedArray.getResourceId(i, 0))
        }
        typedArray.recycle()
        return list
    }

    fun hasTimePassed(date: Date): Boolean {
        return Calendar.getInstance().timeInMillis >= date.time
    }

    fun getTimeStringFromDate(context: Context, date: Date?): String? {
        val cal: Calendar = Calendar.getInstance()
        cal.time = date

        val min: Int = cal.get(Calendar.MINUTE)
        var hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val meridian: String = if (hour > 12) "PM" else "AM"

        hour = hour % 12
        if (hour == 0)
            hour = 12

        return context.getString(R.string.text_time, hour, String.format(Locale.getDefault(), "%02d", min), meridian)
    }

    fun getFreqById(freqId: Int): Frequency? {
        return Frequency.getFrequency(freqId)
    }

    fun getRoutineTimeDetail(context: Context, date: Date?, freqId: Int): String? {

        if (date == null)
            return null

        val routineTime = getTimeStringFromDate(context, date)
        val freq = Frequency.getFrequency(freqId)?.label
                ?: context.getString(R.string.text_unavailable)

        return context.getString(R.string.routine_detail_text, freq, routineTime)
    }

    fun getNextRoutineOccurrenceText(context: Context, freqId: Int, date: Date?): String? {
        if (date == null)
            return null

        val timeText = getUpNext(freqId, date)
        return context.getString(R.string.routine_next_occurrence_text, timeText)
    }

    fun computeNextRoutineTime(freqId: Int, date: Date?): Date? {
        var cal: Calendar = Calendar.getInstance()
        cal.isLenient = false
        cal.time = date

        when (freqId) {
            Frequency.HOURLY.id -> cal.set(Calendar.HOUR_OF_DAY, (cal.get(Calendar.HOUR_OF_DAY) + 1))

            Frequency.DAILY.id -> cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) + 1))

            Frequency.WEEKLY.id -> cal.set(Calendar.WEEK_OF_YEAR, (cal.get(Calendar.WEEK_OF_YEAR) + 1))

            Frequency.MONTHLY.id -> cal.set(Calendar.MONTH, (cal.get(Calendar.MONTH) + 1))

            Frequency.YEARLY.id -> cal.set(Calendar.YEAR, (cal.get(Calendar.YEAR) + 1))

            else -> return null
        }

        return cal.time
    }

    fun getUpNext(freqId: Int, date: Date?): String? {
        return TimeUtil.getDuration(Calendar.getInstance().timeInMillis, date!!.time)
    }

    fun getDateString(date: Date?): String? {
        return parseDateLong("EEE, d MMM yyyy", date?.time)
    }

    fun parseDateLong(patternString: String, timeInMillis: Long?): String? {
        if (TextUtils.isEmpty(patternString) || timeInMillis == null || timeInMillis < 0) {
            return null
        }
        val sdf = SimpleDateFormat(patternString, Locale.ENGLISH)
        try {
            return sdf.format(Date(timeInMillis))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}