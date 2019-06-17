package com.eemf.sirgoingfar.routinechecker.jobs

import android.content.Context
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.routinechecker.notification.NotificationHelper
import kotlinx.coroutines.delay

/**
 *
 * @property context is the job Caller context
 * @property occurrence is the routine occurrence instance that's in process
 *@constructor creates an instance of a SimulatedJob
 *
 * */
class SimulatedJob(private val context: Context, private val occurrence: RoutineOccurrence) {

    /**
     *
     * This function simulates a long running task that's taking place as an
     * instance of a routine goes off.
     *
     * After a wait time (15 minutes for the routine + 5 minutes user's routines categorization window),
     * it marks @param occurrence as a MISSED routine occurrence.
     *
     *
     * Afterwards, it clears the notification tray.
     *
     *
     * */
    suspend fun runJob() {

        //update Routines occurrence Status and update the database
        val mDb = AppDatabase.getInstance(context)
        val currentOccurrence: RoutineOccurrence? = mDb?.dao?.getRoutineOccurrenceByAlarmId(occurrence.alarmId)

        delay((Constants.MAXIMUM_ROUTINE_DURATION_MILLIS + Constants.WAITING_TIME_BEFORE_MARKED_AS_MISSED).toLong())

        if (currentOccurrence?.status != Constants.Status.DONE.id) {
            //If the user hasn't changed the status, toggle it to MISSED
            currentOccurrence!!.status = Constants.Status.MISSED.id
            mDb.dao.updateOccurrence(currentOccurrence)
        }

        NotificationHelper(context).removeNotification()
    }
}