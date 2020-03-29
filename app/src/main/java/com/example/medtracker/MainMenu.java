package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class MainMenu extends AppCompatActivity  {
    private FirebaseFirestore steps_firebase;
    private Query m2Query;
    Date date;
    private static final String NUMBER = "number";
    private static final String DATE = "date";
    private static final String UID = "uid";
    String[] values;
    String[] days_array;
    Vector<String> list = new Vector<>();
    Vector<String> days = new Vector<>();
    String sdate;
    TextView txtSteps;
    TextView txtDate;
    String single_day;
    SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        date = new Date();
        sdate = new SimpleDateFormat("MM-dd-yyyy").format(date);
        txtDate = (TextView) findViewById(R.id.date_textview);
        txtDate.setText(sdate);


        steps_firebase = FirebaseFirestore.getInstance();
        m2Query = steps_firebase.collection("steps");
        txtSteps = (TextView) findViewById(R.id.steps_textview);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        getSteps();
        retrieveSteps();

        //buttons
        Button button = (Button) findViewById(R.id.to_medications_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicationsActivity.class);
                startActivity(intent);
            }
        });
        Button button2 = (Button) findViewById(R.id.add_med);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMedActivity.class);
                startActivity(intent);
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSymptomsActivity.class);
                startActivity(intent);
            }
        });
        Button menu_btn = (Button) findViewById(R.id.menu);
        final LinearLayout dropdown = (LinearLayout) findViewById(R.id.menu_dropdown);

        menu_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dropdown.getVisibility()==View.GONE){
                    dropdown.setVisibility(View.VISIBLE);
                }else{
                    dropdown.setVisibility(View.GONE);
                }

            }
        });

        Button menu_notes = (Button) findViewById(R.id.menu_add_med_btn);
        Button menu_sym = (Button) findViewById(R.id.menu_sym_btn);
        Button menu_med = (Button) findViewById(R.id.menu_med_btn);
        Button menu_steps = (Button) findViewById(R.id.menu_steps_btn);

        menu_med.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MedicationsActivity.class);
                startActivity(intent);
            }
        });
        menu_steps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                values = list.toArray(new String[list.size()]);
                days_array = days.toArray(new String[days.size()]);
                Intent intent = new Intent(getApplicationContext(), StepsActivity.class);

                intent.putExtra("days", days_array);
                intent.putExtra("list",values);

                startActivity(intent);
            }
        });
        menu_sym.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SymptomsActivity.class);
                startActivity(intent);
            }
        });
        menu_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent);
            }
        });
    }

    public class Step {

        public String number;
        public String date;
        public String uid;

        public Step() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Step(String number, String date, String uid) {
            this.number = number;
            this.date = date;
            this.uid = uid;
        }

    }
    private double magPrev = 0;
    private Integer stepCount = 0;
    protected void getSteps(){

        //start sensors
        SensorManager sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor sensor= sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        SensorEventListener stepDetector = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                if(event != null){
                    float x_accel = event.values[0];
                    float y_accel = event.values[1];
                    float z_accel = event.values[2];

                    double magnitude = Math.sqrt(x_accel*x_accel + y_accel*y_accel + z_accel*z_accel);
                    double magnitudeDelta = magnitude - magPrev;
                    magPrev = magnitude;

                    if(magnitudeDelta > 6){
                        stepCount++;
                    }
                    //set textview here to update
                    txtSteps.setText(stepCount.toString() + " steps");
                    //send steps to database
                    CollectionReference step_db = steps_firebase.collection("steps");
                    Step newStep = new Step(Integer.toString(stepCount), sdate, "0");
                    step_db.document(Integer.toString(0)).set(newStep);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    protected void retrieveSteps(){

        m2Query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot doc : task.getResult()) {
                        if(doc.getString(UID).equals("0")){ //if it's the desired uid
                            //temporarily looking through uid 0
                            list.add(doc.getString(NUMBER));
                            single_day = Character.toString(doc.getString(DATE).charAt(3)) + Character.toString(doc.getString(DATE).charAt(4));
                            days.add(single_day);

                        }
                    }
                }
            }
        });

    }
    @Override
    public void onResume() {
        super.onResume();
        txtSteps.setText(stepCount.toString());
    }



}
