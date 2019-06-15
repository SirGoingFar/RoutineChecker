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

        return context.getString(R.string.text_time)
    }

    fun getFreqById(freqId: Int): Frequency? {
        return Frequency.getFrequency(freqId)
    }
}
