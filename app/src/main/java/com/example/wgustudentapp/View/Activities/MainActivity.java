package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.wgustudentapp.Model.AppDatabase;
import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermsAdapter;
import com.example.wgustudentapp.ViewModels.AllTermsViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //Reference to view model
    private AllTermsViewModel allTermsViewModel;
    private long min;
    private long max;
    private long today;
    private TextView tvProgress;
    private ProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final AllTermsAdapter adapter = new AllTermsAdapter();

        allTermsViewModel = ViewModelProviders.of(this).get(AllTermsViewModel.class);

        allTermsViewModel.getAlTerms().observe(this, new Observer<List<Term>>(){

            @Override
            public void onChanged(List<Term> terms) {

                tvProgress = findViewById(R.id.tvProgress);

                adapter.setTerm(terms); //Set terms in arraylist
                ArrayList<Term> testArrayList = (ArrayList)adapter.getAlTerms(); //Create test arraylist
                if(testArrayList.size() > 0) {
                    Term lastTerm = testArrayList.get(adapter.getAlTerms().size() - 1); //Get the last term in arraylist
                    min = lastTerm.getStartDate(); //gets what is most likely to be the largest start date, but it doesn't matter.  Since it will be sorted it just has to have a starting number that is an actual start date
                    max = lastTerm.getEndDate(); //gets what is most likely to be the largest end date, but it doesn't matter. Could technically start with zero since it will be replaced by any larger number
                    for (Term t : testArrayList) {

                        //Sorts the start dates in arraylist to find lowest
                        if (t.getStartDate() < min) {
                            min = t.getStartDate();
                        }

                        //Sorts the start dates in arraylist to find highest
                        if (t.getEndDate() > max) {
                            max = t.getEndDate();
                        }
                    }

                    Calendar calendar = Calendar.getInstance();
                    today = calendar.getTimeInMillis();
                    today = today - min;
                    long range = max - min;
                    double dToday = (double) today;
                    double dRange = (double) range;
                    double dPercent = dToday / dRange;
                    dPercent = dPercent * 100;
                    int percent = (int) dPercent;

                    pbProgress = findViewById(R.id.pbProgress);
                    pbProgress.setVisibility(ProgressBar.VISIBLE);
                    pbProgress.setProgress(percent);

                    tvProgress.setText("You are " + percent + "% complete!");
                } else {
                    tvProgress.setText("Welcome! Save terms to see percent complete.");
                }
            }
        });
    }

    //Open next activity
    public void openAllTermsActivity(View view){
        startActivity(new Intent(MainActivity.this, AllTermsActivity.class));
    }
}
