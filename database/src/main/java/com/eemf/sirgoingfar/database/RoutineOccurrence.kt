package com.eemf.sirgoingfar.database

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
internal class RoutineOccurrence(@field:PrimaryKey(autoGenerate = true)
                                 var id: Int)
