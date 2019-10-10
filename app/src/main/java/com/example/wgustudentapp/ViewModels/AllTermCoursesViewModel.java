package com.example.wgustudentapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Repositories.CourseRepository;

import java.util.List;

public class AllTermCoursesViewModel extends AndroidViewModel {

    private CourseRepository repository;
    private LiveData<List<Course>> alCourses;

    public AllTermCoursesViewModel(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        alCourses = repository.getAlCourses();
    }

    public void insertCourse(Course course){
        repository.insertCourse(course);
    }

    public void updateCourse(Course course){
        repository.updateCourse(course);
    }

    public void deleteCourse(Course course){ repository.deleteCourse(course); }

    public LiveData<List<Course>> getAlCourses(){
        return alCourses;
    }
}
