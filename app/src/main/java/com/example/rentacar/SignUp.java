package com.example.rentacar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    //Variables
    TextInputLayout regName, regUsername, regEmail, regPhone, regPassword;
    Button regBtn, regToLoginBtn;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        regName = findViewById(R.id.fullname);
        regUsername = findViewById(R.id.username);
        regEmail = findViewById(R.id.email);
        regPhone = findViewById(R.id.phone_number);
        regPassword = findViewById(R.id.password);
        regBtn = findViewById(R.id.reg_btn);
        regToLoginBtn = findViewById(R.id.to_login_btn);

        rootNode = FirebaseDatabase.getInstance("https://rentacar-3a886-default-rtdb.asia-southeast1.firebasedatabase.app");
        reference = rootNode.getReference("users");

    }

    private Boolean validateName() {
        String val = regName.getEditText().getText().toString();

        if(val.isEmpty()) {
            regName.setError("Field cannot be empty");
            return false;
        } else {
            regName.setError(null);
            regName.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = regUsername.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if(val.isEmpty()) {
            regUsername.setError("Field cannot be empty");
            return false;
        } else if(val.length() >= 15) {
            regUsername.setError("Username is too long");
            return false;
        } else if(!val.matches(noWhiteSpace)) {
            regUsername.setError("No space allowed");
            return false;
        }
        else {
            regUsername.setError(null);
            regUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateEmail() {
        String val = regEmail.getEditText().getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(val.isEmpty()) {
            regEmail.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(emailPattern)) {
            regEmail.setError("Invalid email address!");
            return false;
        }
        else {
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNumber() {
        String val = regPhone.getEditText().getText().toString();

        if(val.isEmpty()) {
            regPhone.setError("Field cannot be empty");
            return false;
        } else {
            regPhone.setError(null);
            regPhone.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = regPassword.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +             //at least 1 digit
                //"(?=.*[a-z])" +             //at least 1 lower case character
                //"(?=.*[A-Z])" +             //at least 1 upper case character
                "(?=.*[a-zA-Z])" +          //any letter
                "(?=.*[@#$%^&+=])" +        //at least 1 special character
                //"(?=\\S+$])" +              //no white spaces
                ".{4,}" +                   // at least 4 characters
                "$";

        if(val.isEmpty()) {
            regPassword.setError("Field cannot be empty");
            return false;
        } else if(!val.matches(passwordVal)) {
            regPassword.setError("Password is too weak!");
            return false;
        }
        else {
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void registerUser(View view) {

        if(!validateName() || !validateUsername() || !validateEmail() || !validatePhoneNumber() || !validatePassword()) {
            return;
        }

        String name = regName.getEditText().getText().toString();
        String username = regUsername.getEditText().getText().toString();
        String email = regEmail.getEditText().getText().toString();
        String phoneNumber = regPhone.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();

        Intent intent = new Intent(getApplicationContext(), VerifyPhoneNo.class);
        intent.putExtra("phoneNumber", phoneNumber);
        startActivity(intent);

        //UserHelperClass helperClass = new UserHelperClass(name, username, email, phoneNumber, password);
        //reference.child(username).setValue(helperClass);


    }
}
