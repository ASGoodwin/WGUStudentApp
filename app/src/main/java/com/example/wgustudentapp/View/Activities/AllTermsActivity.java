package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.wgustudentapp.Model.Entities.Course;
import com.example.wgustudentapp.Model.Entities.Term;
import com.example.wgustudentapp.R;
import com.example.wgustudentapp.View.Adapters.AllTermsAdapter;
import com.example.wgustudentapp.ViewModels.AllTermsViewModel;

import java.util.List;

public class AllTermsActivity extends AppCompatActivity {

    //Reference to view model
    private AllTermsViewModel allTermsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_terms);

        RecyclerView recyclerView = findViewById(R.id.rvTerms);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final AllTermsAdapter adapter = new AllTermsAdapter();
        recyclerView.setAdapter(adapter);

        allTermsViewModel = ViewModelProviders.of(this).get(AllTermsViewModel.class);

        allTermsViewModel.getAlTerms().observe(this, new Observer<List<Term>>(){

            @Override
            public void onChanged(List<Term> terms) {

                adapter.setTerm(terms);
            }
        });

        allTermsViewModel.getAlCourses().observe(this, new Observer<List<Course>>(){

            @Override
            public void onChanged(List<Course> courses) {

                adapter.setCourse(courses);
            }
        });

        adapter.setOnItemClickListener(new AllTermsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Term term) {

                Intent intent = new Intent(AllTermsActivity.this, SingleTermActivity.class);
                intent.putExtra(SingleTermActivity.EXTRA_TERM_ID, term.getId());
                intent.putExtra(SingleTermActivity.EXTRA_NAME, term.getName());
                intent.putExtra(SingleTermActivity.EXTRA_START_DATE, term.getStartDate());
                intent.putExtra(SingleTermActivity.EXTRA_END_DATE, term.getEndDate());
                startActivity(intent);
            }
        });
    }

    public void openAddTermActivity(View view){

        Intent intent = new Intent(AllTermsActivity.this, AddTermActivity.class);
        startActivity(intent);
    }
}
