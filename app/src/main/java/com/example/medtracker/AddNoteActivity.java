package com.example.medtracker;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

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
                try {
                    writeDataInternal();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                startActivity(intent);

                //writeNewNote();
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
    private void writeDataInternal() throws IOException {


        boolean check = false;
        EditText editSymptom = (EditText) findViewById(R.id.editNoteText);
        String symptom = editSymptom.getText().toString();
        EditText datepicker2 = (EditText) findViewById(R.id.editDateText);
        String date = datepicker2.getText().toString();

        Symptom newSymptom = new Symptom(symptom, date);

        File path = getApplicationContext().getFilesDir();

        File file = new File(path,"notes.txt");

        if(!file.exists()){
            file.createNewFile();
            String s = newSymptom.getDate()+"^"+newSymptom.getSymptom()+"#";
            FileOutputStream stream = new FileOutputStream(file);
            try{
                stream.write(s.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } finally{
                stream.close();
            }
            check=true;
        }

        int length = (int) file.length();
        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try{
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            in.close();
        }

        String contents = new String(bytes);

        if(contents.isEmpty()){

        }else{
            if(!check){
                StringBuilder str = new StringBuilder(contents);

                String s = newSymptom.getDate()+"^"+newSymptom.getSymptom()+"#";
                str.append(s);

                FileOutputStream stream = new FileOutputStream(file);
                try{
                    stream.write(str.toString().getBytes());
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    stream.close();
                }
            }

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
            case R.id.item6:
                signOut();

                return true;
            default:
                return true;
        }
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>(){

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(AddNoteActivity.this, MainActivity.class));
                        finish();

                    }
                });
    }


}
