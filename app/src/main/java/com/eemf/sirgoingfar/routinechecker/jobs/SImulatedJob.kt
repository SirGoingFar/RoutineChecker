package com.eemf.sirgoingfar.routinechecker.jobs

import android.content.Context
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.notification.NotificationHelper
import com.eemf.sirgoingfar.timely.alarm.AlarmHelper
import kotlinx.coroutines.delay

class SimulatedJob(private val context: Context, private val occurrence: RoutineOccurrence) {

    suspend fun runJob() {

        //update Routines occurrence Status and update the database
        val mDb = AppDatabase.getInstance(context)
        val currentOccurrence: RoutineOccurrence? = mDb?.dao?.getRoutineOccurrenceByAlarmId(occurrence.alarmId)

        delay((Constants.MAXIMUM_ROUTINE_DURATION_MILLIS + Constants.WAITING_TIME_BEFORE_MARKED_AS_MISSED).toLong())

        if (currentOccurrence?.status == Constants.Status.PROGRESS.id) {
            //If the user hasn't changed the status, toggle it to MISSED
            currentOccurrence.status = Constants.Status.MISSED.id
            mDb.dao.updateOccurrence(currentOccurrence)
        }

        NotificationHelper(context).removeNotification()
    }
}