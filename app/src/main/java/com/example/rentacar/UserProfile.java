package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserProfile extends AppCompatActivity {

    TextInputLayout fullname, email, phoneNumber, password;
    TextView fullnameLabel, usernameLabel;

    String _USERNAME, _NAME, _EMAIL, _PHONENO, _PASSWORD;

    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        reference = FirebaseDatabase.getInstance("https://rentacar-3a886-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("users");


        fullname = findViewById(R.id.fullname_profile);
        email = findViewById(R.id.email_profile);
        phoneNumber = findViewById(R.id.phone_number_profile);
        password = findViewById(R.id.password_profile);
        fullnameLabel = findViewById(R.id.fullname);
        usernameLabel = findViewById(R.id.username);

        showAllUserData();

    }

    private void showAllUserData() {
        Intent intent = getIntent();
        _USERNAME = intent.getStringExtra("username");
        _NAME = intent.getStringExtra("fullname");
        _EMAIL = intent.getStringExtra("email");
        _PHONENO = intent.getStringExtra("phoneNumber");
        _PASSWORD = intent.getStringExtra("password");

        fullnameLabel.setText(_NAME);
        usernameLabel.setText(_USERNAME);
        email.getEditText().setText(_EMAIL);
        fullname.getEditText().setText(_NAME);
        phoneNumber.getEditText().setText(_PHONENO);
        password.getEditText().setText(_PASSWORD);
    }

    public void updateProfile(View view) {
        if (isNameChanged() || isPasswordChanged()) {
            Toast.makeText(this, "Data has been updated", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Data is same and cannot be updated", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNameChanged() {
        if (!_NAME.equals(fullname.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("name").setValue(fullname.getEditText().getText().toString());
            _USERNAME = fullname.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPasswordChanged() {
        if (!_PASSWORD.equals(password.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("password").setValue(password.getEditText().getText().toString());
            _PASSWORD = password.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isEmailChanged() {
        if (!_EMAIL.equals(email.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("email").setValue(email.getEditText().getText().toString());
            _EMAIL = email.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }

    private boolean isPhoneNumberChanged() {
        if (!_PHONENO.equals(phoneNumber.getEditText().getText().toString())) {
            reference.child(_USERNAME).child("phoneNumber").setValue(phoneNumber.getEditText().getText().toString());
            _PHONENO = phoneNumber.getEditText().getText().toString();
            return true;
        } else {
            return false;
        }
    }
}