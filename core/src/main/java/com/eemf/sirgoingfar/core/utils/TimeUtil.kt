package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.text.format.DateUtils

object TimeUtil {

    fun getDuration(start: Long, end: Long): String {

        validateIllegalArgument(start, end)

        var diff = end - start
        val VALUE_0: Long = 0

        if (diff < VALUE_0)
            return "-"

        val secondsInMillis: Long = 1000
        val minutesInMillis = secondsInMillis * 60
        val hoursInMillis = minutesInMillis * 60
        val daysInMillis = hoursInMillis * 24
        val weeksInMillis = daysInMillis * 7
        val monthInMillis = weeksInMillis * 4
        val yearInMillis = monthInMillis * 12

        val elapsedYears = diff / yearInMillis
        diff %= yearInMillis

        val elapsedMonths = diff / monthInMillis
        diff %= monthInMillis

        val elapsedWeeks = diff / weeksInMillis
        diff %= weeksInMillis

        val elapsedDays = diff / daysInMillis
        diff %= daysInMillis

        val elapsedHours = diff / hoursInMillis
        diff %= hoursInMillis

        val elapsedMinutes = diff / minutesInMillis
        diff %= minutesInMillis

        val elapsedSeconds = diff / secondsInMillis

        //resolve duration
        var duration = ""

        if (elapsedYears > 0) {
            duration += elapsedYears.toString()
            duration += "y "
        }

        if (elapsedMonths > 0) {
            duration += elapsedMonths.toString()
            duration += "m "
        }

        if (elapsedWeeks > 0) {
            duration += elapsedWeeks.toString()
            duration += "w "
        }

        if (elapsedDays > 0) {
            duration += elapsedDays.toString()
            duration += "d "
        }

        if (elapsedHours > 0) {
            duration += elapsedHours.toString()
            duration += "h "
        }

        if (elapsedMinutes > 0) {
            duration += elapsedMinutes.toString()
            duration += "m "
        }

        if (elapsedSeconds > 0) {
            duration += elapsedSeconds.toString()
            duration += "s"
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
