package com.Nameless.earnmoney;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.Nameless.earnmoney.Model.USERS;
import com.example.earnmoney.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, usernameEditText, passwordEditText;
    private EditText phoneEditText, emailEditText;
    private TextView Gologin;
    private ImageView picImageView;
    private CheckBox maleCheckBox, femaleCheckBox;
    private Button registerButton;
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
    private static final String USERS = "users";
    private String TAG = "RegisterActivity";
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameEditText = findViewById(R.id.fullname_edittext);
        usernameEditText = findViewById(R.id.Username_edittext);
        phoneEditText = findViewById(R.id.phone_edittext);
        passwordEditText = findViewById(R.id.enterpass_edittext);
        emailEditText = findViewById(R.id.email_edittext);
        picImageView = findViewById(R.id.pic_imageview);
        Gologin = findViewById(R.id.GoLogin_textview);
        maleCheckBox = findViewById(R.id.male_checkbox);
        femaleCheckBox = findViewById(R.id.female_checkbox);
        registerButton = findViewById(R.id.register_button);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference(USERS);
        mAuth = FirebaseAuth.getInstance();


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

    }

    private void registerUser() {
        final String name = nameEditText.getText().toString();
        final String username = usernameEditText.getText().toString();
        final String email = emailEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final String phone = phoneEditText.getText().toString();

        if ((TextUtils.isEmpty(name)) && (TextUtils.isEmpty(username)) && (TextUtils.isEmpty(email)) && (TextUtils.isEmpty(password)) && (TextUtils.isEmpty(phone))) {
            Toast.makeText(this,"Fill Out All The Fields.", Toast.LENGTH_LONG).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    USERS userInformation = new USERS();
                    userInformation.setEmail(email);
                    userInformation.setPassword(password);
                    userInformation.setFullName(nameEditText.getText().toString());
                    userInformation.setUsername(usernameEditText.getText().toString());
                    userInformation.setPhone(phoneEditText.getText().toString());
                    userInformation.setPoints(0);
                    userInformation.setRupees(0);

                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this, "Error!" +e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /**
     * adding user information to database and redirect to login screen
     * @param user
     */
    public void updateUI(FirebaseUser user) {
        String keyid = mDatabase.push().getKey();
        mDatabase.child(keyid).setValue(user); //adding user info to database
        Intent loginIntent = new Intent(this, MainActivity.class);
        startActivity(loginIntent);
    }
}