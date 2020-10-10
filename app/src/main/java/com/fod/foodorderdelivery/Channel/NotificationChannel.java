package com.fod.foodorderdelivery.Channel;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationChannel {

    public NotificationChannel(Context nContext) {
        this.nContext = nContext;
    }

    Context nContext;
    public final static String CHANNEL_1 = "Channel1";
    public final static String CHANNEL_2 = "Channel2";


    public void notificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel1 = new android.app.NotificationChannel(
                    CHANNEL_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is channel 1");

            android.app.NotificationChannel channel2 = new android.app.NotificationChannel(
                    CHANNEL_2,
                    "Channel 2",
                    NotificationManager.IMPORTANCE_LOW

            );

            channel2.setDescription("this  is channel 2");

            NotificationManager manager = nContext.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }

    }
}
