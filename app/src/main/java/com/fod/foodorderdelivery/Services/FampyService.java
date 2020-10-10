package com.fod.foodorderdelivery.Services;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.fod.foodorderdelivery.BLL.Orders;
import com.fod.foodorderdelivery.Channel.NotificationChannel;
import com.fod.foodorderdelivery.R;
import com.fod.foodorderdelivery.StrictMode.StrictModeClass;
import com.fod.foodorderdelivery.fragment.BasketFragment;

public class FampyService extends Service {
    public Context context = this;
    public Handler handler = null;
    public Runnable runnable = null;
    private NotificationManagerCompat notificationManagerCompat;
    Orders orders = new Orders();

    @Override
    public void onCreate() {
        super.onCreate();

        notificationManagerCompat = NotificationManagerCompat.from(this);
        NotificationChannel channel = new NotificationChannel(this);
        channel.notificationChannel();

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                StrictModeClass.StrictMode();
                if (orders.deliveryStatus()) {
                    deliveryNotification();
                    BasketFragment.basketList.clear();
                    context.stopService(new Intent(context, FampyService.class));
                }
                handler.postDelayed(runnable, 2000);
            }
        };
        handler.postDelayed(runnable, 2000);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(runnable);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void deliveryNotification() {
        Notification notification = new NotificationCompat.Builder(this, NotificationChannel.CHANNEL_1)
                .setSmallIcon(R.drawable.ic_shopping_basket_black_24dp)
                .setContentTitle("Food delivery")
                .setContentText("Food successfully delivered. Thank you for ordering from Fampy")
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManagerCompat.notify(1, notification);
    }
}
