package com.example.medtracker;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddNoteActivity extends AppCompatActivity {

    DatePickerDialog picker;
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
                picker = new DatePickerDialog(AddNoteActivity.this,
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
        //Date date = new Date();
        //final String sdate = new SimpleDateFormat("MM-dd-yyyy").format(date);

        CollectionReference notes = mDatabase.collection("notes");
        EditText editTxtNote = (EditText) findViewById(R.id.editNoteText);
        String note = editTxtNote.getText().toString();

        EditText editTxtDate = (EditText) findViewById(R.id.editDateText);
        String date = editTxtDate.getText().toString();



        Note newNote = new Note(note, date, uid);

        notes.document(Integer.toString(uid)).set(newNote);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.item1:
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
                return true;
            case R.id.item2:
                Intent intent2 = new Intent(getApplicationContext(), MedicationsActivity.class);
                startActivity(intent2);
                return true;
            case R.id.item3:
                Intent intent3 = new Intent(getApplicationContext(), SymptomsActivity.class);
                startActivity(intent3);
                return true;
            case R.id.item4:
                Intent intent4 = new Intent(getApplicationContext(), StepsActivity.class);
                startActivity(intent4);
                return true;
            case R.id.item5:
                Intent intent5 = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent5);

                return true;
            default:
                return true;
        }
    }


}
