package com.eemf.sirgoingfar.core.utils

object Constants {

    const val ARG_START_DATE = "arg_start_date"

    const val MINIMUM_PASS_MARK = 70

    enum class Status(val id: Int, val label: String) {
        UNKNOWN(0, "Unknown"),
        PROGRESS(1, "In Progress"),
        DONE(2, "Done"),
        MISSED(3, "Missed");

        companion object {
            fun getStatusById(id: Int): Status? {
                return when (id) {
                    0 -> UNKNOWN
                    1 -> PROGRESS
                    2 -> DONE
                    3 -> MISSED
                    else -> null
                }
            }
        }
    }
}
