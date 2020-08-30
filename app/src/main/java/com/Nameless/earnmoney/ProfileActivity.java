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

public class ProfileActivity extends AppCompatActivity {

    TextView mfullname, musername, memail, mphone;
    TextView userPoints, userRupees;
    Button btnback;

    //Firebase
    DatabaseReference reference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String FirebaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mfullname = findViewById(R.id.Fullname);
        musername = findViewById(R.id.Username);
        memail = findViewById(R.id.Email);
        mphone = findViewById(R.id.Phone);

        userPoints = findViewById(R.id.userPoints);
        userRupees = findViewById(R.id.userRupees);
        btnback = findViewById(R.id.Back);

        reference = FirebaseDatabase.getInstance().getReference().child("user");
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        FirebaseId = user.getUid();

        reference.child(FirebaseId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                USERS userInformation = dataSnapshot.getValue(USERS.class);

                Log.d("onDataChange","onDataChange:"+userInformation.getFullName());
                Log.d("onDataChange","onDataChange:"+userInformation.getUsername());
                Log.d("onDataChange","onDataChange:"+userInformation.getPoints());
                Log.d("onDataChange","onDataChange:"+userInformation.getEmail());
                Log.d("onDataChange","onDataChange:"+userInformation.getPhone());
                Log.d("OnDataChange","onDataChange:"+userInformation.getRupees());

                mfullname.setText(String.valueOf(userInformation.getFullName()));
                musername.setText(String.valueOf(userInformation.getUsername()));
                userPoints.setText(String.valueOf(userInformation.getPoints()));
                memail.setText(String.valueOf(userInformation.getEmail()));
                mphone.setText(String.valueOf(userInformation.getPhone()));
                userRupees.setText(String.valueOf(userInformation.getRupees()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("onDataChange","onDataChange"+databaseError);
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this,MainMenuActivity.class));
                finish();
            }
        });

    }
}
