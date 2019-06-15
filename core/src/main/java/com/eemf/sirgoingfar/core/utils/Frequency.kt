package com.eemf.sirgoingfar.core.utils

enum class Frequency(public val id: Int, public val label: String) {
    HOURLY(0, "Hourly"),
    DAILY(1, "Daily"),
    WEEKLY(2, "Weekly"),
    MONTHLY(3, "Monthly"),
    YEARLY(4, "Yearly");

    companion object {
        fun getFrequency(id: Int): Frequency? {
            when (id) {
                0 -> return HOURLY
                1 -> return DAILY
                2 -> return WEEKLY
                3 -> return MONTHLY
                4 -> return YEARLY
                else -> return null
            }
        }
    }
}
