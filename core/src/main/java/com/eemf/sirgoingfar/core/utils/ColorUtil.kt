package com.eemf.sirgoingfar.core.utils

import android.content.Context
import com.eemf.sirgoingfar.core.R
import java.util.*

object ColorUtil {

    fun getColorCode(context: Context): ArrayList<Int> {
        val typedArray = context.resources.obtainTypedArray(R.array.progress_color_codes)
        return ArrayList(Helper.convertTypedArrayToIntegerArrayList(typedArray))
    }

    fun getColor(position: Int): Int {
        val colorCodes = App.colorCodes
        return colorCodes!![position % colorCodes.size]
    }

}
