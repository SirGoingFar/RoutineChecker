package com.eemf.sirgoingfar.routinechecker.jobs

import android.content.Context
import com.eemf.sirgoingfar.core.utils.Constants
import com.eemf.sirgoingfar.core.utils.Helper
import com.eemf.sirgoingfar.core.utils.Prefs
import com.eemf.sirgoingfar.database.AppDatabase
import com.eemf.sirgoingfar.database.RoutineOccurrence
import com.eemf.sirgoingfar.timely.alarm.AlarmHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class SimulatedJob(private val context: Context, private val occurrence: RoutineOccurrence) {

    suspend fun runJob() = runBlocking {

        launch(Dispatchers.IO) {

            delay((Constants.MAXIMUM_ROUTINE_DURATION_MILLIS + Constants.WAITING_TIME_BEFORE_MARKED_AS_MISSED).toLong())

            //update Routines's Status and update the database
            val mDb = AppDatabase.getInstance(context)
            val currentOccurrence: RoutineOccurrence? = mDb?.dao?.getRoutineOccurrenceByAlarmId(occurrence.alarmId)

            if (currentOccurrence?.status == Constants.Status.PROGRESS.id) {
                //If the user hasn't changed the status, toggle it to MISSED
                currentOccurrence.status = Constants.Status.MISSED.id
                mDb.dao.updateOccurrence(currentOccurrence)
            }

            //schedule for the next routine
            val nextOccurrencePeriod = Helper.computeNextRoutineTime(currentOccurrence!!.freqId, currentOccurrence.occurrenceDate)
            val nextOccurrence = RoutineOccurrence(currentOccurrence.routineId, Constants.Status.UNKNOWN.id,
                    nextOccurrencePeriod, Prefs.getsInstance().nextAlarmId, currentOccurrence.name, currentOccurrence.desc, currentOccurrence.freqId)

            //schedule next routine
            AlarmHelper().execute(nextOccurrence, AlarmHelper.ACTION_SCHEDULE_ALARM)
        }
    }
}