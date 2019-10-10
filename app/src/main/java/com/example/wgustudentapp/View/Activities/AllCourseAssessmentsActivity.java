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

import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllCourseAssessmentsAdapter;
import com.example.wgustudentapp.ViewModels.AllCourseAssessmentsViewModel;

import java.util.List;

import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_DUE_DATE;
import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_ID;
import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_NAME;
import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_NOTES;
import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_START;
import static com.example.wgustudentapp.View.Activities.AddEditAssessmentActivity.EXTRA_ASSESSMENT_TYPE;

public class AllCourseAssessmentsActivity extends AppCompatActivity {

    //Extras from AddEditCourseActivity
    public static final String EXTRA_COURSE_ID = "com.example.WGUStudentApp.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_START_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE ="com.example.WGUStudentApp.EXTRA_COURSE_END_DATE";

    private AllCourseAssessmentsViewModel allCourseAssessmentsViewModel;

    //Variables to hold extras
    int courseId;
    long courseStart;
    long courseEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_course_assessments);

        Intent intent = getIntent();
        courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);
        courseStart = intent.getLongExtra(EXTRA_COURSE_START_DATE, -1);
        courseEnd = intent.getLongExtra(EXTRA_COURSE_END_DATE, -1);

        RecyclerView recyclerView = findViewById(R.id.rvAssessments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AllCourseAssessmentsAdapter adapter = new AllCourseAssessmentsAdapter();
        adapter.setCourseId(courseId);
        recyclerView.setAdapter(adapter);

        allCourseAssessmentsViewModel = ViewModelProviders.of(this).get(AllCourseAssessmentsViewModel.class);
        allCourseAssessmentsViewModel.getAlAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessment(assessments);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                allCourseAssessmentsViewModel.deleteAssessment(adapter.getAssessmentAt(viewHolder.getAdapterPosition()));
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new AllCourseAssessmentsAdapter.OnItemClickListener(){
            @Override
            public void onItemClick(Assessment assessment) {
                Intent allCourseAssessmentsToEditAssessment = new Intent(AllCourseAssessmentsActivity.this, AddEditAssessmentActivity.class);

                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_ID, assessment.getId());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_COURSE_ID, assessment.getCourseId());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_COURSE_START_DATE, courseStart);
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_COURSE_END_DATE, courseEnd);
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_NAME, assessment.getTitle());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_TYPE, assessment.getType());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_START, assessment.getStartDate());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_DUE_DATE, assessment.getDueDate());
                allCourseAssessmentsToEditAssessment.putExtra(EXTRA_ASSESSMENT_NOTES, assessment.getNotes());

                startActivity(allCourseAssessmentsToEditAssessment);
            }
        });

        Toast.makeText(this, "Swipe Left To Delete Course", Toast.LENGTH_LONG).show();
    }

    public void openAddAssessmentActivity(View view){

        Intent allCourseAssessmentsToAddAssessment = new Intent(AllCourseAssessmentsActivity.this, AddEditAssessmentActivity.class);
        allCourseAssessmentsToAddAssessment.putExtra(EXTRA_COURSE_ID, courseId);
        allCourseAssessmentsToAddAssessment.putExtra(EXTRA_COURSE_START_DATE, courseStart);
        allCourseAssessmentsToAddAssessment.putExtra(EXTRA_COURSE_END_DATE, courseEnd);
        startActivity(allCourseAssessmentsToAddAssessment);
    }
}
