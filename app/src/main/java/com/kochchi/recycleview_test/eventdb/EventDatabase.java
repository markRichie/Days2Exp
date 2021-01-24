package com.kochchi.recycleview_test.eventdb;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.kochchi.recycleview_test.eventdb.dao.EventDao;
import com.kochchi.recycleview_test.eventdb.model.Event;


@Database(entities = { Event.class }, version = 3)
public abstract class EventDatabase extends RoomDatabase {

    public abstract EventDao getEventDao();


    private static EventDatabase eventDB;

    // synchronized is use to avoid concurrent access in multithred environment
    public static /*synchronized*/ EventDatabase getInstance(Context context) {
        if (null == eventDB) {
            eventDB = buildDatabaseInstance(context);
        }
        return eventDB;
    }

    private static EventDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                EventDatabase.class,
                "event_db").allowMainThreadQueries().build();
    }

    public  void cleanUp(){
        eventDB = null;
    }
}