package com.kochchi.recycleview_test.eventdb.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.kochchi.recycleview_test.eventdb.Converters;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "event_db")
public class Event implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long event_id;

    @ColumnInfo(name = "name")
    private String Name;

    @ColumnInfo(name = "Exp_date")
    @TypeConverters({Converters.class})
    private Date expDate;

    @ColumnInfo(name = "Notify_days")
    @TypeConverters({Converters.class})
    private Date NDate;

    public Event(String name, Date date, Date ndate) {
        this.Name = name;
        this.expDate = date;
        this.NDate = ndate;
    }

    public Event(){}

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getName() {
        return Name;
    }

    public long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(long event_id) {
        this.event_id = event_id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Date getNDate() {
        return NDate;
    }

    public void setNDate(Date NDate) {
        this.NDate = NDate;
    }
}