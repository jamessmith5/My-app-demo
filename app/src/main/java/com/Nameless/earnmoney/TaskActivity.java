package com.Nameless.earnmoney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Nameless.earnmoney.Model.USERS;
import com.example.earnmoney.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class TaskActivity extends AppCompatActivity {

    Button btnWatchads, goback;
    InterstitialAd mInterstitialAd;

    int currentPoints, previousPoints;
    int currentRupees, previousRupees;

    String email,name,phone,password,username;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference ref, userInfoDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String firebaseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        btnWatchads = findViewById(R.id.btnWatchAds);
        goback = findViewById(R.id.Back);

        settingUptheFirebase();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showingDatafromdatabase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(TaskActivity.this,"Error!",Toast.LENGTH_SHORT).show();
            }
        });

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9369903313997105/6827793962");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                Toast.makeText(TaskActivity.this,"You Got 1 Points", Toast.LENGTH_SHORT).show();
                currentPoints += 1;
                if (currentPoints >= 50){
                    currentRupees += 10;
                }
            }
        });

        btnWatchads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }
            }
        });

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savingthenewdatainfirebase();
                startActivity(new Intent(TaskActivity.this,MainMenuActivity.class));
                finish();
            }
        });
    }

    private void savingthenewdatainfirebase() {
        previousRupees += currentRupees;
        USERS information = new USERS(name,username,email,phone,password,previousPoints,previousRupees);
        userInfoDatabase.child(firebaseId).setValue(information);
    }

    private void showingDatafromdatabase(DataSnapshot ds) {

        for (DataSnapshot dataSnapshot: ds.getChildren()){
            USERS userInformation = new USERS();
            userInformation.setFullName(dataSnapshot.child(firebaseId).getValue(USERS.class).getFullName());
            userInformation.setUsername(dataSnapshot.child(firebaseId).getValue(USERS.class).getUsername());
            userInformation.setEmail(dataSnapshot.child(firebaseId).getValue(USERS.class).getEmail());
            userInformation.setPhone(dataSnapshot.child(firebaseId).getValue(USERS.class).getPhone());
            userInformation.setRupees(dataSnapshot.child(firebaseId).getValue(USERS.class).getRupees());
            userInformation.setPoints(dataSnapshot.child(firebaseId).getValue(USERS.class).getPoints());
            userInformation.setPassword(dataSnapshot.child(firebaseId).getValue(USERS.class).getPassword());

            name = userInformation.getFullName();
            username = userInformation.getUsername();
            email = userInformation.getEmail();
            phone = userInformation.getPhone();
            previousPoints = userInformation.getPoints();
            previousRupees = userInformation.getRupees();
            password = userInformation.getPassword();
        }
    }

    private void settingUptheFirebase() {
        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        userInfoDatabase = database.getReference("user");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseId = currentUser.getUid();

    }
}

