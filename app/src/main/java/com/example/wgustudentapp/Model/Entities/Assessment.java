package com.example.wgustudentapp.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Assessment {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo
    private int courseId;
    @ColumnInfo
    private String type;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private long startDate;
    @ColumnInfo
    private long dueDate;
    @ColumnInfo
    private String notes;

    public Assessment(){}

    @Ignore
    public Assessment(int courseId, String type, String title, long startDate, long dueDate, String notes) {

        this.courseId = courseId;
        this.type = type;
        this.title = title;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.notes = notes;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(long startDate) { this.startDate = startDate; }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    //Getter
    public int getId() {
        return id;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public long getStartDate() { return startDate; }

    public long getDueDate() {
        return dueDate;
    }

    public String getNotes() {
        return notes;
    }
}
