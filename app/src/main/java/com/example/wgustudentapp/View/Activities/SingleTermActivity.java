package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgustudentapp.Classes.Validation;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.Model.Repositories.CourseRepository;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermCoursesAdapter;
import com.example.wgustudentapp.View.Adapters.AllTermsAdapter;
import com.example.wgustudentapp.ViewModels.AllTermCoursesViewModel;
import com.example.wgustudentapp.ViewModels.AllTermsViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class SingleTermActivity extends AppCompatActivity {

    private AllTermCoursesViewModel allTermCoursesViewModel;
    private AllTermsViewModel allTermsViewModel;

    //Constant to receive termId
    public static final String EXTRA_TERM_ID = "com.example.WGUStudentApp.EXTRA_TERM_ID";
    public static final String EXTRA_NAME = "com.example.WGUStudentApp.EXTRA_NAME";
    public static final String EXTRA_START_DATE = "com.example.WGUStudentApp.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.WGUStudentApp.EXTRA_END_DATE";

    //Calendars
    private Calendar calTermStart = Calendar.getInstance();
    private Calendar calTermEnd = Calendar.getInstance();

    //Date formatter
    private DateFormat fmtDate = DateFormat.getDateInstance();

    //TextViews
    private TextView tvName;
    private TextView tvStart;
    private TextView tvEnd;

    //Strings for TextViews
    private String termName;
    private String termStart;
    private String termEnd;

    //int for termId
    private int termId;

    //long for dates
    private long longStartDate;
    private long longEndDate;

    //courseCount get course size
    int courseCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_term);

        //get intent from AllTermsActivity
        Intent intent = getIntent();

        //Get termId from intent extra
        termId = intent.getIntExtra(EXTRA_TERM_ID, -1);

        final AllTermsAdapter atAdapter = new AllTermsAdapter();

        allTermsViewModel = ViewModelProviders.of(this).get(AllTermsViewModel.class);
        allTermsViewModel.getAlTerms().observe(this, new Observer<List<Term>>() {
            @Override
            public void onChanged(List<Term> terms) {
                atAdapter.setTerm(terms);
            }
        });

        final AllTermCoursesAdapter atcAdapter = new AllTermCoursesAdapter();
        atcAdapter.setTermId(termId);

        allTermCoursesViewModel = ViewModelProviders.of(this).get(AllTermCoursesViewModel.class);
        allTermCoursesViewModel.getAlCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                atcAdapter.setCourse(courses);
                courseCount = atcAdapter.getItemCount();
            }
        });

        //Get long values for dates from intent Extras
        longStartDate = intent.getLongExtra(EXTRA_START_DATE, -1);
        //Log.d("SingleTermActivity", "Start date from EXTRA is: " + intent.getLongExtra(EXTRA_START_DATE, -1));
        //Log.d("SingleTermActivity", "Start date from longStartDate is: " + longStartDate);
        longEndDate = intent.getLongExtra(EXTRA_END_DATE, -1);
        //Log.d("DEBUG", "SINGLE TERM ACTIVITY LINE 64 LONGENDDATE IS: " + longEndDate);

        //Get start and end dates from intent extras, parse to long, convert to calendar
        calTermStart.setTimeInMillis(longStartDate);
        calTermEnd.setTimeInMillis(longEndDate);

        //Get term name as string from intent extra
        termName = intent.getStringExtra(EXTRA_NAME);
        //Format calendars to string
        termStart = fmtDate.format(calTermStart.getTime());
        termEnd = fmtDate.format(calTermEnd.getTime());

        //Initialize controls
        tvName = findViewById(R.id.tvCourseName);
        tvStart = findViewById(R.id.tvStart);
        tvEnd = findViewById(R.id.tvEnd);

        //Set controls to display strings
        tvName.setText(termName);
        tvStart.setText(termStart);
        tvEnd.setText(termEnd);

    }

    public void openAllTermCourses(View view){

        Intent singleTermToAllTermCourses = new Intent(SingleTermActivity.this, AllTermCoursesActivity.class);
        singleTermToAllTermCourses.putExtra(EXTRA_TERM_ID, termId);
        //Log.d("DEBUG", "IN OPENALLTERMCOURSES METHOD TERMID IS: " + termId);
        singleTermToAllTermCourses.putExtra(EXTRA_START_DATE, longStartDate);
        //Log.d("DEBUG", "IN OPENALLTERMCOURSES METHOD LONGSTARTDATE IS: " + longStartDate);
        singleTermToAllTermCourses.putExtra(EXTRA_END_DATE, longEndDate);
        //Log.d("DEBUG", "IN OPENALLTERMCOURSES METHOD LONGENDDATE IS: " + longEndDate);
        startActivity(singleTermToAllTermCourses);
    }

    public void deleteTerm(View view){

        if(courseCount > 0){
            Toast.makeText(this, "Must Delete All Courses From Term Before Deleting Term", Toast.LENGTH_SHORT).show();
        } else {
            Term term = new Term();
            term.setId(termId);
            term.setName(termName);
            term.setStartDate(longStartDate);
            term.setEndDate(longEndDate);
            allTermsViewModel.deleteTerm(term);
            finish();
        }
    }

}
