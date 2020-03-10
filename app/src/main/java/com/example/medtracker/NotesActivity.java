package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NotesActivity extends AppCompatActivity {

    private FirebaseFirestore mDatabase;
    private Query mQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        mQuery = mDatabase.collection("notes");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Button button = (Button) findViewById(R.id.add_note_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                startActivity(intent);
                GetData();
            }
        });
    }

    private void GetData()
    {
        DocumentReference mQuery = mDatabase.collection("notes").document("0");

        String note = mQuery.collection("note").toString();
        String date = mQuery.collection("today").toString();

        TextView disTxtDate = (TextView) findViewById(R.id.textView24);
        String disText = "- " + date;
        disTxtDate.setText(disText);

        TextView disTxtNote = (TextView) findViewById(R.id.textView24);
        disText = "• " + note;
        disTxtNote.setText(disText);


    }
}
