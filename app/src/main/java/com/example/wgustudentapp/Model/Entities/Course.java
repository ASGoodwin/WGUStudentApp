package com.example.wgustudentapp.Model.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Course {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    @ColumnInfo
    private int termId;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private long startDate;
    @ColumnInfo
    private long endDate;
    @ColumnInfo
    private String status;
    @ColumnInfo
    private String mentor;
    @ColumnInfo
    private String phone;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private String notes;

    public Course(){}

    @Ignore
    public Course(int termId, String title, long startDate, long endDate, String status, String mentor, String phone, String email, String notes) {

        this.termId = termId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.mentor = mentor;
        this.phone = phone;
        this.email = email;
        this.notes = notes;
    }

    //Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    //Getter
    public int getId() {
        return id;
    }

    public int getTermId() {
        return termId;
    }

    public String getTitle() {
        return title;
    }

    public long getStartDate() {
        return startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public String getStatus() {
        return status;
    }

    public String getMentor() {
        return mentor;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getNotes() {
        return notes;
    }
}
