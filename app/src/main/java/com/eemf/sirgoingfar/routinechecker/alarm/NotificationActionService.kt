package com.eemf.sirgoingfar.routinechecker.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.text.TextUtils
import com.eemf.sirgoingfar.core.utils.App
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.ParcelableUtil
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.timely.alarm.AlarmHelper

class NotificationActionService : Service() {

    private var pref: Prefs? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        pref = Prefs.getsInstance()

        if (TextUtils.isEmpty(intent.action))
            return START_NOT_STICKY

        val serviceAction = intent.action
        if (TextUtils.isEmpty(serviceAction))
            return START_NOT_STICKY

        if (!intent.hasExtra(AlarmHelper.KEY_EXTRA_OCCURRENCE))
            return START_STICKY

        val occurrenceByte = intent.getByteArrayExtra(AlarmHelper.KEY_EXTRA_OCCURRENCE)
        val occurrence = ParcelableUtil.unmarshall(occurrenceByte, RoutineOccurrence.CREATOR)

        val mediaPlayer = App.mediaPlayer

        when (serviceAction) {
            ACTION_START_RINGTONE -> {
                if (mediaPlayer != null) {
                    mediaPlayer.isLooping = true
                }
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying)
                        return START_NOT_STICKY
                }

                toggleAudioManagerSetting(true)
                mediaPlayer?.start()
            }

            ACTION_STOP_RINGTONE -> {
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying)
                        return START_NOT_STICKY
                }

                mediaPlayer?.stop()
                toggleAudioManagerSetting(false)
            }

            ACTION_UPDATE_ROUTINE -> {
                //stop the ringing tone
                toggleAudioManagerSetting(false)

                //update Routines's Status and update the database
                val mDb = AppDatabase.getInstance(this)
                val currentOccurrence: RoutineOccurrence? = mDb?.dao?.getRoutineOccurrenceByAlarmId(occurrence.alarmId)

                if (currentOccurrence?.status == Constants.Status.PROGRESS.id) {
                    //If the user hasn't changed the status, toggle it
                    currentOccurrence.status = Constants.Status.DONE.id
                    mDb.dao.updateOccurrence(currentOccurrence)
                }
            }
        }

        return START_NOT_STICKY
    }

    private fun toggleAudioManagerSetting(startRinging: Boolean) {
        //toggle ringer mode
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        val ringerMode: Int
        val streamVolume: Int

        if (startRinging) {
            ringerMode = audioManager.ringerMode
            streamVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)

            pref!!.audioRingerMode = ringerMode
            pref!!.streamVolume = streamVolume

            when (audioManager.ringerMode) {
                AudioManager.RINGER_MODE_SILENT -> {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING) / 2, 0)
                }

                AudioManager.RINGER_MODE_VIBRATE -> {
                    audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
                    audioManager.setStreamVolume(AudioManager.STREAM_RING, audioManager.getStreamMaxVolume(AudioManager.STREAM_RING) / 2, 0)
                }
            }
        } else {
            //when the audio stops, restore the initial audio setting
            ringerMode = pref!!.audioRingerMode
            streamVolume = pref!!.streamVolume

            audioManager.ringerMode = ringerMode
            audioManager.setStreamVolume(AudioManager.STREAM_RING, streamVolume, 0)
        }
    }

    companion object {
        const val EXTRA_KEY_ALARM_ID = "extra_key_alarm_id"
        const val ACTION_START_RINGTONE = "action_start_ringtone"
        const val ACTION_STOP_RINGTONE = "action_stop_ringtone"
        const val ACTION_UPDATE_ROUTINE = "action_update_routine"
    }

}