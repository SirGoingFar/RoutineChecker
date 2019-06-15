package com.eemf.sirgoingfar.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;

import com.eemf.sirgoingfar.core.utils.App;

public class NotificationHelper {

    private int notifId;
    private Context context;
    private NotificationManager notificationManager;


    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void notifyUser(NotificationParam param) {

        //clear any notification from the app
        if (param.shouldRemovePreviousNotif())
            removeNotification();

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, App.Companion.getNOTIF_CHANNEL_ID());

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Set Notification Channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notifChannel = new NotificationChannel(
                    App.Companion.getNOTIF_CHANNEL_ID(), App.Companion.getNOTIF_CHANNEL_NAME(), NotificationManager.IMPORTANCE_HIGH
            );

            //create the channel for the Notification Manager
            assert notificationManager != null;
            notificationManager.createNotificationChannel(notifChannel);
        } else {
            builder.setPriority(param.getPriorityType());
        }

        int textColor = ContextCompat.getColor(context, param.getBodyTextColor());
        //Build the Notification
        builder.setSmallIcon(param.getSmallIcon())
                .setLargeIcon(BitmapFactory.decodeResource(
                        context.getResources(),
                        param.getLargeIcon()))
                .setContentText(param.getBody())
                .setColor(textColor <= 0 ? Color.BLACK : textColor)
                .setContentTitle(param.getTitle())
                .setContentIntent(param.getBodyPendingIntent())
                .setSound(param.shouldSound() ? param.getSoundUri() == null ? sound : param.getSoundUri() : null)
                .setGroup(App.Companion.getAPP_GROUP_KEY())
                .setLights(param.getLightColor(), 500, 1000)
                .setSubText(context.getString(R.string.app_name))
                .setVibrate(new long[]{0, 1000, 1000, 1000, 1000})
                .setOngoing(!param.isDismissable())
                .setAutoCancel(param.isAutoCancel());

        if (!TextUtils.isEmpty(param.getBtnOneText())) {
            builder.addAction(param.getBtnOneIconResId(), param.getBtnOneText(), param.getBtnOnePendingIntent());
        }

        if (!TextUtils.isEmpty(param.getBtnTwoText())) {
            builder.addAction(param.getBtnTwoIconResId(), param.getBtnTwoText(), param.getBtnTwoPendingIntent());
        }

        if (!TextUtils.isEmpty(param.getBtnThreeText())) {
            builder.addAction(param.getBtnThreeIconResId(), param.getBtnThreeText(), param.getBtnThreePendingIntent());
        }

        try {
            notificationManager.notify(App.Companion.getNOTIF_ID(), builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeNotification() {
        NotificationManager manager = (NotificationManager) App.Companion.getsInstance().getAppContext().getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }
}
