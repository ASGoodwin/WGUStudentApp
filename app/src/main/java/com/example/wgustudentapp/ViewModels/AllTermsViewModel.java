package com.example.wgustudentapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.Model.Repositories.CourseRepository;
import com.example.wgustudentapp.Model.Repositories.TermRepository;

import java.util.List;

public class AllTermsViewModel extends AndroidViewModel {

    //Properties
    private TermRepository termRepository;
    private CourseRepository courseRepository;
    private LiveData<List<Term>> alTerms; //List of Terms
    private LiveData<List<Course>> alCourses;

    //Constructor
    public AllTermsViewModel(@NonNull Application application) {
        super(application);
        termRepository = new TermRepository(application);
        alTerms = termRepository.getAlTerm();

        courseRepository = new CourseRepository(application);
        alCourses = courseRepository.getAlCourses();
    }

    public void insertTerm(Term term){
        termRepository.insertTerm(term);
    }

    public void deleteTerm(Term term){
        termRepository.deleteTerm(term);
    }

    public LiveData<List<Term>> getAlTerms(){
        return alTerms;
    }

    public LiveData<List<Course>> getAlCourses() { return alCourses ; }

}
