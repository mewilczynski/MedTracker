package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

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

}
