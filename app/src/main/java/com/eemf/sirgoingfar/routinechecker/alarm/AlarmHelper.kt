package com.eemf.sirgoingfar.timely.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.KITKAT
import com.eemf.sirgoingfar.core.utils.App
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.core.utils.ParcelableUtil
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.R
import com.eemf.sirgoingfar.routinechecker.alarm.AlarmReceiver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AlarmHelper {

    private var mAlarmManager: AlarmManager? = null
    private var mContext: Context? = null

    init {
        if (mContext == null)
            mContext = App.getsInstance()?.appContext

        if (mAlarmManager == null)
            mAlarmManager = App.getsInstance()?.appContext
                    ?.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun execute(occurrence: RoutineOccurrence?, action: Int) {
        runBlocking {
            launch(Dispatchers.IO) {
                if (action < 0)
                    throw IllegalArgumentException(mContext?.getString(R.string.text_invalid_action_identifier))

                when (action) {
                    ACTION_SCHEDULE_ALARM -> schedule(occurrence, false)

                    ACTION_UPDATE_ALARM -> update(occurrence)

                    ACTION_DELETE_ALARM -> delete(occurrence)
                }
            }
        }
    }

    private fun delete(occurrence: RoutineOccurrence?) {
        val pendingIntent = getPendingIntentFor(occurrence, false)
        mAlarmManager?.cancel(pendingIntent)
    }

    private fun update(occurrence: RoutineOccurrence?) {
        //delete the previously scheduled alarm for this occurrence
        delete(occurrence)

        //re-schedule
        schedule(occurrence, true)
    }

    private fun schedule(occurrence: RoutineOccurrence?, isUpdate: Boolean) {

        if (Helper.hasTimePassed(occurrence?.occurrenceDate!!))
            occurrence.occurrenceDate = Helper.computeNextRoutineTime(occurrence.freqId, occurrence.occurrenceDate)

        val pendingIntent = getPendingIntentFor(occurrence, isUpdate)
        val alarmTime = (occurrence.occurrenceDate!!.time - Constants.MINIMUM_NOTIF_TIME_TO_START_TIME_MILLIS)
        when {
            SDK_INT >= Build.VERSION_CODES.M -> mAlarmManager?.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
            SDK_INT >= KITKAT -> mAlarmManager?.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
            else -> mAlarmManager?.set(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent)
        }

        //create the Occurrrence
        val mDb = AppDatabase.getInstance(mContext!!)
        mDb?.dao?.addOccurrence(occurrence)
    }

    private fun getPendingIntentFor(occurrence: RoutineOccurrence?, isUpdate: Boolean): PendingIntent {
        val alarmIntent = Intent(mContext, AlarmReceiver::class.java)
        val occurrenceByte = ParcelableUtil.marshall(occurrence!!)
        alarmIntent.putExtra(KEY_EXTRA_OCCURRENCE, occurrenceByte)
        alarmIntent.data = Uri.parse(mContext?.getString(R.string.prefix_text) + occurrence.alarmId)
        alarmIntent.action = occurrence.alarmId.toString()

        if (isUpdate) {
            alarmIntent.putExtra(AlarmReceiver.EXTRA_KEY_ALARM_RECEIVER_ACTION, AlarmReceiver.ALARM_ACTION_UPDATE_ALARM)
        }

        return PendingIntent.getBroadcast(mContext, occurrence.alarmId, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    companion object {

        const val ACTION_SCHEDULE_ALARM = 0
        const val ACTION_UPDATE_ALARM = 1
        const val ACTION_DELETE_ALARM = 2

        const val KEY_EXTRA_OCCURRENCE = "key_extra_occurrence"
    }
}
