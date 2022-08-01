package com.example.rentacar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp;
    TextInputLayout loginUsername, loginPassword;

    Animation topAnim, bottomAnim;
    View image, logo, slogan, username, password, btngo, btnsignin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        callSignUp = findViewById(R.id.signup_screen);
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        loginUsername = findViewById(R.id.username);
        loginPassword = findViewById(R.id.password);

        image = findViewById(R.id.logo_image);
        logo = findViewById(R.id.logo_name);
        slogan = findViewById(R.id.slogan_name);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        btngo = findViewById(R.id.btnGO);
        btnsignin = findViewById(R.id.signup_screen);


        callSignUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(Login.this, SignUp.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(image, "logo_image");
                pairs[1] = new Pair<View, String>(logo, "logo_text");
                pairs[2] = new Pair<View, String>(slogan, "logo_desc");
                pairs[3] = new Pair<View, String>(username, "username_tran");
                pairs[4] = new Pair<View, String>(password, "pass_tran");
                pairs[5] = new Pair<View, String>(btngo, "buttonngo_tran");
                pairs[6] = new Pair<View, String>(btnsignin, "signin_tran");

                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Login.this,pairs);
                startActivity(intent, options.toBundle());
            }
        });


    }

    private Boolean validateUsername() {
        String val = loginUsername.getEditText().getText().toString();

        if(val.isEmpty()) {
            loginUsername.setError("Field cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            loginUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = loginPassword.getEditText().getText().toString();

        if(val.isEmpty()) {
            loginPassword.setError("Field cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            loginPassword.setErrorEnabled(false);
            return true;
        }
    }


    public void loginUser(View view) {
        if(!validateUsername() | !validatePassword()) {
            return;
        }
        else {
            isUser();
        }
    }

    private void isUser() {
        String enteredUsername = loginUsername.getEditText().getText().toString().trim();
        String enteredPassword = loginPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://rentacar-3a886-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");
        Query checkUser = reference.orderByChild("username").equalTo(enteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    loginUsername.setError(null);
                    loginUsername.setErrorEnabled(false);

                    String passwordFromDB = dataSnapshot.child(enteredUsername).child("password").getValue(String.class);
                    if(passwordFromDB.equals(enteredPassword)) {

                        loginUsername.setError(null);
                        loginUsername.setErrorEnabled(false);

                        String nameFromDB = dataSnapshot.child(enteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = dataSnapshot.child(enteredUsername).child("username").getValue(String.class);
                        String emailFromDB = dataSnapshot.child(enteredUsername).child("email").getValue(String.class);
                        String phoneNumberFromDB = dataSnapshot.child(enteredUsername).child("phoneNumber").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);
                        intent.putExtra("fullname", nameFromDB);
                        intent.putExtra("username", usernameFromDB);
                        intent.putExtra("email", emailFromDB);
                        intent.putExtra("phoneNumber", phoneNumberFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }
                    else {
                        loginPassword.setError("Wrong Password");
                        loginPassword.requestFocus();
                    }
                }
                else {
                    loginUsername.setError("Username doesn't exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}