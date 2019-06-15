package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.content.res.TypedArray
import com.eemf.sirgoingfar.core.R
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

    fun hasTimePassed(activityStartTime: Date): Boolean {
        return Calendar.getInstance().timeInMillis > activityStartTime.time
    }

    fun getTimeStringFromDate(context: Context, date: Date?): String? {
        val cal: Calendar = Calendar.getInstance()
        cal.time = date

        val min: Int = cal.get(Calendar.MINUTE)
        val hour: Int = cal.get(Calendar.HOUR_OF_DAY)
        val meridian: String = if (hour > 12) "PM" else "AM"

        return context.getString(R.string.text_time, hour % 12, String.format(Locale.getDefault(), "%02d", min), meridian)
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

        val timeText = getUpNext(context, freqId, date)
        return context.getString(R.string.routine_next_occurrence_text, timeText)
    }

    fun getUpNext(context: Context, freqId: Int, date: Date?): String? {

        val cal: Calendar = Calendar.getInstance()
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

        return TimeUtil.getDuration(Calendar.getInstance().timeInMillis, cal.timeInMillis)
    }
}