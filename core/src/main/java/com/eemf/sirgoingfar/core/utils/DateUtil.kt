package com.eemf.sirgoingfar.core.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import android.util.Log

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

import java.util.Calendar.getInstance

object DateUtil {

    val DATE_FORMAT_YYYYmmdd = "yyyy-mm-dd"
    val DATE_FORMAT_yyyyMMdd_HHmmss = "yyyy-MM-dd HH:mm:ss"
    val DATE_FORMAT_EEEEMMdd = "EEEE, MMM, dd"

    val greeting: String?
        get() {
            val c = Calendar.getInstance()
            var greetingText: String? = null
            val timeOfDay = c.get(Calendar.HOUR_OF_DAY)

            if (timeOfDay >= 0 && timeOfDay < 12)
                greetingText = "Good Morning"
            else if (timeOfDay >= 12 && timeOfDay < 16)
                greetingText = "Good Afternoon"
            else if (timeOfDay >= 16 && timeOfDay < 21)
                greetingText = "Good Evening"
            else if (timeOfDay >= 21 && timeOfDay < 24)
                greetingText = "Good Night"

            return greetingText
        }

    val todayDate_string: String
        get() {
            val date_string = parseDateLong("EEE, d MMM yyyy", Calendar.getInstance().timeInMillis)
            return if (date_string == null) "" else "Today $date_string"
        }

    fun toDate(date_string: String?): Date {
        return toDate(DATE_FORMAT_YYYYmmdd, date_string)
    }

    fun toDate(dateFormat: String, date_string: String?): Date {
        val date = Date()

        if (TextUtils.isEmpty(date_string))
            return date

        try {
            val simpleDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
            return simpleDateFormat.parse(date_string)
        } catch (e: Exception) {
            Log.e(DateUtil::class.java!!.getSimpleName(),
                    if (!TextUtils.isEmpty(e.message)) e.message else "null", e)
            e.printStackTrace()
        }

        return date
    }

    fun isExactlyOrAfter24hrs(date: Date?): Boolean {
        return date != null && isExactlyOrAfter24hrs(date.time)
    }

    fun isExactlyOrAfter24hrs(date_millisecond: Long): Boolean {
        val now = Date()
        val twenty_four_hrs_millisecond = TimeUnit.MILLISECONDS.convert(24, TimeUnit.HOURS)
        val now_millisecond = now.time
        return now_millisecond - date_millisecond >= twenty_four_hrs_millisecond
    }

    fun isDayAfter(date: Date): Boolean {
        val now_date = Date()
        val now_cal = getInstance()
        val then_cal = getInstance()
        then_cal.time = date

        if (date.before(now_date)) {
            if (now_cal.get(Calendar.YEAR) > then_cal.get(Calendar.YEAR) || now_cal.get(Calendar.DAY_OF_YEAR) > then_cal.get(Calendar.DAY_OF_YEAR))
                return true
        }

        return false
    }

    fun parseDateString(patternString: String, dateString: String): Date? {
        if (TextUtils.isEmpty(dateString)) {
            return null
        }

        val date: Date? = null
        val sdf = SimpleDateFormat(patternString, Locale.getDefault())
        try {
            return sdf.parse(dateString)
        } catch (e: ParseException) {
            e.printStackTrace()
            return date
        }

    }

    @SuppressLint("SimpleDateFormat")
    fun parseDateLong(patternString: String, timeInMillis: Long): String? {
        if (TextUtils.isEmpty(patternString) || timeInMillis < 0) {
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

    fun getDate_string(date: Date): String {
        val date_string = parseDateLong("EEE, d MMM yyyy", date.time)
        return date_string ?: ""
    }

    fun getTodoDate(date: Date?): Date? {
        if (date == null)
            return date

        val selectedDay_cal = Calendar.getInstance()
        selectedDay_cal.time = date
        selectedDay_cal.set(Calendar.HOUR_OF_DAY, 0)
        selectedDay_cal.set(Calendar.MINUTE, 0)
        selectedDay_cal.set(Calendar.SECOND, 0)
        selectedDay_cal.set(Calendar.MILLISECOND, 0)
        return selectedDay_cal.time
    }

    fun isTodayDate(date: Date): Boolean {
        val today = Calendar.getInstance()
        val date_cal = Calendar.getInstance()
        date_cal.time = date

        return (today.get(Calendar.DAY_OF_MONTH) == date_cal.get(Calendar.DAY_OF_MONTH)
                && today.get(Calendar.MONTH) == date_cal.get(Calendar.MONTH)
                && today.get(Calendar.YEAR) == date_cal.get(Calendar.YEAR))
    }

    fun isExpiredTodoList(todoListDate: Date): Boolean {
        val now = Calendar.getInstance()
        now.set(Calendar.HOUR_OF_DAY, 0)
        now.set(Calendar.MINUTE, 0)
        now.set(Calendar.SECOND, 0)
        now.set(Calendar.MILLISECOND, 0)

        return now.time.after(todoListDate)
    }
}

