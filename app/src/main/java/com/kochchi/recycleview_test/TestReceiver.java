package com.kochchi.recycleview_test;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TestReceiver extends BroadcastReceiver{

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static NotificationManager mNotifyManager;
    //private static Context ctx;
    private static final int NOTIFICATION_ID = 0;

    public String n;
    public  long x;

    @Override
    public void onReceive(Context context, Intent intent){

         //intent = new Intent(context, AddEventActivity.class);
        Bundle extras = intent.getExtras();

        n = extras.getString("name");
        Log.e("n", "name of exp for noti " + n);
        x = extras.getLong("noD");
        Log.e("n1", "no of d for exp " + x);

         //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

         //context.startActivity(intent);

        //Log.e("in", "alarm num: " + n );

        //if(AddEventActivity.alarmManager != null){
           // AddEventActivity.alarmManager.cancel(AddEventActivity.pendingIntent);
        //}
        //AddEventActivity.alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);
       // AddEventActivity.pendingIntent = PendingIntent.getBroadcast(context, 120, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Calendar calendar = new
        //AddEventActivity.alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AddEventActivity.pendingIntent);
        //AddEventActivity.count++;
        //AddEventActivity addEventActivity = new AddEventActivity();
        //addEventActivity.setMultiAlarm();
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
                .setContentTitle(n + " Expires in " + x + " days! ")
                .setSmallIcon(R.drawable.noti_alarm);
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
        //eName + " Expires in " + edays + " days! "

    }



}
