package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.content.res.Configuration
import android.util.DisplayMetrics
import android.util.TypedValue

class DisplayUtil(private val mContext: Context) {

    val displayMetrics: DisplayMetrics
        get() = mContext.resources.displayMetrics

    private val configuration: Configuration
        get() = mContext.resources.configuration

    val width: Int
        get() = displayMetrics.widthPixels

    val height: Int
        get() = displayMetrics.heightPixels

    val xdpi: Float
        get() = displayMetrics.xdpi

    val ydpi: Float
        get() = displayMetrics.ydpi

    val density: Float
        get() = displayMetrics.density

    val densityDpi: Int
        get() = displayMetrics.densityDpi

    val densityDpiString: String
        get() {
            val dpi = densityDpi
            if (dpi <= DisplayMetrics.DENSITY_LOW) {
                return "low"
            }

            if (dpi <= DisplayMetrics.DENSITY_MEDIUM) {
                return "medium"
            }

            if (dpi <= DisplayMetrics.DENSITY_HIGH) {
                return "high"
            }

            if (dpi <= DisplayMetrics.DENSITY_XHIGH) {
                return "xhigh"
            }

            return if (dpi <= DisplayMetrics.DENSITY_XXHIGH) {
                "xxhigh"
            } else "xxxhigh"

        }

    val scaledDensity: Float
        get() = displayMetrics.scaledDensity

    val screenWidth: Int
        get() = configuration.screenWidthDp

    val screenHeight: Int
        get() = configuration.screenHeightDp

    val screenSizeType: String
        get() {
            val type: String
            when (configuration.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
                Configuration.SCREENLAYOUT_SIZE_SMALL -> type = "small"
                Configuration.SCREENLAYOUT_SIZE_NORMAL -> type = "normal"
                Configuration.SCREENLAYOUT_SIZE_LARGE -> type = "large"
                Configuration.SCREENLAYOUT_SIZE_XLARGE -> type = "xlarge"
                else -> type = "unknown"
            }

            return type
        }

    fun dp(f: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f, displayMetrics)
    }

    fun dp(i: Int): Float {
        return dp(i.toFloat())
    }

    fun sp(f: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, f, displayMetrics)
    }

    fun sp(i: Int): Float {
        return sp(i.toFloat())
    }

    fun px(f: Float): Float {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, f, displayMetrics)
    }

    fun px(i: Int): Float {
        return px(i.toFloat())
    }
}
