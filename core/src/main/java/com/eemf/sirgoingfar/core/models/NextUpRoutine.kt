package com.eemf.sirgoingfar.core.models

import android.os.Parcel
import android.os.Parcelable

class NextUpRoutine() : Parcelable {

    var name: String? = null
    var estimate: String? = null

    constructor(parcel: Parcel) : this() {
        name = parcel.readString()
        estimate = parcel.readString()
    }

    constructor(name: String?, estimate: String?) : this() {
        this.name = name
        this.estimate = estimate
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(estimate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NextUpRoutine> {
        override fun createFromParcel(parcel: Parcel): NextUpRoutine {
            return NextUpRoutine(parcel)
        }

        override fun newArray(size: Int): Array<NextUpRoutine?> {
            return arrayOfNulls(size)
        }
    }
}