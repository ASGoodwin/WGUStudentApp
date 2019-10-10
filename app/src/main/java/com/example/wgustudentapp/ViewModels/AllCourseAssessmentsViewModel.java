package com.example.wgustudentapp.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.Model.Repositories.AssessmentRepository;

import java.util.List;

public class AllCourseAssessmentsViewModel extends AndroidViewModel {

    private AssessmentRepository repository;
    private LiveData<List<Assessment>> alAssessments;

    public AllCourseAssessmentsViewModel(@NonNull Application application) {
        super(application);
        repository = new AssessmentRepository(application);
        alAssessments = repository.getAlAssessments();
    }

    public void insertAssessment(Assessment assessment) { repository.insertAssessment(assessment);}

    public void updateAssessment(Assessment assessment) { repository.updateAssessment(assessment);}

    public void deleteAssessment(Assessment assessment) { repository.deleteAssessment(assessment);}

    public LiveData<List<Assessment>> getAlAssessments() { return alAssessments; }
}
