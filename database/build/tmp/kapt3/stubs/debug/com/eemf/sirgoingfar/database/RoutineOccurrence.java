package com.eemf.sirgoingfar.database;

import java.lang.System;

@android.arch.persistence.room.Entity(tableName = "routine_occurrence", foreignKeys = {@android.arch.persistence.room.ForeignKey(entity = com.eemf.sirgoingfar.database.Routine.class, childColumns = {"routine_id"}, onDelete = 5, parentColumns = {"id"})}, indices = {@android.arch.persistence.room.Index(value = {"routine_id"})})
@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u001d\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0007\u0018\u0000 -2\u00020\u0001:\u0001-B\u000f\b\u0017\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004BM\b\u0016\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0010BE\b\u0017\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0006\u0012\b\u0010\t\u001a\u0004\u0018\u00010\n\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\b\u0010\f\u001a\u0004\u0018\u00010\r\u0012\b\u0010\u000e\u001a\u0004\u0018\u00010\r\u0012\u0006\u0010\u000f\u001a\u00020\u0006\u00a2\u0006\u0002\u0010\u0011B\u0005\u00a2\u0006\u0002\u0010\u0012J\b\u0010)\u001a\u00020\u0006H\u0016J\u0018\u0010*\u001a\u00020+2\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010,\u001a\u00020\u0006H\u0016R\u001e\u0010\u000b\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0014\"\u0004\b\u0015\u0010\u0016R\u001c\u0010\u000e\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\u000f\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001b\u0010\u0014\"\u0004\b\u001c\u0010\u0016R\u001e\u0010\u0005\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u0014\"\u0004\b\u001e\u0010\u0016R\u001c\u0010\f\u001a\u0004\u0018\u00010\rX\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0018\"\u0004\b \u0010\u001aR \u0010\t\u001a\u0004\u0018\u00010\n8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b!\u0010\"\"\u0004\b#\u0010$R\u001e\u0010\u0007\u001a\u00020\u00068\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0014\"\u0004\b&\u0010\u0016R\u001a\u0010\b\u001a\u00020\u0006X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\'\u0010\u0014\"\u0004\b(\u0010\u0016\u00a8\u0006."}, d2 = {"Lcom/eemf/sirgoingfar/database/RoutineOccurrence;", "Landroid/os/Parcelable;", "parcel", "Landroid/os/Parcel;", "(Landroid/os/Parcel;)V", "id", "", "routineId", "status", "occurrenceDate", "Ljava/util/Date;", "alarmId", "name", "", "desc", "freqId", "(IIILjava/util/Date;ILjava/lang/String;Ljava/lang/String;I)V", "(IILjava/util/Date;ILjava/lang/String;Ljava/lang/String;I)V", "()V", "getAlarmId", "()I", "setAlarmId", "(I)V", "getDesc", "()Ljava/lang/String;", "setDesc", "(Ljava/lang/String;)V", "getFreqId", "setFreqId", "getId", "setId", "getName", "setName", "getOccurrenceDate", "()Ljava/util/Date;", "setOccurrenceDate", "(Ljava/util/Date;)V", "getRoutineId", "setRoutineId", "getStatus", "setStatus", "describeContents", "writeToParcel", "", "flags", "CREATOR", "database_debug"})
public final class RoutineOccurrence implements android.os.Parcelable {
    @android.arch.persistence.room.PrimaryKey(autoGenerate = true)
    private int id;
    @android.arch.persistence.room.ColumnInfo(name = "routine_id")
    private int routineId;
    private int status;
    @org.jetbrains.annotations.Nullable()
    @android.arch.persistence.room.ColumnInfo(name = "occurrence_date")
    private java.util.Date occurrenceDate;
    @android.arch.persistence.room.ColumnInfo(name = "alarm_id")
    private int alarmId;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String name;
    @org.jetbrains.annotations.Nullable()
    private java.lang.String desc;
    @android.arch.persistence.room.ColumnInfo(name = "frequency_id")
    private int freqId;
    public static final com.eemf.sirgoingfar.database.RoutineOccurrence.CREATOR CREATOR = null;
    
    public final int getId() {
        return 0;
    }
    
    public final void setId(int p0) {
    }
    
    public final int getRoutineId() {
        return 0;
    }
    
    public final void setRoutineId(int p0) {
    }
    
    public final int getStatus() {
        return 0;
    }
    
    public final void setStatus(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.util.Date getOccurrenceDate() {
        return null;
    }
    
    public final void setOccurrenceDate(@org.jetbrains.annotations.Nullable()
    java.util.Date p0) {
    }
    
    public final int getAlarmId() {
        return 0;
    }
    
    public final void setAlarmId(int p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getName() {
        return null;
    }
    
    public final void setName(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getDesc() {
        return null;
    }
    
    public final void setDesc(@org.jetbrains.annotations.Nullable()
    java.lang.String p0) {
    }
    
    public final int getFreqId() {
        return 0;
    }
    
    public final void setFreqId(int p0) {
    }
    
    @java.lang.Override()
    public void writeToParcel(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel, int flags) {
    }
    
    @java.lang.Override()
    public int describeContents() {
        return 0;
    }
    
    public RoutineOccurrence() {
        super();
    }
    
    @android.arch.persistence.room.Ignore()
    public RoutineOccurrence(@org.jetbrains.annotations.NotNull()
    android.os.Parcel parcel) {
        super();
    }
    
    public RoutineOccurrence(int id, int routineId, int status, @org.jetbrains.annotations.Nullable()
    java.util.Date occurrenceDate, int alarmId, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String desc, int freqId) {
        super();
    }
    
    @android.arch.persistence.room.Ignore()
    public RoutineOccurrence(int routineId, int status, @org.jetbrains.annotations.Nullable()
    java.util.Date occurrenceDate, int alarmId, @org.jetbrains.annotations.Nullable()
    java.lang.String name, @org.jetbrains.annotations.Nullable()
    java.lang.String desc, int freqId) {
        super();
    }
    
    @kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001d\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016\u00a2\u0006\u0002\u0010\u000b\u00a8\u0006\f"}, d2 = {"Lcom/eemf/sirgoingfar/database/RoutineOccurrence$CREATOR;", "Landroid/os/Parcelable$Creator;", "Lcom/eemf/sirgoingfar/database/RoutineOccurrence;", "()V", "createFromParcel", "parcel", "Landroid/os/Parcel;", "newArray", "", "size", "", "(I)[Lcom/eemf/sirgoingfar/database/RoutineOccurrence;", "database_debug"})
    public static final class CREATOR implements android.os.Parcelable.Creator<com.eemf.sirgoingfar.database.RoutineOccurrence> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public com.eemf.sirgoingfar.database.RoutineOccurrence createFromParcel(@org.jetbrains.annotations.NotNull()
        android.os.Parcel parcel) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public com.eemf.sirgoingfar.database.RoutineOccurrence[] newArray(int size) {
            return null;
        }
        
        private CREATOR() {
            super();
        }
    }
}