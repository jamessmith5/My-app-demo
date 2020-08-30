package com.Nameless.earnmoney;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.earnmoney.R;

public class ContactUsActivity extends AppCompatActivity {

    EditText etEmail, etSubject , etFeedback;

    Button btnSubmit, btngo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        etEmail = findViewById(R.id.email);

        etSubject = findViewById(R.id.etSubject);

        etFeedback = findViewById(R.id.etFeedback);

        btnSubmit = findViewById(R.id.btnSubmit);

        btngo = findViewById(R.id.GOBACK);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ourMessageFeedback = etFeedback.getText().toString();
                String ourSubjectFeedback = etSubject.getText().toString();
                String ourEmail = etEmail.getText().toString();
                //to divide the email with comma
                //if you want to receive the feedback instantly
                //on your 2 email ids:)
                //so code this or else don't code :)
                String[] email_divided = ourEmail.split(",");
                //now send it :)
                Intent btnSubmit = new Intent(Intent.ACTION_SEND);
                btnSubmit.putExtra(Intent.EXTRA_EMAIL, email_divided);
                btnSubmit.putExtra(Intent.EXTRA_TEXT, ourMessageFeedback);
                btnSubmit.putExtra(Intent.EXTRA_SUBJECT, ourSubjectFeedback);
                btnSubmit.setType("message/rfc822");
                startActivity(btnSubmit);
            }
        });

        btngo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactUsActivity.this,MainMenuActivity.class));
                finish();
            }
        });

    }
}
