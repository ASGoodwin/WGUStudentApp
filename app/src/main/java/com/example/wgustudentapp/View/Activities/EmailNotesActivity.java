package com.example.wgustudentapp.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.wgustudentapp.R;

public class EmailNotesActivity extends AppCompatActivity {

    //Extra for notes from AddEditCourseActivity or AddEditAssessmentActivity
    public static final String EXTRA_EMAIL_NOTES = "com.example.WGUStudent.EXTRA_EMAIL_NOTES";

    //Fields for controls
    private EditText etEmailTo;
    private EditText etEmailSubject;
    private EditText etEmailNotes;
    private Button btnSendEmail;

    //Fields for values
    private String emailTo;
    private String emailSubject;
    private String emailNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_notes);

        etEmailTo = findViewById(R.id.etEmailTo);
        etEmailSubject = findViewById(R.id.etEmailSubject);
        etEmailNotes = findViewById(R.id.etEmailNotes);
        btnSendEmail = findViewById(R.id.btnSendEmail);

        Intent intent = getIntent();

        emailNotes = intent.getStringExtra(EXTRA_EMAIL_NOTES);
        etEmailNotes.setText(emailNotes);

        btnSendEmail.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                sendEmail();
            }
        });
    }

    private void sendEmail(){
        emailTo = etEmailTo.getText().toString().trim();
        String[] emailList = emailTo.split(",");
        emailSubject = etEmailSubject.getText().toString().trim();
        emailNotes = etEmailNotes.getText().toString().trim();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, emailList);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailNotes);

        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an email client"));
        finish();
    }
}
