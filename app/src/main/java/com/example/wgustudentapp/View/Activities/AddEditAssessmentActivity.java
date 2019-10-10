package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.example.wgustudentapp.Classes.AlertReceiver;
import com.example.wgustudentapp.Model.Entities.Assessment;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllCourseAssessmentsAdapter;
import com.example.wgustudentapp.ViewModels.AllCourseAssessmentsViewModel;

import java.util.Calendar;
import java.util.List;

import static com.example.wgustudentapp.View.Activities.EmailNotesActivity.EXTRA_EMAIL_NOTES;

public class AddEditAssessmentActivity extends AppCompatActivity {

    private AllCourseAssessmentsViewModel allCourseAssessmentsViewModel;
    final AllCourseAssessmentsAdapter adapter = new AllCourseAssessmentsAdapter();

    //Extras from AddEditCourseActivity --> AllCourseAssessmentsActivity --> AddEditAssessmentsActivity
    public static final String EXTRA_COURSE_ID = "com.example.WGUStudentApp.EXTRA_COURSE_ID";
    public static final String EXTRA_COURSE_START_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_START_DATE";
    public static final String EXTRA_COURSE_END_DATE = "com.example.WGUStudentApp.EXTRA_COURSE_END_DATE";

    //Extras from AllCourseAssessmentsActivity
    public static final String EXTRA_ASSESSMENT_ID = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_ID";
    public static final String EXTRA_ASSESSMENT_NAME = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_NAME";
    public static final String EXTRA_ASSESSMENT_TYPE = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_TYPE";
    public static final String EXTRA_ASSESSMENT_START = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_START";
    public static final String EXTRA_ASSESSMENT_DUE_DATE = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_DUE_DATE";
    public static final String EXTRA_ASSESSMENT_NOTES = "com.example.WGUStudentApp.EXTRA_ASSESSMENT_NOTES";

    //Controls that will change depending on if new or old assessment
    private EditText etAssessmentName;
    private NumberPicker npType;
    private DatePicker dpAssessmentStartDate;
    private DatePicker dpDueDate;
    private EditText etAssessmentNotes;
    private Button btnSaveAssessment;

    //Calendars
    private Calendar calStartDate = Calendar.getInstance();
    private Calendar calDueDate = Calendar.getInstance();

    //Array to hold types
    private String[] arType;

    //Variables to hold extras
    private int courseId;
    private long courseStart;
    private long courseEnd;
    private int assessmentId;
    private String assessmentName;
    private String assessmentType;
    private long assessmentStart;
    private long assessmentDueDate;
    private String assessmentNotes;

