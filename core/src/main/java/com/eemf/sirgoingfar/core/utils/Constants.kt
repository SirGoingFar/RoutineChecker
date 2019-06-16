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
