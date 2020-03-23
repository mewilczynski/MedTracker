package com.example.medtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    private FirebaseFirestore mDatabase;
    //uids will increase as user adds notes and the value will hold throughout
    public static int uid = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        Button button = (Button) findViewById(R.id.add_sym_btn_sym);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent);
                writeNewNote();
               // uid++;
            }
        });
    }

    public class Note {

        public String note;
        public String today;
        public int uid;

        public Note() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public Note(String note, String today, int uid) {
            this.note = note;
            this.today = today;
            this.uid = uid;
        }

    }


    private void writeNewNote() {
        Date date = new Date();
        final String sdate = new SimpleDateFormat("MM-dd-yyyy").format(date);

        CollectionReference notes = mDatabase.collection("notes");
        EditText editTxtNote = (EditText) findViewById(R.id.editNoteText);
        String note = editTxtNote.getText().toString();

        EditText editTxtDate = (EditText) findViewById(R.id.editDateText);
        //String date = editTxtDate.getText().toString();



        Note newNote = new Note(note, sdate, uid);

        notes.document(Integer.toString(uid)).set(newNote);

    }
}
