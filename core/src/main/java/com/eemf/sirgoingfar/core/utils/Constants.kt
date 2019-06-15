package com.eemf.sirgoingfar.core.utils

object Constants {

    val ARG_START_DATE = "arg_start_date"
    val ARG_CURRENT_TIME = "arg_current_time"
    val ARG_TIME_FROM = "arg_time_from"
    val ARG_TIME_TO = "arg_time_to"
    val ARG_ACTIVITY_TITLE = "arg_activity_title"
    val ARG_ACTIVITY_PRIORITY = "arg_activity_priority"
    val ARG_ACTIVITY_LISTENER = "arg_activity_listener"
    val ARG_CURRENT_ACTIVITIES = "arg_current_activity"
    val ARG_CURRENT_TODO = "arg_current_todo"

    var STANDARD_PASSWORD_LENGTH = 6
    val MINIMUM_TITLE_TEXT = 5

    enum class Priority private constructor(val id: Int, val title: String) {
        NONE(0, "No priority"),
        LOW(1, "Low priority"),
        MEDIUM(2, "Medium priority"),
        HIGH(3, "High priority")
    }

    enum class Status private constructor(val id: Int, val title: String) {

        PENDING(0, "Pending"),
        DONE(1, "Done"),
        MISSED(2, "Missed"),
        SHIFTED(3, "Shifted")
    }
}
