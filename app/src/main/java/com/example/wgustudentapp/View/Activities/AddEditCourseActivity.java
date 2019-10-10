package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wgustudentapp.Classes.AlertReceiver;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermCoursesAdapter;
import com.example.wgustudentapp.ViewModels.AllTermCoursesViewModel;
import com.example.wgustudentapp.Classes.Mentors;

import java.util.Calendar;
import java.util.List;

import static com.example.wgustudentapp.View.Activities.EmailNotesActivity.EXTRA_EMAIL_NOTES;

public class AddEditCourseActivity extends AppCompatActivity {

    //Properties
    //ViewModel
    private AllTermCoursesViewModel allTermCoursesViewModel;
    final AllTermCoursesAdapter adapter = new AllTermCoursesAdapter();

    //Extras imported from AllTermsActivity --> SingleTermActivity --> AllTermCoursesActivity --> AddEditCourseActivity
    public static final String EXTRA_TERM_ID = "com.example.WGUStudentApp.EXTRA_TERM_ID";
    public static final String EXTRA_START_DATE = "com.example.WGUStudentApp.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.WGUStudentApp.EXTRA_END_DATE";

    //Extras imported from AllTermCoursesActivity --> AddEditCourseActivity
    public static final String EXTRA_COURSE_ID = "com.example.WGUStudentApp.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_TERM_ID = "com.example.WGUStudentApp.EXTRA_COURSE_TERM_ID";
    public static final String EXTRA_COURSE_NAME = "com.example.WGUStudentApp.EXTRA_COURSE_NAME";
    public static final String EXTRA_COURSE_STATUS = "com.example.WGUStudentApp.EXTRA_COURSE_STATUS";
    public static final String EXTRA_COURSE_START_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE ="com.example.WGUStudentApp.EXTRA_COURSE_END_DATE";
    public static final String EXTRA_COURSE_MENTOR_NAME ="com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_NAME";
    public static final String EXTRA_COURSE_MENTOR_PHONE ="com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_PHONE";
    public static final String EXTRA_COURSE_MENTOR_EMAIL ="com.example.WGUStudentApp.EXTRA_COURSE_MENTOR_EMAIL";
    public static final String EXTRA_COURSE_NOTES ="com.example.WGUStudentApp.EXTRA_COURSE_NOTES";
    public static final String EXTRA_COURSE_TERM_START_DATE ="com.example.WGUStudentApp.EXTRA_COURSE_TERM_START_DATE";
    public static final String EXTRA_COURSE_TERM_END_DATE ="com.example.WGUStudentApp.EXTRA_COURSE_TERM_END_DATE";

    //Controls that will change depending on if new or old course
    private EditText etCourseName;
    private NumberPicker npStatus;
    private DatePicker dpCourseStart;
    private DatePicker dpCourseEnd;
    private EditText etMentorName;
    private EditText etMentorPhone;
    private EditText etMentorEmail;
    private EditText etCourseNotes;
    private Button btnViewAssessments;
    private Button btnSaveCourse;

    //Calendars
    private Calendar calCourseStart = Calendar.getInstance();
    private Calendar calCourseEnd = Calendar.getInstance();

    //Array to hold status options
    private String[] arStatus;

    //Variables to hold extras imported from AllTermCoursesActivity
    private int termId;
    private long termStartDate;
    private long termEndDate;
    private int courseId;
    private String courseName;
    private int intCourseStatus;
    private String strCourseStatus;
    private long courseStart;
    private long courseEnd;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;
    private String courseNotes;

