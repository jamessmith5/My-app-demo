package com.Nameless.earnmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Nameless.earnmoney.Model.RequestPayment;
import com.Nameless.earnmoney.Model.USERS;
import com.example.earnmoney.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.Date;

public class Withdrawn extends AppCompatActivity {

    // Variable
    EditText etEmaiL, etEsewa;
    Button btnwithdraw, btnback;

    //Firebase
    FirebaseDatabase database;
    DatabaseReference userInfoDatabase, requestDatabase;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    String firebaseId;

    String usernameGt;
    int balanceGt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl);

        etEmaiL = findViewById(R.id.EmaiL);
        etEsewa = findViewById(R.id.Esewa);
        btnwithdraw = findViewById(R.id.RequestPayment);
        btnback = findViewById(R.id.BacK);

        final String currentDateTime = DateFormat.getDateTimeInstance().format(new Date());

        database = FirebaseDatabase.getInstance();
        userInfoDatabase = database.getReference();
        requestDatabase = database.getReference("RequestPayment");
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userInfoDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showingDataFromFirebase(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Withdrawn.this,"Error!"+databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        btnwithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmaiL.getText().toString();
                final String esewa = etEsewa.getText().toString();

                if (balanceGt >= 50){
                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(esewa)) {
                        RequestPayment payment = new RequestPayment(usernameGt,email,esewa,currentDateTime,balanceGt);
                        requestDatabase.child(firebaseId).setValue(payment);
                        Toast.makeText(Withdrawn.this,"Request Payment Sent!",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Withdrawn.this,MainMenuActivity.class));
                        finish();
                    }
                    else{
                        Toast.makeText(Withdrawn.this,"Fill The Fields First",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Withdrawn.this,"Your Balance is Less than 50",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Withdrawn.this,MainMenuActivity.class));
                finish();
            }
        });

    }

    private void showingDataFromFirebase(DataSnapshot dataSnapshot) {

        for (DataSnapshot ds: dataSnapshot.getChildren()){
            USERS userInformation = new USERS();
            userInformation.setFullName(ds.child(currentUser.getUid()).getValue(USERS.class).getFullName());
            userInformation.setRupees(ds.child(currentUser.getUid()).getValue(USERS.class).getRupees());

            usernameGt = userInformation.getFullName();
            balanceGt = userInformation.getRupees();
        }
    }
}