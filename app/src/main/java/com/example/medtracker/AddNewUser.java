package com.example.medtracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

public class AddNewUser extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;
    private FirebaseAuth mAuth;
    DatePickerDialog picker;
    private FirebaseFirestore mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        Button signUpBtn = (Button) findViewById(R.id.login_button);

        final EditText editEmail = (EditText) findViewById(R.id.emailBox);
        final EditText editPass = (EditText) findViewById(R.id.passwordBox);
        final EditText editfName = (EditText) findViewById(R.id.FirstName);
        final EditText editlName = (EditText) findViewById(R.id.lastName);
        final EditText editBirthday = (EditText) findViewById(R.id.birthday);
        final EditText reenterPass = (EditText) findViewById(R.id.reenterPass);


        final TextView errorTxt = (TextView) findViewById(R.id.ErrorTxt);

        final Calendar cldr = Calendar.getInstance();
        final EditText datepicker = (EditText) findViewById(R.id.editDateText);

        datepicker.setInputType(InputType.TYPE_NULL);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddNewUser.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = (monthOfYear + 1) + "/" + dayOfMonth  +  "/" + year;
                                datepicker.setText(date);

                            }
                        }, year, month, day);
                picker.show();
            }
        });



        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String pass = editPass.getText().toString();
                String fName = editfName.getText().toString();
                String lName = editlName.getText().toString();
                String birthday = editBirthday.getText().toString();
                String rPass = reenterPass.getText().toString();
                if((email != null && !email.isEmpty()) || (pass != null && !pass.isEmpty()) || (fName != null && !fName.isEmpty()) || (lName != null && !lName.isEmpty())|| (birthday != null && !birthday.isEmpty()) || (rPass != null && !rPass.isEmpty())){
                    AddNewUser.user newUser = new AddNewUser.user(fName, lName, birthday);
                    createUser(email, pass, errorTxt, newUser);
                }
                else if(!rPass.equals(pass)){
                    errorTxt.setVisibility(View.VISIBLE);
                    String warning = "Passwords don't match";
                    errorTxt.setText(warning);
                }
                else{
                    errorTxt.setVisibility(View.VISIBLE);
                    String warning = "Please fill in all fields";
                    errorTxt.setText(warning);
                }
            }
        });

    }

    protected void createUser(String email, String password, final TextView errorTxt, final user newUser){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();
                            writeNewUser(newUser, uid);

                            //go to main menu
                            startActivity(new Intent(AddNewUser.this, MainMenu.class));
                            finish();
                        } else {
                            errorTxt.setVisibility(View.VISIBLE);
                            String warning = "Something went wrong...";
                            errorTxt.setText(warning);
                        }

                        // ...
                    }
                });
    }

    public class user {

        public String fName;
        public String lName;
        public String birthday;
        public String uid;


        public user() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public user(String fName, String lName, String birthday) {
            this.fName = fName;
            this.lName = lName;
            this.birthday = birthday;
            this.uid = "";
        }

        public void setUID(String uid) {
            this.uid = uid;
        }

    }

    private void writeNewUser(user User, String uid) {
        CollectionReference users = mDatabase.collection("user");
        User.setUID(uid);

        users.add(User);

    }

}
