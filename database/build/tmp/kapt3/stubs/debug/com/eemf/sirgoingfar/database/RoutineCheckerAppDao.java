package com.eemf.sirgoingfar.database;

import java.lang.System;

@android.arch.persistence.room.Dao()
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\bg\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\'J\u0014\u0010\t\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\b0\u000b0\nH\'J\u001c\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u000b0\n2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0016\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\b0\n2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u0010\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u0011\u001a\u00020\u00052\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u0013\u001a\u00020\u0005H\'J\u0010\u0010\u0014\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH\'\u00a8\u0006\u0015"}, d2 = {"Lcom/eemf/sirgoingfar/database/RoutineCheckerAppDao;", "", "addOccurrence", "", "occurrence", "Lcom/eemf/sirgoingfar/database/RoutineOccurrence;", "addRoutine", "routine", "Lcom/eemf/sirgoingfar/database/Routine;", "getAllRoutine", "Landroid/arch/lifecycle/LiveData;", "", "getAllRoutineOccurrences", "id", "", "getRoutineById", "getRoutineByIdAsync", "getRoutineOccurrenceByAlarmId", "updateOccurrence", "currentOccurrence", "updateRoutine", "database_debug"})
public abstract interface RoutineCheckerAppDao {
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine WHERE id=:id")
    public abstract android.arch.lifecycle.LiveData<com.eemf.sirgoingfar.database.Routine> getRoutineById(int id);
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine WHERE id=:id")
    public abstract com.eemf.sirgoingfar.database.Routine getRoutineByIdAsync(int id);
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine")
    public abstract android.arch.lifecycle.LiveData<java.util.List<com.eemf.sirgoingfar.database.Routine>> getAllRoutine();
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine_occurrence WHERE alarm_id=:id")
    public abstract com.eemf.sirgoingfar.database.RoutineOccurrence getRoutineOccurrenceByAlarmId(int id);
    
    @android.arch.persistence.room.Insert()
    public abstract void addRoutine(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.Routine routine);
    
    @android.arch.persistence.room.Update(onConflict = android.arch.persistence.room.OnConflictStrategy.REPLACE)
    public abstract void updateRoutine(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.Routine routine);
    
    @org.jetbrains.annotations.NotNull()
    @android.arch.persistence.room.Query(value = "SELECT * FROM routine_occurrence WHERE routine_id = :id AND NOT status=0")
    public abstract android.arch.lifecycle.LiveData<java.util.List<com.eemf.sirgoingfar.database.RoutineOccurrence>> getAllRoutineOccurrences(int id);
    
    @android.arch.persistence.room.Insert()
    public abstract void addOccurrence(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.RoutineOccurrence occurrence);
    
    @android.arch.persistence.room.Update(onConflict = android.arch.persistence.room.OnConflictStrategy.REPLACE)
    public abstract void updateOccurrence(@org.jetbrains.annotations.NotNull()
    com.eemf.sirgoingfar.database.RoutineOccurrence currentOccurrence);
}