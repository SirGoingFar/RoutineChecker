package com.eemf.sirgoingfar.database;

import java.lang.System;

@android.arch.persistence.room.TypeConverters(value = {com.eemf.sirgoingfar.database.DateConverter.class})
@android.arch.persistence.room.Database(entities = {com.eemf.sirgoingfar.database.Routine.class, com.eemf.sirgoingfar.database.RoutineOccurrence.class}, version = 1, exportSchema = false)
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\'\u0018\u0000 \u00072\u00020\u0001:\u0001\u0007B\u0005\u00a2\u0006\u0002\u0010\u0002R\u0012\u0010\u0003\u001a\u00020\u0004X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\b"}, d2 = {"Lcom/eemf/sirgoingfar/database/AppDatabase;", "Landroid/arch/persistence/room/RoomDatabase;", "()V", "dao", "Lcom/eemf/sirgoingfar/database/RoutineCheckerAppDao;", "getDao", "()Lcom/eemf/sirgoingfar/database/RoutineCheckerAppDao;", "Companion", "database_debug"})
public abstract class AppDatabase extends android.arch.persistence.room.RoomDatabase {
    private static final java.lang.Object LOCK = null;
    private static final java.lang.String DATABASE_NAME = "routine_app_db";
    private static com.eemf.sirgoingfar.database.AppDatabase sInstance;
    public static final com.eemf.sirgoingfar.database.AppDatabase.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    public abstract com.eemf.sirgoingfar.database.RoutineCheckerAppDao getDao();
    
    public AppDatabase() {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\b\u001a\u0004\u0018\u00010\u00072\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082D\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/eemf/sirgoingfar/database/AppDatabase$Companion;", "", "()V", "DATABASE_NAME", "", "LOCK", "sInstance", "Lcom/eemf/sirgoingfar/database/AppDatabase;", "getInstance", "context", "Landroid/content/Context;", "database_debug"})
    public static final class Companion {
        
        @org.jetbrains.annotations.Nullable()
        public final com.eemf.sirgoingfar.database.AppDatabase getInstance(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
        
        private Companion() {
            super();
        }
    }
}