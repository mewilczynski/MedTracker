package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;

public class SymptomsActivity extends AppCompatActivity {

    //private FirebaseFirestore mDatabase ;
    private static final String NOTE = "note";
    private static final String DATE = "today";
    private static final String TAG = "SymptomsActivity";
    private int j = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);

        //mDatabase = FirebaseFirestore.getInstance();

        Button button = (Button) findViewById(R.id.add_sym);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddSymptomsActivity.class);
                startActivity(intent); //launch addSymptoms activity
            }
        });





    }

    private void popInternal() throws IOException {

        resetTxt();

        final TextView txt1 = (TextView) findViewById(R.id.date1);
        final TextView txt2 = (TextView) findViewById(R.id.date2);
        final TextView txt3 = (TextView) findViewById(R.id.date3);
        final TextView txt4 = (TextView) findViewById(R.id.date4);

        final TextView txt11 = (TextView) findViewById(R.id.sym1);
        final TextView txt22 = (TextView) findViewById(R.id.sym2);
        final TextView txt33 = (TextView) findViewById(R.id.sym3);
        final TextView txt44 = (TextView) findViewById(R.id.sym4);


        File path = getApplicationContext().getFilesDir();

        File file = new File(path,"symptoms3.txt");



        int length = (int) file.length();
        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file);
        try{
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String contents = new String(bytes);

        if(!contents.isEmpty()){
            String sub1 = contents.substring(0,contents.indexOf("#"));
            String temp = contents.substring(contents.indexOf("#")+1,contents.length());

            String sub2 = "",sub3= "",sub4= "";
            if(!temp.isEmpty()){
                sub2 = temp.substring(0,temp.indexOf("#"));
                temp = temp.substring(temp.indexOf("#")+1,temp.length());
            }
            if(!temp.isEmpty()){
                sub3 = temp.substring(0,temp.indexOf("#"));
                temp = temp.substring(temp.indexOf("#")+1,temp.length());
            }
            if(!temp.isEmpty()){
                sub4 = temp.substring(0,temp.indexOf("#"));
                temp = temp.substring(temp.indexOf("#")+1,temp.length());
            }


            if(!sub1.isEmpty()){
                String date = sub1.substring(0,sub1.indexOf("^"));
                String temp0 = sub1.substring(sub1.indexOf("^")+1,sub1.length());

                String sym = temp0;


                txt1.setVisibility(View.VISIBLE);
                txt11.setVisibility(View.VISIBLE);
                txt1.setText("• " + date);
                txt11.setText("- "+ sym);
            }
            if(!sub2.isEmpty()){
                String date = sub2.substring(0,sub2.indexOf("^"));
                String temp0 = sub2.substring(sub2.indexOf("^")+1,sub2.length());

                String sym = temp0;
                txt2.setVisibility(View.VISIBLE);
                txt22.setVisibility(View.VISIBLE);
                txt2.setText("• " + date);
                txt22.setText("- "+ sym);
            }
            if(!sub3.isEmpty()){
                String date = sub3.substring(0,sub3.indexOf("^"));
                String temp0 = sub3.substring(sub3.indexOf("^")+1,sub3.length());

                String sym = temp0;

                txt3.setVisibility(View.VISIBLE);
                txt33.setVisibility(View.VISIBLE);
                txt3.setText("• " + date);
                txt33.setText("- "+ sym);
            }
            if(!sub4.isEmpty()){
                String date = sub4.substring(0,sub4.indexOf("^"));
                String temp0 = sub4.substring(sub4.indexOf("^")+1,sub4.length());

                String sym = temp0;
                txt4.setVisibility(View.VISIBLE);
                txt44.setVisibility(View.VISIBLE);
                txt4.setText("• " + date);
                txt44.setText("- "+ sym);
            }
        }


    }
/*
    private void popSym(){

        resetTxt();

        j = 1;
        final TextView txt1 = (TextView) findViewById(R.id.date1);
        final TextView txt2 = (TextView) findViewById(R.id.date2);
        final TextView txt3 = (TextView) findViewById(R.id.date3);
        final TextView txt4 = (TextView) findViewById(R.id.date4);

        final TextView txt11 = (TextView) findViewById(R.id.sym1);
        final TextView txt22 = (TextView) findViewById(R.id.sym2);
        final TextView txt33 = (TextView) findViewById(R.id.sym3);
        final TextView txt44 = (TextView) findViewById(R.id.sym4);

        mDatabase.collection("symptoms1").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        Symptom symptom  = document.toObject(Symptom.class);

                        if(j==1){
                            txt1.setVisibility(View.VISIBLE);
                            txt11.setVisibility(View.VISIBLE);
                            txt1.setText(symptom.getDate());
                            txt11.setText(symptom.getSymptom());
                        }else if(j==2){
                            txt2.setVisibility(View.VISIBLE);
                            txt22.setVisibility(View.VISIBLE);
                            txt2.setText(symptom.getDate());
                            txt22.setText(symptom.getSymptom());
                        }else if(j==3){
                            txt3.setVisibility(View.VISIBLE);
                            txt33.setVisibility(View.VISIBLE);
                            txt3.setText(symptom.getDate());
                            txt3.setText(symptom.getSymptom());
                        }else if(j==4){
                            txt4.setVisibility(View.VISIBLE);
                            txt44.setVisibility(View.VISIBLE);
                            txt4.setText(symptom.getDate());
                            txt4.setText(symptom.getSymptom());
                        }
                        j++;
                    }
                } else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
            }
        });
    }
*/
    private void resetTxt(){
        TextView txt1 = (TextView) findViewById(R.id.date1);
        TextView txt2 = (TextView) findViewById(R.id.date2);
        TextView txt3 = (TextView) findViewById(R.id.date3);
        TextView txt4 = (TextView) findViewById(R.id.date4);

        TextView txt11 = (TextView) findViewById(R.id.sym1);
        TextView txt22 = (TextView) findViewById(R.id.sym2);
        TextView txt33 = (TextView) findViewById(R.id.sym3);
        TextView txt44 = (TextView) findViewById(R.id.sym4);

        txt1.setVisibility(View.INVISIBLE);
        txt1.setText("");
        txt2.setVisibility(View.INVISIBLE);
        txt2.setText("");
        txt3.setVisibility(View.INVISIBLE);
        txt3.setText("");
        txt4.setVisibility(View.INVISIBLE);
        txt4.setText("");

        txt11.setVisibility(View.INVISIBLE);
        txt11.setText("");
        txt22.setVisibility(View.INVISIBLE);
        txt22.setText("");
        txt33.setVisibility(View.INVISIBLE);
        txt33.setText("");
        txt44.setVisibility(View.INVISIBLE);
        txt44.setText("");

    }

    @Override
    public void onResume(){
        super.onResume();
        try {
            popInternal();
        } catch (IOException e) {
            e.printStackTrace();
        }

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
                        startActivity(new Intent(SymptomsActivity.this, MainActivity.class));
                        finish();

                    }
                });
    }
}
