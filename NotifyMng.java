package com.example.mqttapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotifyMng {
    public  static void createChannel (Context mContext,
                                       String chId,
                                       String chName,
                                       int impt) {
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel(chId, chName, impt );
            nc.enableLights(true);
            assert nm != null;
            nm.createNotificationChannel(nc);
        }
    }

    public static void callNotify4mess(Context mContext,
                                  int id,
                                  String chId,
                                  String title,
                                  String text) {
        NotificationManager nm = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification.Builder nb = new Notification.Builder(mContext)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setVibrate(new long[] {200, 200, 200, 200, 200});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nb.setChannelId(chId);
        }
        nm.notify(id, nb.build());
    }
    public static Notification callNotify4mainService(Context mContext,
                                  String chId,
                                  String title,
                                  String text) {
        Notification.Builder nb = new Notification.Builder(mContext)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setVibrate(new long[] {200, 200, 200, 200, 200});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nb.setChannelId(chId);
        }
        return nb.build();
    }
}
