package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;

public class NotesActivity extends AppCompatActivity {

    private FirebaseFirestore mDatabase ;
    private Query m1Query;
    String note;
    String date;
    int count = 0;
    private CollectionReference note1;
    private CollectionReference note2;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        m1Query = mDatabase.collection("notes");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        Button button = (Button) findViewById(R.id.add_note_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                startActivity(intent);
                GetData();
                count++;
            }
        });
    }

    private void GetData()
    {

        DocumentReference mQuery = mDatabase.collection("notes").document("0");
       /* mQuery.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                 note = documentSnapshot.getString("note");
                date = documentSnapshot.getString("date");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/
        note = mQuery.collection("note").toString();
        date = mQuery.collection("today").toString();
       // note = note2;
        TextView disTxtDate = (TextView) findViewById(R.id.textView25);
        String disText = "- " + date;
        disTxtDate.setText(disText);
        String date_text = "• " + note;
        TextView disTxtNote = (TextView) findViewById(R.id.textView24);
        //disText = "• " + note;
        disTxtNote.setText(date_text);


    }
}
