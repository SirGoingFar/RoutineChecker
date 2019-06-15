package com.eemf.sirgoingfar.core.utils

import android.content.Context
import android.content.SharedPreferences
import android.media.AudioManager

class Prefs private constructor(context: Context) {

    private val APP_PREFS = "app_prefs"
    private val PREF_ALARM_ID = "pref_alarm_id"
    private val PREF_AUDIO_RINGER_MODE = "pref_audio_ringer_mode"
    private val PREF_STREAM_VOLUME = "pref_stream_volume"

    private val mPrefs: SharedPreferences

    private val editor: SharedPreferences.Editor
        get() = mPrefs.edit()

    private val lastAlarmId: Int
        get() = mPrefs.getInt(PREF_ALARM_ID, -1)

    val nextAlarmId: Int
        get() {
            val lastAlarmId = lastAlarmId
            val nextAlarmId = lastAlarmId + 1
            saveNextAlarmId(nextAlarmId)
            return nextAlarmId
        }

    var audioRingerMode: Int
        get() = mPrefs.getInt(PREF_AUDIO_RINGER_MODE, AudioManager.MODE_NORMAL)
        set(ringerMode) = editor.putInt(PREF_AUDIO_RINGER_MODE, ringerMode).apply()

    var streamVolume: Int
        get() = mPrefs.getInt(PREF_STREAM_VOLUME, 2)
        set(streamVolume) = editor.putInt(PREF_STREAM_VOLUME, streamVolume).apply()

    init {
        sInstance = this
        mPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
    }

    private fun saveNextAlarmId(id: Int) {
        editor.putInt(PREF_ALARM_ID, id).apply()
    }

    companion object {
        private var sInstance: Prefs? = null

        fun getsInstance(): Prefs {

            if (sInstance == null)
                sInstance = Prefs(App.getsInstance()!!.appContext)

            return sInstance as Prefs
        }
    }
}
