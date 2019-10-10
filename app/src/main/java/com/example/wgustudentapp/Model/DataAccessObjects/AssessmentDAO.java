package com.example.wgustudentapp.Model.DataAccessObjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wgustudentapp.Model.Entities.Assessment;

import java.util.List;

@Dao
public interface AssessmentDAO {

    @Insert
    void insertAssessment(Assessment assessment);

    @Update
    void updateAssessment(Assessment assessment);

    @Delete
    void deleteAssessment(Assessment assessment);

    @Query("select * from assessment where courseId = :courseId order by dueDate")
    LiveData<List<Assessment>> getAssessmentList(int courseId);
}
