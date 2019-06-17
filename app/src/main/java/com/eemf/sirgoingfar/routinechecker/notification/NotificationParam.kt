package com.eemf.sirgoingfar.routinechecker.notification

import android.app.PendingIntent
import android.graphics.Color
import android.media.RingtoneManager
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import com.eemf.sirgoingfar.routinechecker.R

/**
 *
 * @constructor creates an object of NotificationParam - a notification property
 *
 * */
class NotificationParam {

    var id: Int = 0
    var priorityType: Int = 0
    @DrawableRes
    var smallIcon = R.drawable.ic_alarm_raven
    @DrawableRes
    var largeIcon = R.drawable.ic_alarm_raven
    @DrawableRes
    var btnOneIconResId: Int = 0
    @DrawableRes
    var btnTwoIconResId: Int = 0
    @DrawableRes
    var btnThreeIconResId: Int = 0
    @ColorRes
    var bodyTextColor = R.color.colorBlack
    var lightColor = Color.RED

    var title = "Timely"
    var body: String? = null
    var btnOneText: String? = null
    var btnTwoText: String? = null
    var btnThreeText: String? = null

    private var shouldVibrate: Boolean = false
    private var shouldSound: Boolean = false
    var isDismissable: Boolean = false
    var isAutoCancel: Boolean = false
    private var removePreviousNotif: Boolean = false

    var soundUri: Uri = DEFAULT_SOUND

    var bodyPendingIntent: PendingIntent? = null
    var btnOnePendingIntent: PendingIntent? = null
    var btnTwoPendingIntent: PendingIntent? = null
    var btnThreePendingIntent: PendingIntent? = null

    fun shouldVibrate(): Boolean {
        return shouldVibrate
    }

    fun setShouldVibrate(shouldVibrate: Boolean) {
        this.shouldVibrate = shouldVibrate
    }

    fun shouldSound(): Boolean {
        return shouldSound
    }

    fun setShouldSound(shouldSound: Boolean) {
        this.shouldSound = shouldSound
    }

    fun shouldRemovePreviousNotif(): Boolean {
        return removePreviousNotif
    }

    fun setRemovePreviousNotif(removePreviousNotif: Boolean) {
        this.removePreviousNotif = removePreviousNotif
    }

    companion object {
        var DEFAULT_SOUND = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
    }
}
