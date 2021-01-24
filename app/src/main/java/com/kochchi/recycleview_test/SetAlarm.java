package com.kochchi.recycleview_test;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kochchi.recycleview_test.eventdb.model.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SetAlarm extends AppCompatActivity {

    //private AlarmManager alarmManager;
    //private Intent intent;
    //private PendingIntent pendingIntent;
    public static AlarmManager alarmManager;
    public static Context con;
    List<Event> ex;
    //Intent intent;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        set(this, ex, 0);




    }

    public Context set_context(){
        return this;
    }


    public void set(Context context, List<Event> list, int flag){

        /*List<Integer> secounds = new ArrayList<>();
        secounds.add(5);
        secounds.add(15);
        secounds.add(30);

        Log.e("con2","event element" + list.get(0));*/



        //AlarmManager [] alarmManagers = new AlarmManager[secounds.size()];
        //AlarmManager [] al = new AlarmManager[4];
           /* if(alarmManager != null){
                alarmManager.cancel(pendingIntent);
                //pendingIntent.cancel();
                Log.e("in2", "inside cancel alarm");
            }*/
        //Context context = this;

        if(flag == 1){
            Log.e("in4", "inside flag 1");

            Intent intents[] = new Intent[list.size()];
            for (int i = 0; i < list.size(); i++) {
                intents[i] = new Intent(context, TestReceiver.class);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intents[i], 0);
                SetAlarm.alarmManager.cancel(pendingIntent);
                Log.e("in5", "inside cancel alarm!");
            }

            //Intent intents [] = new Intent[list.size()];
            for(int i = 0; i < list.size(); i++) {
                //int i = (list.size() - 1);

                //Log.e("con", "context" + i);

                intents[i] = new Intent(context, TestReceiver.class);
                intents[i].putExtra("name", list.get(i).getName());

                Date n_date = list.get(i).getNDate();
                long diff = list.get(i).getExpDate().getTime() - n_date.getTime();
                intents[i].putExtra("noD", TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));



                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(System.currentTimeMillis());
                c.setTime(n_date);
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int date = c.get(Calendar.DAY_OF_MONTH);

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(year, month, date, 8, 00);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intents[i], 0);

                alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
                Log.e("a", "alarm d" + calendar.getTime());
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
        else if(flag == 2){

            Log.e("con9", "inside flag 2");

            Intent intents[] = new Intent[list.size()];
            //for(int i = 0; i < secounds.size(); i++){
            int i = (list.size() - 1);

            Log.e("con", "event no" + context);

            intents[i] = new Intent(context, TestReceiver.class);
            intents[i].putExtra("name", list.get(i).getName());

            Date n_date = list.get(i).getNDate();
            long diff = list.get(i).getExpDate().getTime() - n_date.getTime();
            intents[i].putExtra("noD", TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));


            //intents[i].putExtra("time", secounds.get(i).toString());
            //intent[count] = new Intent(getApplicationContext(), TestReceiver.class);
            //int i = (int) System.currentTimeMillis();
            //pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), i, intents[i], 0);
            //int cr = 5 + count;

            //Date n_date = list.get(i).getNDate();
            Calendar c = Calendar.getInstance();
            c.setTimeInMillis(System.currentTimeMillis());
            c.setTime(n_date);
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int date = c.get(Calendar.DAY_OF_MONTH);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(year, month, date, 8, 00);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intents[i], 0);
            //if(count != 0){
            //Calendar calendar = Calendar.getInstance();
            //calendar.set(Calendar.SECOND, secounds.get(i));
            //calendar.
            alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
            Log.e("a", "alarm d" + calendar.getTime());
            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            //}
        }

    }
    public void removeAlarms(List<Event> list, Context context){
        Intent intents[] = new Intent[list.size()];
        for (int i = 0; i < list.size(); i++) {
            intents[i] = new Intent(context, TestReceiver.class);

            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, i, intents[i], 0);
            SetAlarm.alarmManager.cancel(pendingIntent);
            Log.e("in5", "inside cancel alarm!");

        }
    }
  }