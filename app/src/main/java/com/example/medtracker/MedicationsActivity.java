package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medications);



        for(int i = 0; i<10;i++){
            String SLid = "med"+ Integer.toString(i+1);
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

        Vector<Med> m = medVec;
/*
        for(int i = 0; i < 10; i++){
            if(i<m.size()/2){
                //layout
                String SLid = "med"+ Integer.toString(i+1);
                int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);

                //textview
                String Stxtid = "medtxt"+Integer.toString(i+1);
                int txtid = getResources().getIdentifier(Stxtid,"id", getPackageName());
                TextView med1 = (TextView) findViewById(txtid);
                String m1 = "• "+m.elementAt(i).getType() + " " + m.elementAt(i).getName() + " - " + m.elementAt(i).getDosage() + "mg";
                med1.setText(m1);

                layout.setVisibility(View.VISIBLE);
            }


        }

*/




        //((Globals) this.getApplication()).setMedVector(medVec);
       // updateMed();
    }

    private void updateMed(){

        Vector<Med> m = ((Globals) this.getApplication()).getMedVector();

        for(int i = 0; i < 10; i++){
            if(i<m.size()/2){
                //layout
                String SLid = "med"+ Integer.toString(i+1);
                int Lid = getResources().getIdentifier(SLid,"id", getPackageName());
                ConstraintLayout layout = (ConstraintLayout) findViewById(Lid);

                //textview
                String Stxtid = "medtxt"+Integer.toString(i+1);
                int txtid = getResources().getIdentifier(Stxtid,"id", getPackageName());
                TextView med1 = (TextView) findViewById(txtid);
                String m1 = "• "+m.elementAt(i).getType() + " " + m.elementAt(i).getName() + " - " + m.elementAt(i).getDosage() + "mg";
                med1.setText(m1);

                layout.setVisibility(View.VISIBLE);
            }


        }



    }
    @Override
    public void onResume(){
        super.onResume();
        //updateMed();
        populateList();
        refreshView();
       // updateMed();
    }
    private void refreshView(){
        NestedScrollView mScrollView = (NestedScrollView) findViewById(R.id.scroll);
        mScrollView.setVisibility(View.GONE);
        mScrollView.setVisibility(View.VISIBLE);
    }
}
