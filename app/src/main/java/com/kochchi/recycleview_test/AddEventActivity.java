package com.kochchi.recycleview_test;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kochchi.recycleview_test.eventdb.EventDatabase;
import com.kochchi.recycleview_test.eventdb.model.Event;

import java.lang.ref.WeakReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;



public class AddEventActivity extends AppCompatActivity {
    private EditText et_name, et_date, et_nDate;
    private DatePickerDialog picker;
    private EventDatabase eventDatabase;
    private Event event;
    private TextView nameError, dateError, notifydError;
    private boolean update;
    private Date expDate, nExpDate;
    public static AlarmManager alarmManager;
    public Intent [] intent = new Intent[3];
    public static PendingIntent pendingIntent;
    public static int count = 0;
    public static Date rtDate;
    public static int listLen;
    public static  int _id = 102;
    public static String eName, edays, e_name;
    private ImageButton im_date;
    private boolean vali;
    private int x;
    public static int flag = 0;
    public AlarmManager [] alarmManagers = new AlarmManager[1];
    public static Context cnt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event_activity);
        et_name = findViewById(R.id.et_name);
        et_date = findViewById(R.id.et_date);
        et_nDate = findViewById(R.id.et_notifyDate);
        im_date = findViewById(R.id.calenda_btn);
        nameError = findViewById(R.id.name_error);
        dateError = findViewById(R.id.date_error);
        notifydError = findViewById(R.id.notifyD_error);
        eventDatabase = EventDatabase.getInstance(AddEventActivity.this);
        Button button = findViewById(R.id.button);
        x = 0;



        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        cnt = this;




        if ( (event = (Event) getIntent().getSerializableExtra("event"))!=null ){
            getSupportActionBar().setTitle("Update Event");
            update = true;
            button.setText("Update");
            et_name.setText(event.getName());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String dateTime = dateFormat.format(event.getExpDate());
                et_date.setText(dateTime);
            } catch (Exception e) {
                e.printStackTrace();
            }


            long diff = event.getExpDate().getTime() - event.getNDate().getTime();

            et_nDate.setText(Long.toString(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)));

        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                print();

                if ( x == 1) {


                }else{
                    if (update) {

                        event.setName(et_name.getText().toString());


                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            expDate = format.parse(et_date.getText().toString());

                            //date to notify
                            Calendar c = Calendar.getInstance();
                            c.setTime(expDate);
                            c.add(Calendar.DATE, -Integer.parseInt(et_nDate.getText().toString()));
                            nExpDate = c.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        event.setExpDate(expDate);
                        event.setNDate(nExpDate);
                        eventDatabase.getEventDao().updateEvent(event);
                        setResult(event, 2);
                    } else {
                        //getting exp date
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        try {
                            expDate = format.parse(et_date.getText().toString());

                            //date to notify
                            Calendar c = Calendar.getInstance();
                            c.setTime(expDate);
                            c.add(Calendar.DATE, -Integer.parseInt(et_nDate.getText().toString()));
                            nExpDate = c.getTime();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        //et_date = (EditText)findViewById(R.id.et_date);
                        Log.e("bind1", "onBindViewHolder: " + nExpDate);
                        Log.e("bind1", "exp: " + expDate);

                        //eventDatabase = EventDatabase.getInstance(AddEventActivity.this);

                        event = new Event(et_name.getText().toString(), expDate, nExpDate);
                        new InsertTask(AddEventActivity.this, event).execute();


                        //initRet();

                        //new RetrieveDaysTask(AddEventActivity.this).execute();
                        set_flag(2);

                    }
                }
            }
            //}
        });

        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });

        im_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddEventActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                et_date.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }


    public void set_flag(int f){
        flag = f;
        eventDatabase = EventDatabase.getInstance(AddEventActivity.this);
        new RetrieveDaysTask(AddEventActivity.this).execute();
    }



    public void print(){
        String edate, nday;
        e_name = et_name.getText().toString();
        edate = et_date.getText().toString();
        nday = et_nDate.getText().toString();
        x = 0;

        nameError.setText("");
        dateError.setText("");
        notifydError.setText("");

        Log.e("ID ", "inside validation" + e_name );
        if(e_name.isEmpty() || edate.isEmpty() || nday.isEmpty()) {

            if (e_name.isEmpty()) {
                nameError.setText("*enter product name");
            }
            if (edate.isEmpty()) {
                dateError.setText("*enter Expiry date");
            }
            if (nday.isEmpty()) {
                notifydError.setText("*enter No of days to notify");
            }
            x = 1;
        }
    }



    private void setResult(Event event, int flag){
        setResult(flag,new Intent().putExtra("event",event));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void,Void,Boolean> {

        private WeakReference<AddEventActivity> activityReference;
        private Event event;

        // only retain a weak reference to the activity
        InsertTask(AddEventActivity context, Event event) {
            activityReference = new WeakReference<>(context);
            this.event = event;
        }

        // doInBackground methods runs on a worker thread
        @Override
        protected Boolean doInBackground(Void... objs) {
            // retrieve auto incremented note id
            long j = activityReference.get().eventDatabase.getEventDao().insertEvent(event);
            event.setEvent_id(j);
            Log.e("ID ", "doInBackground: "+j );
            return true;
        }

        // onPostExecute runs on main thread
        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(event,1);
                activityReference.get().finish();
            }


        }
    }


    //this class was static
    private static class RetrieveDaysTask extends AsyncTask<Void,Void, List<Event>> {

        private int ct;
        private WeakReference<AddEventActivity> activityReference;


        // only retain a weak reference to the activity
        RetrieveDaysTask(AddEventActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eventDatabase.getEventDao().getNDate();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
           if (events!=null && events.size()>0 ){


               SetAlarm setAlarm = new SetAlarm();
               setAlarm.set(cnt, events, flag);


            }
        }
    }




}


