package com.eemf.sirgoingfar.routinechecker.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

    }

    /*public static final String EXTRA_KEY_ALARM_RECEIVER_ACTION = "key_update_alarm";
    public static final String ALARM_ACTION_UPDATE_ALARM = "action_update_alarm";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!intent.hasExtra(AlarmHelper.KEY_EXTRA_ACTIVITY))
            return;

        byte[] activityByte = intent.getByteArrayExtra(AlarmHelper.KEY_EXTRA_ACTIVITY);
        TodoActivity activity = ParcelableUtil.unmarshall(activityByte, TodoActivity.CREATOR);

        if (activity == null)
            return;

        NotificationHelper notifHelper = new NotificationHelper(context);

        //trigger PUSH: clear previous ones first
        NotificationParam param = new NotificationParam();
        param.setRemovePreviousNotif(true);
        param.setTitle(context.getString(com.eemf.sirgoingfar.notification.R.string.text_push_title));
        param.setBody(getNotifBody(activity));
        param.setPriorityType(NotificationCompat.PRIORITY_HIGH);
        param.setDismissable(false);
        param.setBtnOneText(context.getString(com.eemf.sirgoingfar.timely.R.string.text_push_btn_one_label));
//        param.setBtnOneIconResId(R.drawable.ic_check);
        param.setBtnOnePendingIntent(getStopRingtonePendingIntent(context, activity.getAlarmId()));
        param.setBodyPendingIntent(getLaunchAppPendingIntent(context, activity.getAlarmId()));
        notifHelper.notifyUser(param);

        Toast.makeText(context, "PUSH sent", Toast.LENGTH_SHORT).show();

        //ring tone
        context.startService(getStartRingtoneIntent(context));

        //update Activity's Status and update the database
        if (intent.hasExtra(EXTRA_KEY_ALARM_RECEIVER_ACTION) &&
                intent.getStringExtra(EXTRA_KEY_ALARM_RECEIVER_ACTION).equalsIgnoreCase(ALARM_ACTION_UPDATE_ALARM)) {
            activity.setAlarmStatus(AlarmStatus.RANG.getStatusId());
            AppDatabase.getInstance(context).getDao().updateTodoActivity(activity);
        }
    }

    private String getNotifBody(TodoActivity activity) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(activity.getStartTime());
        int val;

        String body = "Description: ";
        body = body.concat(activity.getDescription() + "\n");

        val = cal.get(Calendar.HOUR_OF_DAY);
        body = body.concat("Time: ");
        body = body.concat(String.valueOf(val > 12 ? val % 12 : val)) + ":" + String.format(Locale.getDefault(), "%2d", cal.get(Calendar.MINUTE)) + (cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
        body = body.concat(" - ");

        cal.setTime(activity.getEndTime());
        val = cal.get(Calendar.HOUR_OF_DAY);
        body = body.concat(String.valueOf(val > 12 ? val % 12 : val)) + ":" + String.format(Locale.getDefault(), "%2d", cal.get(Calendar.MINUTE)) + (cal.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM");
        ;
        body = body.concat(" (Duration: " + TimeUtil.getDuration(activity.getStartTime().getTime(), activity.getEndTime().getTime()) + ")");
        return body;
    }

    private PendingIntent getStopRingtonePendingIntent(Context context, int alarmId) {
        Intent intent = new Intent(context, RingtoneService.class);
        intent.setAction(RingtoneService.ACTION_STOP_RINGTONE);
        return PendingIntent.getService(context, alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private PendingIntent getLaunchAppPendingIntent(Context context, int alarmId) {
        return PendingIntent.getActivity(context, alarmId, new Intent(context, SplashActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Intent getStartRingtoneIntent(Context context) {
        Intent intent = new Intent(context, RingtoneService.class);
        intent.setAction(RingtoneService.ACTION_START_RINGTONE);
        return intent;
    }*/
}
