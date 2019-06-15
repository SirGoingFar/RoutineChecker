package com.eemf.sirgoingfar.routinechecker.alarm

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.text.TextUtils
import com.eemf.sirgoingfar.core.utils.App
import com.eemf.sirgoingfar.core.utils.Prefs

class RingtoneService : Service() {

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
                if (mediaPlayer != null) {
                    mediaPlayer.start()
                }
            }

            ACTION_STOP_RINGTONE -> {
                if (mediaPlayer != null) {
                    if (!mediaPlayer.isPlaying)
                        return START_NOT_STICKY
                }

                if (mediaPlayer != null) {
                    mediaPlayer.stop()
                }
                toggleAudioManagerSetting(false)
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
        val EXTRA_KEY_ALARM_ID = "extra_key_alarm_id"
        val ACTION_START_RINGTONE = "action_start_ringtone"
        val ACTION_STOP_RINGTONE = "action_stop_ringtone"
    }

}