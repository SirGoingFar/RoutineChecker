package com.eemf.sirgoingfar.routinechecker.alarm

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.text.TextUtils
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.ParcelableUtil
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.notification.NotificationHelper
import com.eemf.sirgoingfar.timely.alarm.AlarmHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *
 * This class handles the notification action buttons click actions
 *
 *
 * @constructor creates an instance of the NotificationActionService
 *
 * */
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


        when (serviceAction) {
            ACTION_UPDATE_ROUTINE -> {
                GlobalScope.launch {
                    updateRoutineOccurrence(occurrence)
                }
            }
        }
        return START_NOT_STICKY
    }

    /**
     *
     * @param occurrence is the occurrence instance of a Routine that is currently in process
     *
     * The function handles the DONE action button click:
     * it marks the current routine occurrence as DONE
     *
     *
     * */
    private suspend fun updateRoutineOccurrence(occurrence: RoutineOccurrence) {
        val job = GlobalScope.launch {
            withContext(Dispatchers.IO) {
                //update Routines's Status and update the database
                val mDb = AppDatabase.getInstance(this@NotificationActionService)
                val currentOccurrence: RoutineOccurrence? = mDb?.dao?.getRoutineOccurrenceByAlarmId(occurrence.alarmId)

                if (currentOccurrence?.status == Constants.Status.PROGRESS.id) {
                    //If the user hasn't changed the status, toggle it
                    currentOccurrence.status = Constants.Status.DONE.id
                    mDb.dao.updateOccurrence(currentOccurrence)
                    NotificationHelper(this@NotificationActionService).removeNotification()
                }
            }
        }

        job.join()
    }

    companion object {
        const val ACTION_UPDATE_ROUTINE = "action_update_routine"
    }

}