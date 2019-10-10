package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.wgustudentapp.Classes.Validation;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermsAdapter;
import com.example.wgustudentapp.ViewModels.AllTermsViewModel;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class AddTermActivity extends AppCompatActivity {

    //Properties
    //Term name
    private String strTermName;
    private String strStartDate;
    private String strEndDate;

    //Constants to pass info to AllTermsActivity
    public static final String EXTRA_TERM_NAME = "com.example.wgustudentapp.EXTRA_TERM_NAME";
    public static final String EXTRA_START_DATE = "com.example.wgustudentapp.EXTRA_START_DATE";
    public static final String EXTRA_END_DATE = "com.example.wgustudentapp.EXTRA_END_DATE";

    //EditText Box
    private EditText etTermName;

    //Calendars
    private Calendar calTermStart = Calendar.getInstance();
    private Calendar calTermEnd = Calendar.getInstance();

    //DatePickers
    DatePicker dpStart;
    DatePicker dpEnd;

    //Date Formatter
    private DateFormat fmtDate = DateFormat.getDateInstance();

    //ViewModel -- Not receiving any data from ViewModel, but using to have access to insert method
    private AllTermsViewModel allTermsViewModel;
    final AllTermsAdapter termsAdapter = new AllTermsAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_term);

        etTermName = findViewById(R.id.etTermName);
        dpStart = findViewById(R.id.dpTermStart);
        dpEnd = findViewById(R.id.dpTermEnd);

        allTermsViewModel = ViewModelProviders.of(this).get(AllTermsViewModel.class);

        allTermsViewModel.getAlTerms().observe(this, new Observer<List<Term>>(){

            @Override
            public void onChanged(List<Term> terms) {
                termsAdapter.setTerm(terms);
            }
        });
    }

    //Methods
    //Method to set Term Dates
    public Calendar setTermDates(Calendar c, int year, int monthOfYear, int dayOfMonth){
        //Log.d("DEBUG", "FROM METHOD RECEIVING " + year + ", " + monthOfYear + ", " + dayOfMonth);
        c.set(year, monthOfYear, dayOfMonth);
        //Log.d("DEBUG", "AFTER SET " + c.get(Calendar.YEAR) + ", " + c.get(Calendar.MONTH) + ", " + c.get(Calendar.DAY_OF_MONTH));
        return c;
    }

    //Method to save term
    public void saveTerm(View view){

        //Convert dates from datepicker to calendar
        setTermDates(calTermStart, dpStart.getYear(), dpStart.getMonth(), dpStart.getDayOfMonth());
        setTermDates(calTermEnd, dpEnd.getYear(), dpEnd.getMonth(), dpEnd.getDayOfMonth());

        strTermName = etTermName.getText().toString();

        //Format dates to string
        strStartDate = fmtDate.format(calTermStart.getTime());
        strEndDate = fmtDate.format(calTermEnd.getTime());
        if(strTermName.trim().isEmpty()){
            strTermName = "Term";
        }
        if(Validation.isConsecutive(calTermStart, calTermEnd) && Validation.noOverlap(calTermStart, calTermEnd, termsAdapter.getAlTerms())){
            Term term = new Term(strTermName, calTermStart, calTermEnd);
            Toast.makeText(getApplicationContext(), "Name: " + term.getName() + ", " + strStartDate + ", " + strEndDate, Toast.LENGTH_LONG).show();
            allTermsViewModel.insertTerm(term);

            //Creates intent and tags start name, start date, and end date to pass to AllTermsActivity
            Intent termInfo = new Intent();
            termInfo.putExtra(EXTRA_TERM_NAME, strTermName);
            termInfo.putExtra(EXTRA_START_DATE, strStartDate);
            termInfo.putExtra(EXTRA_END_DATE, strEndDate);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Term dates must be consecutive and may not overlap",Toast.LENGTH_SHORT).show();
        }
    }
}
