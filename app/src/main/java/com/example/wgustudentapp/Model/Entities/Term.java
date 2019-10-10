package com.example.wgustudentapp.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Calendar;

@Entity
public class Term {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo
    private String name;
    @ColumnInfo
    private long startDate;
    @ColumnInfo
    private long endDate;



    public Term(){}

    @Ignore
    public Term(String name, Calendar startDate, Calendar endDate){ //takes calendar entries and converts to longs
        this.name = name;
        this.startDate = startDate.getTimeInMillis();
        this.endDate = endDate.getTimeInMillis();
    }

    @Ignore
    public Term(int id, String name, long startDate, long endDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    //Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    //Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }







}
