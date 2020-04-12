package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
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

    private boolean removeOn = false;
    private Vector<String> medIds = new Vector<String>(10);
    private Vector<String> searchmedIds = new Vector<String>(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);

        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
        layout11.setVisibility(View.GONE);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.linearLayout3);
        layout11.setVisibility(View.VISIBLE);


        resetRemovebuttons();

        final Button remove = (Button) findViewById(R.id.add_med2); //remove button

        final Drawable blueCir = getResources().getDrawable(R.drawable.circle_button);
        final Drawable greenCir = getResources().getDrawable(R.drawable.circle_button_green);

        for(int i = 0; i<10;i++){
            String SLid = "med"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
            String Sbutton = "med1x" + Integer.toString(i+1);
            int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
            Button buttonx = (Button) findViewById(buttonid);
            buttonx.setVisibility(View.GONE);
        }
        for(int i = 0; i<10;i++){
            String SLid = "meda"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
            String Sbutton = "med1xa" + Integer.toString(i+1);
            int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
            Button buttonx = (Button) findViewById(buttonid);
            buttonx.setVisibility(View.GONE);
        }
        //updateMed();
        //populateList();
        //populateList();
        refreshView();
        //updateMed();
        final Button button = (Button) findViewById(R.id.add_med);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(removeOn){
                    button.setBackground(blueCir);
                    button.setText(R.string.plus_btn_txt);
                    remove.setVisibility(View.VISIBLE);
                    removeOn=false;
                    resetRemovebuttons();
                }else {
                    Intent intent = new Intent(getApplicationContext(), AddMedActivity.class);
                    startActivity(intent);
                }
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




        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove.setVisibility(View.GONE);
                removeOn = true;
                button.setText(R.string.check_button);
                button.setBackground(greenCir);
                popRemovebuttons();
            }
        });

        //all remove buttons
        Button r1 = (Button) findViewById(R.id.med1x1);
        Button r2 = (Button) findViewById(R.id.med1x2);
        Button r3 = (Button) findViewById(R.id.med1x3);
        Button r4 = (Button) findViewById(R.id.med1x4);
        Button r5 = (Button) findViewById(R.id.med1x5);
        Button r6 = (Button) findViewById(R.id.med1x6);
        Button r7 = (Button) findViewById(R.id.med1x7);
        Button r8 = (Button) findViewById(R.id.med1x8);
        Button r9 = (Button) findViewById(R.id.med1x9);
        Button r10 = (Button) findViewById(R.id.med1x10);

        Button ra1 = (Button) findViewById(R.id.med1xa1);
        Button ra2 = (Button) findViewById(R.id.med1xa2);
        Button ra3 = (Button) findViewById(R.id.med1xa3);
        Button ra4 = (Button) findViewById(R.id.med1xa4);
        Button ra5 = (Button) findViewById(R.id.med1xa5);
        Button ra6 = (Button) findViewById(R.id.med1xa6);
        Button ra7 = (Button) findViewById(R.id.med1xa7);
        Button ra8 = (Button) findViewById(R.id.med1xa8);
        Button ra9 = (Button) findViewById(R.id.med1xa9);
        Button ra10 = (Button) findViewById(R.id.med1xa10);

        r1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(0)).delete();
                }
                populateList();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(1)).delete();
                }
                populateList();
            }
        });
        r3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(2)).delete();
                }
                populateList();
            }
        });
        r4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(3)).delete();
                }
                populateList();
            }
        });
        r5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(4)).delete();
                }
                populateList();
            }
        });
        r6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(5)).delete();
                }
                populateList();
            }
        });
        r7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(6)).delete();
                }
                populateList();
            }
        });
        r8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(7)).delete();
                }
                populateList();
            }
        });
        r9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(8)).delete();
                }
                populateList();
            }
        });
        r10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(medIds.elementAt(9)).delete();
                }
                populateList();
            }
        });
        ra1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(0)).delete();
                }
                populateSearchList();
            }
        });
        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(1)).delete();
                }
                populateSearchList();
            }
        });
        ra3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(2)).delete();
                }
                populateSearchList();
            }
        });
        ra4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(3)).delete();
                }
                populateSearchList();
            }
        });
        ra5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(4)).delete();
                }
                populateSearchList();
            }
        });
        ra6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(5)).delete();
                }
                populateSearchList();
            }
        });
        ra7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(6)).delete();
                }
                populateSearchList();
            }
        });
        ra8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(7)).delete();
                }
                populateSearchList();
            }
        });
        ra9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(8)).delete();
                }
                populateSearchList();
            }
        });
        ra10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!medIds.isEmpty()){
                    mDatabase.collection("medications").document(searchmedIds.elementAt(9)).delete();
                }
                populateSearchList();
            }
        });









    }

    private void populateList(){

        j = 1;
        medIds.clear();
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
                                    medIds.add(med.getType()+med.getName());
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

        searchmedIds.clear();
        for(int i = 0; i<10;i++){
            String SLid = "meda"+ Integer.toString(i+1);
            int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
            ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);
            layout.setVisibility(View.GONE);
            String Sbutton = "med1xa" + Integer.toString(i+1);
            int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
            Button buttonx = (Button) findViewById(buttonid);
            buttonx.setVisibility(View.GONE);
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
                                        searchmedIds.add(med.getType()+med.getName());
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

        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayout3);
        layout11.setVisibility(View.GONE);
    }


    private void popRemovebuttons(){
        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout3);
        if(layout.getVisibility()==View.VISIBLE){

            for(int i = 0; i<10;i++){
                String SLid = "med"+ Integer.toString(i+1);
                int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                ConstraintLayout l = (ConstraintLayout) findViewById(Lid);

                if(l.getVisibility()==View.VISIBLE){
                    String Sbutton = "med1x" + Integer.toString(i+1);
                    int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
                    Button buttonx = (Button) findViewById(buttonid);
                    buttonx.setVisibility(View.VISIBLE);
                }

            }


        }else{
            for(int i = 0; i<10;i++){
                String SLid = "meda"+ Integer.toString(i+1);
                int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                ConstraintLayout l = (ConstraintLayout) findViewById(Lid);

                if(l.getVisibility()==View.VISIBLE){
                    String Sbutton = "med1xa" + Integer.toString(i+1);
                    int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
                    Button buttonx = (Button) findViewById(buttonid);
                    buttonx.setVisibility(View.VISIBLE);
                }

            }
        }
    }
    private void resetRemovebuttons(){
        for(int i = 0; i<10;i++){

            String Sbutton = "med1x" + Integer.toString(i+1);
            int buttonid = getResources().getIdentifier(Sbutton,"id",getPackageName());
            Button buttonx = (Button) findViewById(buttonid);
            buttonx.setVisibility(View.GONE);

            String Sbuttona = "med1xa" + Integer.toString(i+1);
            int buttonida = getResources().getIdentifier(Sbuttona,"id",getPackageName());
            Button buttonxa = (Button) findViewById(buttonida);
            buttonxa.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume(){
        super.onResume();

        populateList();
        refreshView();
        /*
        LinearLayout layout11 = (LinearLayout) findViewById(R.id.linearLayouta3);
        layout11.setVisibility(View.GONE);
        LinearLayout layout1 = (LinearLayout) findViewById(R.id.linearLayout3);
        layout11.setVisibility(View.VISIBLE);*/

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
            case R.id.item5:
                Intent intent5 = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent5);

                return true;
            default:
                return true;
        }
    }
}
