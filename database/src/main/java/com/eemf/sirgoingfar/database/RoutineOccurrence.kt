package com.eemf.sirgoingfar.database

import android.arch.persistence.room.*
import android.os.Parcel
import android.os.Parcelable
import java.util.*

@Entity(tableName = "routine_occurrence",
        foreignKeys = [ForeignKey(
                entity = Routine::class, parentColumns = ["id"],
                childColumns = ["routine_id"],
                onDelete = ForeignKey.CASCADE)],
        indices = [Index("routine_id")])
class RoutineOccurrence() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    @ColumnInfo(name = "routine_id")
    var routineId: Int = 0

    var status: Int = 0

    @ColumnInfo(name = "occurrence_date")
    var occurrenceDate: Date? = null

    @Ignore
    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        routineId = parcel.readInt()
        status = parcel.readInt()
        occurrenceDate = Date(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(routineId)
        parcel.writeInt(status)
        parcel.writeLong(occurrenceDate?.time ?: 0)
    }

    constructor(id: Int, routineId: Int, status: Int, occurrenceDate: Date?) : this() {
        this.id = id
        this.routineId = routineId
        this.status = status
        this.occurrenceDate = occurrenceDate
    }

    @Ignore
    constructor(routineId: Int, status: Int, occurrenceDate: Date?) : this() {
        this.routineId = routineId
        this.status = status
        this.occurrenceDate = occurrenceDate
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoutineOccurrence> {
        override fun createFromParcel(parcel: Parcel): RoutineOccurrence {
            return RoutineOccurrence(parcel)
        }

        override fun newArray(size: Int): Array<RoutineOccurrence?> {
            return arrayOfNulls(size)
        }
    }
}
