package com.kochchi.recycleview_test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class BootReceiver extends BroadcastReceiver {
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static NotificationManager mNotifyManager;
    //private static Context ctx;
    private static final int NOTIFICATION_ID = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        //if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {

           // AddEventActivity addEventActivity = new AddEventActivity();
            // ddEventActivity.set_flag(1);
        //}
        mNotifyManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "expire notification", NotificationManager
                    .IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from exp");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
        //sendNotification();
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                //NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this, PRIMARY_CHANNELID)
                .setContentTitle("test on boot ")
                .setSmallIcon(R.drawable.noti_alarm);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());

        AddEventActivity addEventActivity = new AddEventActivity();
        addEventActivity.set_flag(1);

    }
}
