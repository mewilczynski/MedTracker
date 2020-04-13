package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.util.*;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        Button button = (Button) findViewById(R.id.login_button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
//                startActivity(intent);
//            }
//        });
//
//
//    }

    // Request code for sign in
    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        Button signInBtn = (Button) findViewById(R.id.login_button);
        Button signUpBtn = (Button) findViewById(R.id.signup_button);

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (auth.getCurrentUser() != null) {
            // Already signed in
            startActivity(new Intent(MainActivity.this, MainMenu.class));
            finish();
        } else {
            //Not signed in
        }

        final EditText editEmail = (EditText) findViewById(R.id.emailBox);
        final EditText editPass = (EditText) findViewById(R.id.passwordBox);

        final TextView errorTxt = (TextView) findViewById(R.id.ErrorTxt);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                if((email != null && !email.isEmpty()) || (pass != null && !pass.isEmpty())){
                    signIn(email, pass, errorTxt);
                }
                else{
                    errorTxt.setVisibility(View.VISIBLE);
                    String warning = "Please enter your email and password";
                    errorTxt.setText(warning);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewUser.class);
                startActivity(intent);
            }
        });
    }

    protected void signIn(String email, String password, final TextView errorTxt){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            //go to main menu
                            startActivity(new Intent(MainActivity.this, MainMenu.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            // ...
                            errorTxt.setVisibility(View.VISIBLE);
                            String warning = "The email or password is incorrect";
                            errorTxt.setText(warning);
                        }

                        // ...
                    }
                });

    }

}
