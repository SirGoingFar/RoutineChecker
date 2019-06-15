package com.eemf.sirgoingfar.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface RoutineCheckerAppDao {

    @Query("SELECT * FROM routine WHERE id=:id")
    fun getRoutineById(id: Int): LiveData<Routine>

    @Query("SELECT * FROM routine")
    fun getAllRoutine(): LiveData<List<Routine>>

    @Insert
    fun addRoutine(routine: Routine)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun editRoutine(routine: Routine)

    @Query("SELECT * FROM routine_occurrence WHERE routine_id = :id")
    fun getAllRoutineOccurrences(id: Int): LiveData<List<RoutineOccurrence>>

}
