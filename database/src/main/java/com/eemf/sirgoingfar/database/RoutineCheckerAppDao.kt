package com.eemf.sirgoingfar.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface RoutineCheckerAppDao {

    @Query("SELECT * FROM routine WHERE id=:id")
    fun getRoutineById(id: Int): LiveData<Routine>

    @Query("SELECT * FROM routine WHERE id=:id")
    fun getRoutineByIdAsync(id: Int): Routine

    @Query("SELECT * FROM routine")
    fun getAllRoutine(): LiveData<List<Routine>>

    @Query("SELECT * FROM routine_occurrence WHERE alarm_id=:id")
    fun getRoutineOccurrenceByAlarmId(id: Int): RoutineOccurrence

    @Insert
    fun addRoutine(routine: Routine)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateRoutine(routine: Routine)

    @Query("SELECT * FROM routine_occurrence WHERE routine_id = :id AND NOT status=0")
    fun getAllRoutineOccurrences(id: Int): LiveData<List<RoutineOccurrence>>

    @Insert
    fun addOccurrence(occurrence: RoutineOccurrence)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOccurrence(currentOccurrence: RoutineOccurrence)
}