    //Request code
    int requestCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_assessment);

        requestCode = 1000;


        //Controls
        etAssessmentName = findViewById(R.id.etAssessmentName);
        npType = findViewById(R.id.npType);
        dpAssessmentStartDate = findViewById(R.id.dpAssessmentStartDate);
        dpDueDate = findViewById(R.id.dpDueDate);
        etAssessmentNotes = findViewById(R.id.etAssessmentNotes);
        btnSaveAssessment = findViewById(R.id.btnSaveAssessment);

        Intent intent = getIntent();

        courseId = intent.getIntExtra(EXTRA_COURSE_ID, -1);
        courseStart = intent.getLongExtra(EXTRA_COURSE_START_DATE, -1);
        courseEnd = intent.getLongExtra(EXTRA_COURSE_END_DATE, -1);

        allCourseAssessmentsViewModel = ViewModelProviders.of(this).get(AllCourseAssessmentsViewModel.class);
        allCourseAssessmentsViewModel.getAlAssessments().observe(this, new Observer<List<Assessment>>() {
            @Override
            public void onChanged(List<Assessment> assessments) {
                adapter.setAssessment(assessments);
            }
        });

        //Configure number picker
        arType = new String[]{"Objective", "Performance"}; //Set options
        npType.setDisplayedValues(arType); //Set arType as list of options
        npType.setMinValue(0); //Start options with "objective"
        npType.setMaxValue(arType.length-1); //Set max number to array.length -1
        npType.setWrapSelectorWheel(true); //Set to cycle through options at end

        //Set min and max options so assessment are not scheduled outside of course dates
        dpAssessmentStartDate.setMinDate(courseStart);
        dpAssessmentStartDate.setMaxDate(courseEnd);
        dpDueDate.setMinDate(courseStart);
        dpDueDate.setMaxDate(courseEnd);

        if(intent.hasExtra(EXTRA_ASSESSMENT_ID)){ //Edit Assessment Code

            //Get extras
            assessmentId = intent.getIntExtra(EXTRA_ASSESSMENT_ID, -1);
            assessmentName = intent.getStringExtra(EXTRA_ASSESSMENT_NAME);
            assessmentType = intent.getStringExtra(EXTRA_ASSESSMENT_TYPE);
            assessmentStart = intent.getLongExtra(EXTRA_ASSESSMENT_START, -1);
            assessmentDueDate = intent.getLongExtra(EXTRA_ASSESSMENT_DUE_DATE, -1);
            assessmentNotes = intent.getStringExtra(EXTRA_ASSESSMENT_NOTES);

            //Set extras
            etAssessmentName.setText(assessmentName);
            //Set status on numberpicker
            if(assessmentType.equals("Objective")){
                npType.setValue(0);
            } else {
                npType.setValue(1);
            }
            //Set date on datepicker
            calStartDate.setTimeInMillis(assessmentStart);
            calDueDate.setTimeInMillis(assessmentDueDate);
            dpAssessmentStartDate.updateDate(calStartDate.get(Calendar.YEAR), calStartDate.get(Calendar.MONTH), calStartDate.get(Calendar.DAY_OF_MONTH));
            dpDueDate.updateDate(calDueDate.get(Calendar.YEAR), calDueDate.get(Calendar.MONTH), calDueDate.get(Calendar.DAY_OF_MONTH));
            //Set assessment notes
            etAssessmentNotes.setText(assessmentNotes);
            btnSaveAssessment.setText("Update");

        } else { //Code for new assessment activity

            npType.setValue(0); //Set number picker at objective
            //Set due date as last day of course
            calStartDate.setTimeInMillis(courseStart);
            calDueDate.setTimeInMillis(courseEnd);
            dpAssessmentStartDate.updateDate(calStartDate.get(Calendar.YEAR), calStartDate.get(Calendar.MONTH), calStartDate.get(Calendar.DAY_OF_MONTH));
            dpDueDate.updateDate(calDueDate.get(Calendar.YEAR), calDueDate.get(Calendar.MONTH), calDueDate.get(Calendar.DAY_OF_MONTH));
        }

        if(savedInstanceState != null){
            npType.setValue(savedInstanceState.getInt("type"));
        }
    }

    public Calendar setDueDate(Calendar c, int year, int monthOfYear, int dayOfMonth){
        c.set(year, monthOfYear, dayOfMonth);
        return c;
    }

    public void saveAssessment(View view){

        //Get assessment name
        assessmentName = etAssessmentName.getText().toString().trim();

        //Get assessment type
        if(npType.getValue() == 0){
            assessmentType = "Objective";
        } else {
            assessmentType = "Performance";
        }
        //If no assessment name, set assessment name
        if(assessmentName.trim().isEmpty()){
            assessmentName = "" + assessmentType + " Assessment";
        }
        //Get start and due dates
        assessmentStart = setDueDate(calStartDate, dpAssessmentStartDate.getYear(), dpAssessmentStartDate.getMonth(), dpAssessmentStartDate.getDayOfMonth()).getTimeInMillis();
        assessmentDueDate = setDueDate(calDueDate, dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth()).getTimeInMillis();
        //Get notes
        assessmentNotes = etAssessmentNotes.getText().toString().trim();

        Assessment assessment = new Assessment(courseId, assessmentType, assessmentName, assessmentStart, assessmentDueDate, assessmentNotes);

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_ASSESSMENT_ID)){
            assessment.setId(assessmentId);
            allCourseAssessmentsViewModel.updateAssessment(assessment);
        } else {
            allCourseAssessmentsViewModel.insertAssessment(assessment);
        }
        finish();
    }

    public void emailNotes(View view){

        assessmentNotes = etAssessmentNotes.getText().toString().trim();

        Intent addEditAssessmentActivityToEmailNotes = new Intent(AddEditAssessmentActivity.this, EmailNotesActivity.class);

        addEditAssessmentActivityToEmailNotes.putExtra(EXTRA_EMAIL_NOTES, assessmentNotes);

        startActivity(addEditAssessmentActivityToEmailNotes);
    }

    public void addAssessmentAlerts(View view){

        startAlarm(setDueDate(calStartDate, dpAssessmentStartDate.getYear(), dpAssessmentStartDate.getMonth(), dpAssessmentStartDate.getDayOfMonth()));
        startAlarm(setDueDate(calDueDate, dpDueDate.getYear(), dpDueDate.getMonth(), dpDueDate.getDayOfMonth()));
    }

    public void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, 0);

        requestCode++;

        alarmManager.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("type", npType.getValue());
    }
}
