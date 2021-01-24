package com.kochchi.recycleview_test;


import android.app.NotificationManager;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.AsyncTask;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;


import com.kochchi.recycleview_test.eventdb.EventDatabase;
import com.kochchi.recycleview_test.eventdb.model.Event;

import java.lang.ref.WeakReference;

import java.util.ArrayList;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MyListAdapter.OnNoteItemClick{
    private TextView textViewMsg;
    private RecyclerView recyclerView;
    private EventDatabase eventDatabase;
    private List<Event> events;
    private MyListAdapter myListAdapter;
    private int pos;
    public static final int REQUEST_CODE=101;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVies();
        displayList();


    }

    private void displayList(){
        eventDatabase = EventDatabase.getInstance(MainActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<Event>> {

        private WeakReference<MainActivity> activityReference;

        // only retain a weak reference to the activity
        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<Event> doInBackground(Void... voids) {
            if (activityReference.get()!=null)
                return activityReference.get().eventDatabase.getEventDao().getEvents();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<Event> events) {
            if (events!=null && events.size()>0 ){
                activityReference.get().events.clear();
                activityReference.get().events.addAll(events);
                // hides empty text view
                activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().myListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void initializeVies(){
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        textViewMsg =  (TextView) findViewById(R.id.tv__empty);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        events = new ArrayList<>();
        myListAdapter = new MyListAdapter(events,MainActivity.this);
        recyclerView.setAdapter(myListAdapter);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(MainActivity.this,AddEventActivity.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                events.add((Event) data.getSerializableExtra("event"));
            }else if( resultCode == 2){
                events.set(pos,(Event) data.getSerializableExtra("event"));
            }
            listVisibility();
        }
    }

    @Override
    public void onNoteClick(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Delete", "Update"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                SetAlarm setAlarm = new SetAlarm();
                                setAlarm.removeAlarms(events, MainActivity.this);
                                eventDatabase.getEventDao().deleteEvent(events.get(pos));
                                events.remove(pos);
                                listVisibility();
                                //AddEventActivity addEventActivity = new AddEventActivity();
                                //addEventActivity.set_flag(3);
                                break;
                            case 1:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(MainActivity.this,
                                                AddEventActivity.class).putExtra("event",events.get(pos)),
                                        100);

                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        int emptyMsgVisibility = View.GONE;
        if (events.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);
        myListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        eventDatabase.cleanUp();
        super.onDestroy();
    }


}

