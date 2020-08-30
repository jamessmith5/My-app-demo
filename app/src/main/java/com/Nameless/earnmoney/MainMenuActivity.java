package com.Nameless.earnmoney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Nameless.earnmoney.Model.USERS;
import com.example.earnmoney.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuActivity extends AppCompatActivity {

    Button mProfile;
    Button mTask;
    Button mWithdraw;
    Button mContactUs;
    Button mAbout;

    TextView userPoints, userRupees;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String FirebaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        mProfile = findViewById(R.id.btnProfile);
        mTask = findViewById(R.id.btnTask);
        mContactUs = findViewById(R.id.btnContactUs);
        mAbout = findViewById(R.id.btnAbout);

        userPoints = findViewById(R.id.userPoints);
        userRupees = findViewById(R.id.userRupees);

        reference = FirebaseDatabase.getInstance().getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseId = user.getUid();

        reference.child(FirebaseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                USERS userInformation = dataSnapshot.getValue(USERS.class);

                Log.d("onDataChange","onDataChange:"+userInformation.getPoints());
                Log.d("onDataChange","onDataChange:"+userInformation.getRupees());

                userPoints.setText(String.valueOf(userInformation.getPoints()));
                userRupees.setText(String.valueOf(userInformation.getRupees()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onDataChange","onDataChange"+databaseError);
            }
        });

        mProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, ProfileActivity.class));
                finish();
            }
        });

        mTask = findViewById(R.id.btnTask);
        mTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, TaskActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mWithdraw = findViewById(R.id.btnWithdraw);
        mWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuActivity.this, Withdrawn.class));
                finish();
            }
        });

        mContactUs = findViewById(R.id.btnContactUs);
        mContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, ContactUsActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mAbout = findViewById(R.id.btnAbout);
        mAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, AboutActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    public void Logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainMenuActivity.this,MainActivity.class));
        finish();
    }
}
