package com.kochchi.recycleview_test;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.kochchi.recycleview_test.eventdb.EventDatabase;
import com.kochchi.recycleview_test.eventdb.model.Event;

import java.lang.ref.WeakReference;
import java.util.List;

public class TimeChangeReceiver extends BroadcastReceiver {
    private EventDatabase eventDatabase;
    private static Context cntx;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        //cntx = context;

        if (Intent.ACTION_TIME_CHANGED.equals(action) || Intent.ACTION_TIMEZONE_CHANGED.equals(action)) {
            // cancel previous alarm and set a new one

            //eventDatabase = EventDatabase.getInstance(context);
            //new RetrieveDaysTask(TimeChangeReceiver.this).execute();

            AddEventActivity addEventActivity = new AddEventActivity();
            addEventActivity.set_flag(1);

           // AddEventActivity addEventActivity = new AddEventActivity();
           // addEventActivity.setMultiAlarm();
        }
    }

    private static class RetrieveDaysTask extends AsyncTask<Void, Void, List<Event>> {

        private int ct;

        private WeakReference<TimeChangeReceiver> activityReference;


        // only retain a weak reference to the activity
        RetrieveDaysTask(TimeChangeReceiver context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().eventDatabase.getEventDao().getNDate();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            AddEventActivity addEventActivity = new AddEventActivity();

            Log.e("cnt", "context " + cntx);

            if (events != null && events.size() > 0) {
                //cntx = addEventActivity.set_context();

                Intent intents[] = new Intent[events.size()];
                for (int i = 0; i < events.size(); i++) {
                    intents[i] = new Intent(cntx, TestReceiver.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(cntx, i, intents[i], 0);
                    SetAlarm.alarmManager.cancel(pendingIntent);
                    Log.e("in5", "inside cancel alarm!");
                }

                //List<Date> ntfy_date = new ArrayList<>();
                //SetAlarm setAlarm = new SetAlarm();
                //setAlarm.set(cntx, events, 1);

                //Log.e("evt", "event id")


                //for(int i = 0; i < events.size(); i++){
                //ntfy_date.add(events.get(i).getNDate());
                //}


               /*listLen = events.size();

               Date currentDate = new Date();
               //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM--dd");
               for(int i = 0; i < listLen; i++){
                   if(currentDate.compareTo(events.get(i).getNDate()) < 0){
                       ct = i;
                       break;
                   }
               }
               Log.e("bind5", "Add current Date: "+ currentDate);
               Log.e("bind5", "Add Exp Date: "+ events.get(ct).getNDate());

               long diff = events.get(ct).getExpDate().getTime() - events.get(ct).getNDate().getTime();

               rtDate = events.get(ct).getNDate();
               //eName = events.get(count).getName();
               //edays = Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
               Log.e("bind2", "onBindViewHolder: "+ rtDate );

               //runAlarm();
               Calendar c = Calendar.getInstance();
               c.setTimeInMillis(System.currentTimeMillis());
               c.setTime(rtDate);
               int year = c.get(Calendar.YEAR);
               int month = c.get(Calendar.MONTH);
               int date = c.get(Calendar.DAY_OF_MONTH);

               //Log.e("bind4", "onBindViewHolder: "+ date);

               Calendar calendar = Calendar.getInstance();
               calendar.setTimeInMillis(System.currentTimeMillis());
               calendar.set(year, month, date, 12, 38);

               Log.e("bind3", "setting alarm: "+ calendar.getTime());

               //_id = (int) System.currentTimeMillis();
               //AddEventActivity addEve = new AddEventActivity();
               Calendar cl = Calendar.getInstance();
               cl.setTimeInMillis(System.currentTimeMillis());
               cl.set(2019, 8, 30, 20, 15);
               if (c1 == 2){
                   Log.e("2nd","inside 2 alarm");
                   alarmManager1.setExact(AlarmManager.RTC, cl.getTimeInMillis(), pendingIntent1);
               }
               else {
                   alarmManager.setExact(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);
               }*/


            }
        }
    }
}
