package com.example.wgustudentapp.View.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermCoursesAdapter;
import com.example.wgustudentapp.ViewModels.AllTermCoursesViewModel;

import java.util.Calendar;
import java.util.List;

import static java.util.Calendar.getInstance;

public class AllTermCoursesActivity extends AppCompatActivity {

    private AllTermCoursesViewModel allTermCoursesViewModel;

    //Extras imported from AllTermsActivity --> SingleTermActivity --> AllTermCoursesActivity
    public static final String EXTRA_TERM_ID = "com.example.WGUStudentApp.EXTRA_TERM_ID";
    public static final String EXTRA_START_DATE = "com.example.WGUStudentApp.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.WGUStudentApp.EXTRA_END_DATE";
    //Extras to export to AddEditCourseActivity
    public static final String EXTRA_COURSE_ID = "com.example.WGUStudentApp.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TERM_ID = "com.example.WGUStudentApp.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_NAME = "com.example.WGUStudentApp.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_STATUS = "com.example.WGUStudentApp.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_START_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_END_DATE";
    public static final String EXTRA_COURSE_MENTOR_NAME = "com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_NAME";
    public static final String EXTRA_COURSE_MENTOR_PHONE = "com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_PHONE";
    public static final String EXTRA_COURSE_MENTOR_EMAIL = "com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_EMAIL";
    public static final String EXTRA_COURSE_NOTES = "com.example.WGUStudentApp.EXTRA_COURSE_NOTES";
    public static final String EXTRA_COURSE_TERM_START_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_TERM_START_DATE";
    public static final String EXTRA_COURSE_TERM_END_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_TERM_END_DATE";

    //Variables to hold extras
    private int termId;
    private long termStartDate;
    private long termEndDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_term_courses);

        //Intent from SingleTermActivity
        Intent intent = getIntent();

        termId = intent.getIntExtra(EXTRA_TERM_ID, -1);
        //Log.d("DEBUG", "ALLTERMCOURSES TERMID IS: " + termId);
        termStartDate = intent.getLongExtra(EXTRA_START_DATE, -1);
        //Log.d("DEBUG", "ALLTERMCOURSES termStartDate IS: " + termStartDate);
        termEndDate = intent.getLongExtra(EXTRA_END_DATE, -1);
        //Log.d("DEBUG", "ALLTERMCOURSES termEndDate IS: " + termEndDate);

        RecyclerView recyclerView = findViewById(R.id.rvCourses);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AllTermCoursesAdapter adapter = new AllTermCoursesAdapter();
        adapter.setTermId(termId);
        recyclerView.setAdapter(adapter);

        allTermCoursesViewModel = ViewModelProviders.of(this).get(AllTermCoursesViewModel.class);
        allTermCoursesViewModel.getAlCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourse(courses);
            }
        });



        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                allTermCoursesViewModel.deleteCourse(adapter.getCourseAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AllTermCoursesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {
                Intent allTermCoursesToEditCourse = new Intent(AllTermCoursesActivity.this, AddEditCourseActivity.class);

                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_ID, course.getId());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_TERM_ID, course.getTermId());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_NAME, course.getTitle());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_STATUS, course.getStatus());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_START_DATE, course.getStartDate());
                //Log.d("DEBUG", "course.getDueDate() is " + course.getDueDate());
                Calendar cStart = getInstance();
                cStart.setTimeInMillis(course.getStartDate());
                //Log.d("DEBUG", "course.getDueDate Year: " + cStart.YEAR + ", Month: " + cStart.MONTH + ", Day: " + cStart.DAY_OF_MONTH);
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_END_DATE, course.getEndDate());
                //Log.d("DEBUG", "course.getEndDate() is " + course.getEndDate());
                Calendar cEnd = getInstance();
                cEnd.setTimeInMillis(course.getEndDate());
                //Log.d("DEBUG", "course.getEndDate Year: " + cEnd.YEAR + ", Month: " + cEnd.MONTH + ", Day: " + cEnd.DAY_OF_MONTH);
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_MENTOR_NAME, course.getMentor());
                //Log.d("DEBUG", "course.getMentor() is " + course.getMentor());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_MENTOR_PHONE, course.getPhone());
                //Log.d("DEBUG", "course.getPhone() is " + course.getPhone());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_MENTOR_EMAIL, course.getEmail());
                //Log.d("DEBUG", "course.getEmail() is " + course.getEmail());
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_NOTES, course.getNotes());

                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_TERM_START_DATE, termStartDate);
                allTermCoursesToEditCourse.putExtra(EXTRA_COURSE_TERM_END_DATE, termEndDate);

                startActivity(allTermCoursesToEditCourse);
            }
        });

        Toast.makeText(this, "Swipe Left To Delete Course", Toast.LENGTH_LONG).show();
    }

    public void openAddCourseActivity(View view){

        Intent allTermCoursesToAddCourse = new Intent(AllTermCoursesActivity.this, AddEditCourseActivity.class);
        allTermCoursesToAddCourse.putExtra(EXTRA_TERM_ID, termId);
        //Log.d("DEBUG", "OPENADDCOURSE METHOD TERMID IS: " + termId);
        allTermCoursesToAddCourse.putExtra(EXTRA_START_DATE, termStartDate);
        //Log.d("DEBUG", "OPENADDCOURSE METHOD termStartDate IS: " + termStartDate);
        allTermCoursesToAddCourse.putExtra(EXTRA_END_DATE, termEndDate);
        //Log.d("DEBUG", "OPENADDCOURSE METHOD termEndDate IS: " + termEndDate);
        startActivity(allTermCoursesToAddCourse);
    }
}
