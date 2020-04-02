package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class MedicationsActivity extends AppCompatActivity {

    private static Vector<Med> medVec = new Vector<Med>(100);

    private FirebaseFirestore mDatabase ;
    private static final String TAG = "MedicationsActivity";
    //private static Vector<Med> medVec = new Vector<Med>(100);

    private int j = 1;
    private int k = 1;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
        layout11.setVisibility(View.GONE);

        for(int i = 0; i<10;i++){
            String SLid = "med"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
        }
        for(int i = 0; i<10;i++){
            String SLid = "meda"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
        }
        //updateMed();
        //populateList();
        //populateList();
        refreshView();
        //updateMed();
        Button button = (Button) findViewById(R.id.add_med);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMedActivity.class);
                startActivity(intent);
            }
        });

        SearchView search = (SearchView) findViewById(R.id.search);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //key = query;
                //populateSearchList();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                key = newText;
                populateSearchList();
                return false;
            }
        });

    }

    private void populateList(){

        j = 1;

        mDatabase.collection("medications").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Med med  = document.toObject(Med.class);
                                medVec.add(med);



                                if(j<=10){
                                    String SLid = "med"+ Integer.toString(j);
                                    int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                                    ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);

                                    //textview
                                    String Stxtid = "medtxt"+Integer.toString(j);
                                    int txtid = getResources().getIdentifier(Stxtid,"id", getPackageName());
                                    TextView med1 = (TextView) findViewById(txtid);
                                    String m1 = "• "+med.getType() + " " + med.getName() + " - " + med.getDosage() + "mg";
                                    med1.setText(m1);

                                    layout.setVisibility(View.VISIBLE);

                                    j++;
                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });



    }

    private void populateSearchList(){

        for(int i = 0; i<10;i++){
            String SLid = "meda"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
        }
        k = 1;

        mDatabase.collection("medications").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                Med med  = document.toObject(Med.class);
                                medVec.add(med);

                                if(k<=10){
                                    if(med.getName().toString().contains(key) || med.getType().toString().contains(key)){
                                        String SLid = "meda"+ Integer.toString(k);
                                        int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                                        ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);

                                        //textview
                                        String Stxtid = "medtxta"+Integer.toString(k);
                                        int txtid = getResources().getIdentifier(Stxtid,"id", getPackageName());
                                        TextView med1 = (TextView) findViewById(txtid);
                                        String m1 = "• "+med.getType() + " " + med.getName() + " - " + med.getDosage() + "mg";
                                        med1.setText(m1);

                                        layout.setVisibility(View.VISIBLE);
                                        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
                                        layout11.setVisibility(View.VISIBLE);
                                        k++;
                                    }else{
                                        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
                                        layout11.setVisibility(View.VISIBLE);
                                    }

                                }

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    @Override
    public void onResume(){
        super.onResume();

        populateList();
        refreshView();
        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
        layout11.setVisibility(View.GONE);

    }
    private void refreshView(){
        NestedScrollView mScrollView = (NestedScrollView) findViewById(R.id.scroll);
        mScrollView.setVisibility(View.GONE);
        mScrollView.setVisibility(View.VISIBLE);
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
            default:
                return true;
        }
    }
}
