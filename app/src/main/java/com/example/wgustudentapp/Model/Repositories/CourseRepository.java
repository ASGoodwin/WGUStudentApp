package com.example.wgustudentapp.Model.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.wgustudentapp.Model.AppDatabase;
import com.example.wgustudentapp.Model.DataAccessObjects.CourseDAO;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.View.Adapters.AllTermCoursesAdapter;

import java.util.List;

public class CourseRepository {

    private CourseDAO courseDao;
    private LiveData<List<Course>> alCourse;
    private int courseCount;

    private AllTermCoursesAdapter allTermCoursesAdapter = new AllTermCoursesAdapter();

    public CourseRepository(Application application) {

        AppDatabase db = AppDatabase.getInstance(application);
        courseDao = db.courseDao();
        alCourse = courseDao.getCourseList(allTermCoursesAdapter.getTermId());
    }

    public void insertCourse(Course course){ new InsertCourseAsyncTask(courseDao).execute(course); }

    public void updateCourse(Course course){ new UpdateCourseAsyncTask(courseDao).execute(course); }

    public void deleteCourse(Course course){ new DeleteCourseAsyncTask(courseDao).execute(course); }

    public LiveData<List<Course>> getAlCourses() { return alCourse; }

    public static class InsertCourseAsyncTask extends AsyncTask<Course, Void, Void>{

        private CourseDAO courseDao;

        private InsertCourseAsyncTask(CourseDAO courseDao) { this.courseDao = courseDao; }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.insertCourse(courses[0]);
            return null;
        }
    }

    public static class UpdateCourseAsyncTask extends AsyncTask<Course, Void, Void>{

        private CourseDAO courseDao;

        private UpdateCourseAsyncTask(CourseDAO courseDao) { this.courseDao = courseDao; }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.updateCourse(courses[0]);
            return null;
        }
    }

    public static class DeleteCourseAsyncTask extends AsyncTask<Course, Void, Void>{

        private CourseDAO courseDao;

        private DeleteCourseAsyncTask(CourseDAO courseDao) { this.courseDao = courseDao; }

        @Override
        protected Void doInBackground(Course... courses) {
            courseDao.deleteCourse(courses[0]);
            return null;
        }
    }
}
