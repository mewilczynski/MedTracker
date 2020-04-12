package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.FirebaseFirestore;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.DocumentReference;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class NotesActivity extends AppCompatActivity {
   // private NoteClass note1;
    private FirebaseFirestore mDatabase ;
    private static final String NOTE = "note";
    private static final String DATE = "today";

    private Query m1Query;
    String note;
    String date;
    String snapshot = "";
    int count = 0;
    //private CollectionReference note1;
    private CollectionReference note2;;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        m1Query = mDatabase.collection("notes");




        //GetData();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        Button button = (Button) findViewById(R.id.add_note_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), AddNoteActivity.class);
                startActivity(intent);
                //GetData();
                count++;
            }
        });
    }

    private void GetData()
    {

        DocumentReference mQuery = mDatabase.collection("notes").document("0");
        mQuery.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if(documentSnapshot.exists()){
                        note = documentSnapshot.getString(NOTE);
                        date = documentSnapshot.getString(DATE);
                       // note1 = documentSnapshot.getData();
                        TextView disTxtDate = (TextView) findViewById(R.id.textView25);
                        TextView disTxtNote = (TextView) findViewById(R.id.textView24);
                        disTxtDate.setVisibility(View.VISIBLE);
                        disTxtNote.setVisibility(View.VISIBLE);
                       // TextView disTxtDate = (TextView) findViewById(R.id.textView25);
                        if(note==""){
                            disTxtDate.setText("");
                            disTxtNote.setText("");
                        }else{
                            String disText = "- " + note;

                            disTxtDate.setText(disText);
                            String date_text = "• " + date;
                            //TextView disTxtNote = (TextView) findViewById(R.id.textView24);
                            //disText = "• " + note;
                            disTxtNote.setText(date_text);
                        }

                    }
                }
            }
        });


    /*
        TextView disTxtDate = (TextView) findViewById(R.id.textView25);
        String disText = "- " + note;
        disTxtDate.setText(disText);
        String date_text = "• " + date;
        TextView disTxtNote = (TextView) findViewById(R.id.textView24);
        //disText = "• " + note;
        disTxtNote.setText(date_text);
*/

    }
    @Override
    public void onResume(){
        super.onResume();
        //disTxtDate.setVisibility(View.VISIBLE);
        //disTxtNote.setVisibility(View.VISIBLE);
        GetData();
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
