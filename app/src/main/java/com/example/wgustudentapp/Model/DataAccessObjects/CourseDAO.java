package com.example.wgustudentapp.Model.DataAccessObjects;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.wgustudentapp.Model.Entities.Course;

import java.util.List;

@Dao
public interface CourseDAO {

    @Insert
    void insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("select * from course where termId = :termId order by startDate") //Used to get all available courses for menu
    LiveData<List<Course>> getCourseList(int termId);
}
