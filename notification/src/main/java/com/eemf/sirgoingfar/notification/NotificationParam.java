package com.eemf.sirgoingfar.notification;

import android.app.PendingIntent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

public class NotificationParam {

    public static Uri DEFAULT_SOUND = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

    private int id;
    private int priorityType;
    @DrawableRes
    private int smallIcon; // = R.drawable.ic_launcher;
    @DrawableRes
    private int largeIcon; //= R.drawable.ic_launcher;
    @DrawableRes
    private int btnOneIconResId;
    @DrawableRes
    private int btnTwoIconResId;
    @DrawableRes
    private int btnThreeIconResId;
    @ColorRes
    private int bodyTextColor = R.color.colorBlack;
    private int lightColor = Color.RED;

    private String title = "Timely";
    private String body;
    private String btnOneText;
    private String btnTwoText;
    private String btnThreeText;

    private boolean shouldVibrate;
    private boolean shouldSound;
    private boolean isDismissable;
    private boolean isAutoCancel;
    private boolean removePreviousNotif;

    private Uri soundUri = DEFAULT_SOUND;

    private PendingIntent bodyPendingIntent;
    private PendingIntent btnOnePendingIntent;
    private PendingIntent btnTwoPendingIntent;
    private PendingIntent btnThreePendingIntent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(int priorityType) {
        this.priorityType = priorityType;
    }

    public int getSmallIcon() {
        return smallIcon;
    }

    public void setSmallIcon(int smallIcon) {
        this.smallIcon = smallIcon;
    }

    public int getLargeIcon() {
        return largeIcon;
    }

    public void setLargeIcon(int largeIcon) {
        this.largeIcon = largeIcon;
    }

    public int getBtnOneIconResId() {
        return btnOneIconResId;
    }

    public void setBtnOneIconResId(int btnOneIconResId) {
        this.btnOneIconResId = btnOneIconResId;
    }

    public int getBtnTwoIconResId() {
        return btnTwoIconResId;
    }

    public void setBtnTwoIconResId(int btnTwoIconResId) {
        this.btnTwoIconResId = btnTwoIconResId;
    }

    public int getBtnThreeIconResId() {
        return btnThreeIconResId;
    }

    public void setBtnThreeIconResId(int btnThreeIconResId) {
        this.btnThreeIconResId = btnThreeIconResId;
    }

    public int getBodyTextColor() {
        return bodyTextColor;
    }

    public void setBodyTextColor(int bodyTextColor) {
        this.bodyTextColor = bodyTextColor;
    }

    public int getLightColor() {
        return lightColor;
    }

    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBtnOneText() {
        return btnOneText;
    }

    public void setBtnOneText(String btnOneText) {
        this.btnOneText = btnOneText;
    }

    public String getBtnTwoText() {
        return btnTwoText;
    }

    public void setBtnTwoText(String btnTwoText) {
        this.btnTwoText = btnTwoText;
    }

    public String getBtnThreeText() {
        return btnThreeText;
    }

    public void setBtnThreeText(String btnThreeText) {
        this.btnThreeText = btnThreeText;
    }

    public boolean shouldVibrate() {
        return shouldVibrate;
    }

    public void setShouldVibrate(boolean shouldVibrate) {
        this.shouldVibrate = shouldVibrate;
    }

    public boolean shouldSound() {
        return shouldSound;
    }

    public void setShouldSound(boolean shouldSound) {
        this.shouldSound = shouldSound;
    }

    public boolean isDismissable() {
        return isDismissable;
    }

    public void setDismissable(boolean dismissable) {
        isDismissable = dismissable;
    }

    public boolean isAutoCancel() {
        return isAutoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        isAutoCancel = autoCancel;
    }

    public boolean shouldRemovePreviousNotif() {
        return removePreviousNotif;
    }

    public void setRemovePreviousNotif(boolean removePreviousNotif) {
        this.removePreviousNotif = removePreviousNotif;
    }

    public Uri getSoundUri() {
        return soundUri;
    }

    public void setSoundUri(Uri soundUri) {
        this.soundUri = soundUri;
    }

    public PendingIntent getBodyPendingIntent() {
        return bodyPendingIntent;
    }

    public void setBodyPendingIntent(PendingIntent bodyPendingIntent) {
        this.bodyPendingIntent = bodyPendingIntent;
    }

    public PendingIntent getBtnOnePendingIntent() {
        return btnOnePendingIntent;
    }

    public void setBtnOnePendingIntent(PendingIntent btnOnePendingIntent) {
        this.btnOnePendingIntent = btnOnePendingIntent;
    }

    public PendingIntent getBtnTwoPendingIntent() {
        return btnTwoPendingIntent;
    }

    public void setBtnTwoPendingIntent(PendingIntent btnTwoPendingIntent) {
        this.btnTwoPendingIntent = btnTwoPendingIntent;
    }

    public PendingIntent getBtnThreePendingIntent() {
        return btnThreePendingIntent;
    }

    public void setBtnThreePendingIntent(PendingIntent btnThreePendingIntent) {
        this.btnThreePendingIntent = btnThreePendingIntent;
    }
}
