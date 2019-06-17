package com.eemf.sirgoingfar.core.utils

object Constants {

    const val ARG_START_DATE = "arg_start_date"
    const val ARG_TIME = "arg_time"
    const val ARG_IS_TIME_SELECTED = "arg_is_time_selected"
    const val ARG_ROUTINE_DESC = "arg_routine_desc"
    const val ARG_ROUTINE_TITLE = "arg_routine_title"
    const val ARG_ROUTINE_PRIORITY = "arg_routine_priority"
    const val ARG_LISTENER = "arg_listener"
    const val ARG_CURRENT_ROUTINE = "arg_current_routine"

    const val MINIMUM_PASS_MARK = 70
    const val MINIMUM_NOTIF_TIME_TO_START_TIME_MILLIS = 5 * 60 * 1000 //notify when it's 5 minutes to a routine
    const val WAITING_TIME_BEFORE_MARKED_AS_MISSED = 5 * 60 * 1000 //wait for 5 minutes after routine has expired before marking as MISSED routine
    const val MAXIMUM_ROUTINE_DURATION_MILLIS = 15 * 60 * 1000 //all routine have a maximum execution time of 15 minutes


    const val TWELVE_HOURS_IN_MILLIS = 12 * 60 * 60 * 1000

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
