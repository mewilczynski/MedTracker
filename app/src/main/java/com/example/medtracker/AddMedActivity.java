package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class AddMedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);


        Button button_yes = (Button) findViewById(R.id.yes_btn);
        Button button_no = (Button) findViewById(R.id.no_btn);
        Button button = (Button) findViewById(R.id.add_med);

        final LinearLayout reminder_layout = (LinearLayout) findViewById(R.id.reminder);
        button_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder_layout.setVisibility(View.VISIBLE);
            }
        });

        button_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reminder_layout.setVisibility(View.GONE);
            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });
    }
}
