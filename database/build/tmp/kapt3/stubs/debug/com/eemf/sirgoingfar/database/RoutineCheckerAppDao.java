package com.eemf.sirgoingfar.database;

import java.lang.System;

@android.arch.persistence.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\t0\bH\'J\u001c\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000b0\t0\b2\u0006\u0010\f\u001a\u00020\rH\'\u00a8\u0006\u000e"}, d2 = {"Lcom/eemf/sirgoingfar/database/RoutineCheckerAppDao;", "", "addRoutine", "", "routine", "Lcom/eemf/sirgoingfar/database/Routine;", "editRoutine", "getAllRoutine", "Landroid/arch/lifecycle/LiveData;", "", "getAllRoutineOccurrences", "Lcom/eemf/sirgoingfar/database/RoutineOccurrence;", "id", "", "database_debug"})
public abstract interface RoutineCheckerAppDao {
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine")
    public abstract android.arch.lifecycle.LiveData<java.util.List<com.eemf.sirgoingfar.database.Routine>> getAllRoutine();
    
    @android.arch.persistence.room.Insert()
    public abstract void addRoutine(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.Routine routine);
    
    @android.arch.persistence.room.Update(onConflict = android.arch.persistence.room.OnConflictStrategy.REPLACE)
    public abstract void editRoutine(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.Routine routine);
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine_occurrence WHERE routine_id = :id")
    public abstract android.arch.lifecycle.LiveData<java.util.List<com.eemf.sirgoingfar.database.RoutineOccurrence>> getAllRoutineOccurrences(int id);
}