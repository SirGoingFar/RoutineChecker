package com.eemf.sirgoingfar.database;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 15}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0019\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006H\u0007\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\b\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u0004H\u0007\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, d2 = {"Lcom/eemf/sirgoingfar/database/DateConverter;", "", "()V", "toDate", "Ljava/util/Date;", "timeStamp", "", "(Ljava/lang/Long;)Ljava/util/Date;", "toTimeStamp", "date", "(Ljava/util/Date;)Ljava/lang/Long;", "database_debug"})
public final class DateConverter {
    
    @org.jetbrains.annotations.Nullable()
    @android.arch.persistence.room.TypeConverter()
    public final java.lang.Long toTimeStamp(@org.jetbrains.annotations.Nullable()
    java.util.Date date) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    @android.arch.persistence.room.TypeConverter()
    public final java.util.Date toDate(@org.jetbrains.annotations.Nullable()
    java.lang.Long timeStamp) {
        return null;
    }
    
    public DateConverter() {
        super();
    }
}