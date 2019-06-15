package com.eemf.sirgoingfar.routinechecker.alarm

enum class AlarmStatus private constructor(id: Int) {
    PENDING(0),
    RANG(1);

    var statusId: Int = 0
        internal set

    init {
        statusId = id
    }
}
