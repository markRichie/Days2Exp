package com.kochchi.recycleview_test.eventdb.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.kochchi.recycleview_test.eventdb.model.Event;

import java.util.List;


@Dao
public interface EventDao {

    @Query("SELECT * FROM event_db")
    List<Event> getEvents();

    @Query("SELECT * FROM event_db ORDER BY Notify_days ASC")
    List<Event> getNDate();

    /*
    * Insert the object in database
    * @param note, object to be inserted
    */
    @Insert
    long insertEvent(Event event);

    /*
    * update the object in database
    * @param note, object to be updated
    */
    @Update
    void updateEvent(Event event);

    /*
    * delete the object from database
    * @param note, object to be deleted
    */
    @Delete
    void deleteEvent(Event event);

    // Note... is varargs, here note is an array
    /*
    * delete list of objects from database
    * @param note, array of oject to be deleted
    */
    @Delete
    void deleteEvents(Event... event);

}
