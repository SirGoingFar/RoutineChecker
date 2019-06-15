package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.text.format.DateUtils

object TimeUtil {

    fun getDuration(start: Long, end: Long): String {

        validateIllegalArgument(start, end)

        var diff = end - start
        val VALUE_0: Long = 0

        if (diff < VALUE_0)
            throw IllegalArgumentException("Time difference can't be NEGATIVE")

        val secondsInMilli: Long = 1000
        val minutesInMilli = secondsInMilli * 60
        val hoursInMilli = minutesInMilli * 60
        val daysInMilli = hoursInMilli * 24

        val elapsedDays = diff / daysInMilli
        diff = diff % daysInMilli

        val elapsedHours = diff / hoursInMilli
        diff = diff % hoursInMilli

        val elapsedMinutes = diff / minutesInMilli
        diff = diff % minutesInMilli

        val elapsedSeconds = diff / secondsInMilli

        //resolve duration
        var duration = ""

        if (elapsedDays > 0) {
            duration = duration + elapsedDays.toString()
            duration = duration + "d "
        }

        if (elapsedHours > 0) {
            duration = duration + elapsedHours.toString()
            duration = duration + "h "
        }

        if (elapsedMinutes > 0) {
            duration = duration + elapsedMinutes.toString()
            duration = duration + "m "
        }

        if (elapsedSeconds > 0) {
            duration = duration + elapsedSeconds.toString()
            duration = duration + "s"
        }

        return duration.trim { it <= ' ' }
    }

    fun getTimeRange(context: Context, start: Long, end: Long): String {

        validateIllegalArgument(start, end)

        return DateUtils.formatDateRange(context, start, end, DateUtils.FORMAT_SHOW_TIME)
    }

    private fun validateIllegalArgument(start: Long, end: Long) {
        val VALUE_0: Long = 0

        if (start < VALUE_0 || end < VALUE_0)
            throw IllegalArgumentException("Invalid 'start' or 'end' value")
    }
}