    //Request code
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_course);

        requestCode = 0;

        //Controls
        etCourseName = findViewById(R.id.etCourseName);
        npStatus = findViewById(R.id.npStatus);
        dpCourseStart = findViewById(R.id.dpCourseStart);
        dpCourseEnd = findViewById(R.id.dpCourseEnd);
        etMentorName = findViewById(R.id.etMentorName);
        etMentorPhone = findViewById(R.id.etMentorPhone);
        etMentorEmail = findViewById(R.id.etMentorEmail);
        etCourseNotes = findViewById(R.id.etCourseNotes);
        btnViewAssessments = findViewById(R.id.btnViewAssessments);
        btnSaveCourse = findViewById(R.id.btnSaveCourse);



        allTermCoursesViewModel = ViewModelProviders.of(this).get(AllTermCoursesViewModel.class);
        allTermCoursesViewModel.getAlCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                adapter.setCourse(courses);
            }
        });

        //Get intent from AllTermCoursesActivity
        Intent intent = getIntent();

        //Configure number picker
        arStatus = new String[]{"Plan To Take", "In Progress", "Completed", "Dropped"}; //Set options
        npStatus.setDisplayedValues(arStatus); //Set arStatus as list of options
        npStatus.setMinValue(0); //Start options at "Plan To Take"
        npStatus.setMaxValue(arStatus.length - 1); //Set max number to array.length -1
        npStatus.setWrapSelectorWheel(true); //Set to cycle through options at end

        if(intent.hasExtra(EXTRA_COURSE_ID)){ //if editing course

            termId = intent.getIntExtra(EXTRA_COURSE_TERM_ID, -1);
            termStartDate = intent.getLongExtra(EXTRA_COURSE_TERM_START_DATE, -1);
            termEndDate = intent.getLongExtra(EXTRA_COURSE_TERM_END_DATE, -1);
            courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);
            courseName = intent.getStringExtra(EXTRA_COURSE_NAME);
            strCourseStatus = intent.getStringExtra(EXTRA_COURSE_STATUS);
            courseStart = intent.getLongExtra(EXTRA_COURSE_START_DATE, -1);
            courseEnd = intent.getLongExtra(EXTRA_COURSE_END_DATE, -1);
            mentorName = intent.getStringExtra(EXTRA_COURSE_MENTOR_NAME);
            mentorPhone = intent.getStringExtra(EXTRA_COURSE_MENTOR_PHONE);
            mentorEmail = intent.getStringExtra(EXTRA_COURSE_MENTOR_EMAIL);
            courseNotes = intent.getStringExtra(EXTRA_COURSE_NOTES);

            btnViewAssessments.setVisibility(View.VISIBLE);

            etCourseName.setText(courseName); //Set title
            switch(strCourseStatus){ //Set status on number picker
                case "Plan To Take":
                    intCourseStatus = 0;
                    break;
                case "In Progress":
                    intCourseStatus = 1;
                    break;
                case "Completed":
                    intCourseStatus = 2;
                    break;
                case "Dropped":
                    intCourseStatus = 3;
            }
            npStatus.setValue(intCourseStatus);
            //Set dates on datepickers
            Calendar calStart = Calendar.getInstance();
            Calendar calEnd = Calendar.getInstance();
            calStart.setTimeInMillis(courseStart);
            dpCourseStart.updateDate(calStart.get(Calendar.YEAR), calStart.get(Calendar.MONTH), calStart.get(Calendar.DAY_OF_MONTH));
            calEnd.setTimeInMillis(courseEnd);
            dpCourseEnd.updateDate(calEnd.get(Calendar.YEAR), calEnd.get(Calendar.MONTH), calEnd.get(Calendar.DAY_OF_MONTH));
            //Set mentor information
            etMentorName.setText(mentorName);
            etMentorPhone.setText(mentorPhone);
            etMentorEmail.setText(mentorEmail);
            //Load notes
            etCourseNotes.setText(courseNotes);
            //Change button text
            btnSaveCourse.setText("Update");

        } else { //Set as new

            termId = intent.getIntExtra(EXTRA_TERM_ID, -1);
            termStartDate = intent.getLongExtra(EXTRA_START_DATE, -1);
            termEndDate = intent.getLongExtra(EXTRA_END_DATE, -1);

            npStatus.setValue(0); //Set picker at "Plan To Take"


        }

        //Setting regardless of add or edit
        //Configure number picker
        arStatus = new String[]{"Plan To Take", "In Progress", "Completed", "Dropped"}; //set options
        npStatus.setDisplayedValues(arStatus); //Set arStatus as list of options
        npStatus.setMinValue(0); //Start options at "Plan To Take"
        npStatus.setMaxValue(arStatus.length - 1); //Set max number to array -1
        npStatus.setWrapSelectorWheel(true); //set to cycle through options at end

        //Set min and max course dates based on term dates
        dpCourseStart.setMinDate(termStartDate);
        dpCourseStart.setMaxDate(termEndDate);
        dpCourseEnd.setMinDate(termStartDate);
        dpCourseEnd.setMaxDate(termEndDate);

        if(savedInstanceState != null){
            npStatus.setValue(savedInstanceState.getInt("status"));
        }
    }

    public Calendar setCourseDates(Calendar c, int year, int monthOfYear, int dayOfMonth){
        c.set(year, monthOfYear, dayOfMonth);
        return c;
    }

    public void saveCourse(View view){

        courseName = etCourseName.getText().toString();
        if(courseName.trim().isEmpty()){
            Toast.makeText(this, "Please Enter Course Title", Toast.LENGTH_SHORT);
        } else {
            intCourseStatus = npStatus.getValue();
            switch (intCourseStatus) {
                case 0:
                    strCourseStatus = "Plan To Take";
                    break;
                case 1:
                    strCourseStatus = "In Progress";
                    break;
                case 2:
                    strCourseStatus = "Completed";
                    break;
                case 3:
                    strCourseStatus = "Dropped";
                    break;
            }
            courseStart = setCourseDates(calCourseStart, dpCourseStart.getYear(), dpCourseStart.getMonth(), dpCourseStart.getDayOfMonth()).getTimeInMillis();
            courseEnd = setCourseDates(calCourseEnd, dpCourseEnd.getYear(), dpCourseEnd.getMonth(), dpCourseEnd.getDayOfMonth()).getTimeInMillis();

            if(etMentorName.getText().toString().trim().isEmpty()) {
                Mentors mentors = new Mentors();
                mentorName = mentors.getMentorName();
                mentorPhone = mentors.getMentorPhone();
                mentorEmail = mentors.getMentorEmail();
            } else {
                mentorName = etMentorName.getText().toString().trim();
                mentorPhone = etMentorPhone.getText().toString().trim();
                mentorEmail = etMentorEmail.getText().toString().trim();
            }

            courseNotes = etCourseNotes.getText().toString().trim();

            Course course = new Course(termId, courseName, courseStart, courseEnd, strCourseStatus, mentorName, mentorPhone, mentorEmail, courseNotes);

            Intent intent = getIntent();
            if(intent.hasExtra(EXTRA_COURSE_ID)){
                course.setId(courseId);
                allTermCoursesViewModel.updateCourse(course);
            } else {
                allTermCoursesViewModel.insertCourse(course);
            }
            finish();
        }
    }

    public void addCourseAlerts(View view){

        startAlarm(setCourseDates(calCourseStart, dpCourseStart.getYear(), dpCourseStart.getMonth(), dpCourseStart.getDayOfMonth()));
        startAlarm(setCourseDates(calCourseEnd, dpCourseEnd.getYear(), dpCourseEnd.getMonth(), dpCourseEnd.getDayOfMonth()));
    }

    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        requestCode++;

        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    public void openAllCourseAssessments(View view){

        Intent addEditCourseToAllCourseAssessments = new Intent(AddEditCourseActivity.this, AllCourseAssessmentsActivity.class);

        addEditCourseToAllCourseAssessments.putExtra(EXTRA_COURSE_ID, courseId);
        addEditCourseToAllCourseAssessments.putExtra(EXTRA_COURSE_START_DATE, courseStart);
        addEditCourseToAllCourseAssessments.putExtra(EXTRA_COURSE_END_DATE, courseEnd);
        startActivity(addEditCourseToAllCourseAssessments);
    }

    public void emailNotes(View view){

        courseNotes = etCourseNotes.getText().toString().trim();

        Intent addEditCourseActivityToEmailNotes = new Intent(AddEditCourseActivity.this, EmailNotesActivity.class);

        addEditCourseActivityToEmailNotes.putExtra(EXTRA_EMAIL_NOTES, courseNotes);

        startActivity(addEditCourseActivityToEmailNotes);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("status", npStatus.getValue());
    }

}
