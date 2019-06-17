package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.support.multidex.MultiDexApplication
import java.util.*

/**
 *
 * The App class is the point of entry/instantiation of the App
 *
 * App-wide static objects are initialized here
 *
 * */
class App : MultiDexApplication() {

    val appContext: Context
        get() = this

    override fun onCreate() {
        super.onCreate()

        //init class static instance
        if (sInstance == null)
            sInstance = this

        //init crucial instances
        initInstances()
    }

    private fun initInstances() {
        //init App Executors
        sExecutors = AppExecutors.instance

        //init color codes
        colorCodes = ColorUtil.getColorCode(this)
    }

    companion object {

        val NOTIF_CHANNEL_ID = "channel_general"
        val NOTIF_CHANNEL_NAME = "General"
        val NOTIF_ID = 123
        val APP_GROUP_KEY = "com.eemf.sirgoingfar.android"

        private var sInstance: App? = null
        private var sExecutors: AppExecutors? = null
        var colorCodes: ArrayList<Int>? = null
            private set

        fun getsInstance(): App? {
            return sInstance
        }

        fun getsExecutors(): AppExecutors? {
            return sExecutors
        }
    }
}
