package com.example.wgustudentapp.Model.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.AppDatabase;
import com.example.wgustudentapp.Model.DataAccessObjects.AssessmentDAO;
import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.View.Adapters.AllCourseAssessmentsAdapter;

import java.util.List;

public class AssessmentRepository {

    private AssessmentDAO assessmentDao;
    private LiveData<List<Assessment>> alAssessment;

    private AllCourseAssessmentsAdapter allCourseAssessmentsAdapter = new AllCourseAssessmentsAdapter();

    public AssessmentRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application);
        assessmentDao = db.assessmentDao();
        alAssessment = assessmentDao.getAssessmentList(allCourseAssessmentsAdapter.getCourseId());
    }

    public void insertAssessment(Assessment assessment){ new InsertAssessmentAsyncTask(assessmentDao).execute(assessment); }

    public void updateAssessment(Assessment assessment){ new UpdateAssessmentAsyncTask(assessmentDao).execute(assessment); }

    public void deleteAssessment(Assessment assessment) { new DeleteAssessmentAsyncTask(assessmentDao).execute(assessment); }

    public LiveData<List<Assessment>> getAlAssessments() {
        return alAssessment;
    }

    public static class InsertAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void> {

        private AssessmentDAO assessmentDao;

        private InsertAssessmentAsyncTask(AssessmentDAO assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.insertAssessment(assessments[0]);
            return null;
        }
    }

    public static class UpdateAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{

        private AssessmentDAO assessmentDao;

        private UpdateAssessmentAsyncTask(AssessmentDAO assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.updateAssessment(assessments[0]);
            return null;
        }
    }

    public static class DeleteAssessmentAsyncTask extends AsyncTask<Assessment, Void, Void>{

        private AssessmentDAO assessmentDao;

        private DeleteAssessmentAsyncTask(AssessmentDAO assessmentDao) { this.assessmentDao = assessmentDao; }

        @Override
        protected Void doInBackground(Assessment... assessments) {
            assessmentDao.deleteAssessment(assessments[0]);
            return null;
        }
    }
}
