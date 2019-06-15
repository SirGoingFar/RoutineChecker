package com.eemf.sirgoingfar.routinechecker.alarm

class AlarmHelper/*public static final int ACTION_SCHEDULE_ALARM = 0;
    public static final int ACTION_UPDATE_ALARM = 1;
    public static final int ACTION_DELETE_ALARM = 2;

    public static final String KEY_EXTRA_ACTIVITY = "key_extra_activity";

    private AlarmManager mAlarmManager;
    private Context mContext;

    public AlarmHelper() {
        if (mContext == null)
            mContext = App.getsInstance().getAppContext();

        if (mAlarmManager == null)
            mAlarmManager = (AlarmManager) App.getsInstance().getAppContext()
                    .getSystemService(Context.ALARM_SERVICE);
    }

    public void execute(final TodoActivity activity, final int action) {
        if(Helper.hasTimePassed(activity.getStartTime()))
            return;

        new Thread(() -> {
            if (activity == null)
                throw new IllegalArgumentException(mContext.getString(R.string.text_activity_is_null));

            if (action < 0)
                throw new IllegalArgumentException(mContext.getString(R.string.text_invalid_action_identifier));

            switch (action) {
                case ACTION_SCHEDULE_ALARM:
                    schedule(activity, false);
                    break;

                case ACTION_UPDATE_ALARM:
                    update(activity);
                    break;

                case ACTION_DELETE_ALARM:
                    delete(activity);
                    break;
            }
        }).run();
    }

    public void execute(List<TodoActivity> activityList, int action) {
        new Thread(() -> {
            for (TodoActivity activity : activityList)
                execute(activity, action);
        }).run();
    }

    private void delete(TodoActivity activity) {
        PendingIntent pendingIntent = getPendingIntentFor(activity, false);
        mAlarmManager.cancel(pendingIntent);
    }

    private void update(TodoActivity activity) {
        //delete the previously scheduled alarm for this activity
        delete(activity);

        //re-schedule
        schedule(activity, true);
    }

    private void schedule(TodoActivity activity, boolean isUpdate) {
        PendingIntent pendingIntent = getPendingIntentFor(activity, isUpdate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, activity.getStartTime().getTime(), pendingIntent);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mAlarmManager.setExact(AlarmManager.RTC_WAKEUP, activity.getStartTime().getTime(), pendingIntent);
        } else {
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, activity.getStartTime().getTime(), pendingIntent);
        }
    }

    private PendingIntent getPendingIntentFor(TodoActivity activity, boolean isUpdate) {
        Intent alarmIntent = new Intent(mContext, AlarmReceiver.class);
        byte[] activityByte = ParcelableUtil.marshall(activity);
        alarmIntent.putExtra(KEY_EXTRA_ACTIVITY, activityByte);
        alarmIntent.setData(Uri.parse(mContext.getString(R.string.prefix_text) + activity.getAlarmId()));
        alarmIntent.setAction(String.valueOf(activity.getAlarmId()));

        if (isUpdate) {
            alarmIntent.putExtra(AlarmReceiver.EXTRA_KEY_ALARM_RECEIVER_ACTION, AlarmReceiver.ALARM_ACTION_UPDATE_ALARM);
        }

        return PendingIntent.getBroadcast(mContext, activity.getAlarmId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }*/
