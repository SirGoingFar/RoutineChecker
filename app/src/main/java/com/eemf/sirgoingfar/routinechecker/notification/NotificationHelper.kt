package com.eemf.sirgoingfar.routinechecker.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils
import com.eemf.sirgoingfar.core.utils.App
import com.eemf.sirgoingfar.routinechecker.R

/**
 *
 * This class is a Helper class for setting the Notification Properties and sending the Notification
 * to the device Notification tray.
 *
 *
 * @property context is the caller context
 * @constructor creates an instance of the NotificationHelper
 *
 *
 * */
class NotificationHelper(private val context: Context) {

    private var notificationManager: NotificationManager? = null

    fun notifyUser(param: NotificationParam) {

        //clear any notification from the app
        if (param.shouldRemovePreviousNotif())
            removeNotification()

        notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, App.NOTIF_CHANNEL_ID)

        val sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        //Set Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notifChannel = NotificationChannel(
                    App.NOTIF_CHANNEL_ID, App.NOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
            )

            //create the channel for the Notification Manager
            assert(notificationManager != null)
            notificationManager!!.createNotificationChannel(notifChannel)
        } else {
            builder.priority = param.priorityType
        }

        val textColor = ContextCompat.getColor(context, param.bodyTextColor)
        //Build the Notification
        builder.setSmallIcon(param.smallIcon)
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.resources,
                        param.largeIcon))
                .setContentText(param.body)
                .setColor(if (textColor <= 0) Color.BLACK else textColor)
                .setContentTitle(param.title)
                .setContentIntent(param.bodyPendingIntent)
                .setSound(if (param.shouldSound()) param.soundUri else null)
                .setGroup(App.APP_GROUP_KEY)
                .setLights(param.lightColor, 500, 1000)
                .setSubText(context.getString(R.string.app_name))
                .setVibrate(longArrayOf(0, 1000, 1000, 1000, 1000))
                .setAutoCancel(param.isAutoCancel)

        if (!TextUtils.isEmpty(param.btnOneText)) {
            builder.addAction(param.btnOneIconResId, param.btnOneText, param.btnOnePendingIntent)
        }

        if (!TextUtils.isEmpty(param.btnTwoText)) {
            builder.addAction(param.btnTwoIconResId, param.btnTwoText, param.btnTwoPendingIntent)
        }

        if (!TextUtils.isEmpty(param.btnThreeText)) {
            builder.addAction(param.btnThreeIconResId, param.btnThreeText, param.btnThreePendingIntent)
        }

        try {
            notificationManager!!.notify(App.NOTIF_ID, builder.build())
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun removeNotification() {
        val manager = App.getsInstance()!!.appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.cancelAll()
    }
}
