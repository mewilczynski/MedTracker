package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainMenu extends AppCompatActivity  {
    private FirebaseFirestore steps_firebase;
    Date date;
    String sdate;
    TextView txtSteps;
    TextView txtDate;
    SensorManager sensorManager;
    public boolean running = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        date = new Date();
        sdate = new SimpleDateFormat("MM-dd-yyyy").format(date);
        txtDate = (TextView) findViewById(R.id.date_textview);
        txtDate.setText(sdate);


        steps_firebase = FirebaseFirestore.getInstance();
        txtSteps = (TextView) findViewById(R.id.steps_textview);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        getSteps();


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
                Intent intent = new Intent(getApplicationContext(), StepsActivity.class);

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

        public int number;
        public String date;
        public int uid;

        public Step() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Step(int number, String date, int uid) {
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
                    Step newStep = new Step(stepCount, sdate, 0);
                    step_db.document(Integer.toString(0)).set(newStep);
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensorManager.registerListener(stepDetector, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onResume() {
        super.onResume();
        txtSteps.setText(stepCount.toString());
    }



}
