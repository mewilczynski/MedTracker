package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMedActivity extends AppCompatActivity {

    private FirebaseFirestore mDatabase;
    private String color;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mDatabase = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_med);

        final ImageView bl = (ImageView) findViewById(R.id.bl);
        final ImageView br = (ImageView) findViewById(R.id.br);
        final ImageView g = (ImageView) findViewById(R.id.g);
        final ImageView p = (ImageView) findViewById(R.id.p);
        final ImageView r = (ImageView) findViewById(R.id.r);

        final ImageView blc = (ImageView) findViewById(R.id.blc);
        final ImageView brc = (ImageView) findViewById(R.id.brc);
        final ImageView gc = (ImageView) findViewById(R.id.gc);
        final ImageView pc = (ImageView) findViewById(R.id.pc);
        final ImageView rc = (ImageView) findViewById(R.id.rc);

        Button button = (Button) findViewById(R.id.add_med);
        Switch myswitch = (Switch) findViewById(R.id.switch1);

        Button blue_btn = (Button) findViewById(R.id.bl_btn);
        Button brown_btn = (Button) findViewById(R.id.br_btn);
        Button green_btn = (Button) findViewById(R.id.g_btn);
        Button pink_btn = (Button) findViewById(R.id.p_btn);
        Button red_btn = (Button) findViewById(R.id.r_btn);

        Button bluec_btn = (Button) findViewById(R.id.blc_btn);
        Button brownc_btn = (Button) findViewById(R.id.brc_btn);
        Button greenc_btn = (Button) findViewById(R.id.gc_btn);
        Button pinkc_btn = (Button) findViewById(R.id.pc_btn);
        Button redc_btn = (Button) findViewById(R.id.rc_btn);




        final LinearLayout reminder_layout = (LinearLayout) findViewById(R.id.reminder);

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    reminder_layout.setVisibility(View.VISIBLE);
                }else{
                    reminder_layout.setVisibility(View.GONE);
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMed();
                Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                startActivity(intent);
            }
        });


        //yellow circle button
        blue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "yellow";
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        brown_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "brown";
                bl.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        green_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "green";
                br.setImageResource(R.drawable.grey);
                bl.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        pink_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "pink";
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                bl.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        red_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "white";
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                bl.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        bluec_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "bluec";
                bl.setImageResource(R.drawable.grey);
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        brownc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "brownc";
                bl.setImageResource(R.drawable.grey);
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                blc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        greenc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "greenc";
                bl.setImageResource(R.drawable.grey);
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                brc.setImageResource(R.drawable.greyc);
                blc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        pinkc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "pinkc";
                bl.setImageResource(R.drawable.grey);
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                blc.setImageResource(R.drawable.greyc);
                rc.setImageResource(R.drawable.greyc);
            }
        });
        redc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnBackground();
                color = "redc";
                bl.setImageResource(R.drawable.grey);
                br.setImageResource(R.drawable.grey);
                g.setImageResource(R.drawable.grey);
                p.setImageResource(R.drawable.grey);
                r.setImageResource(R.drawable.grey);

                brc.setImageResource(R.drawable.greyc);
                gc.setImageResource(R.drawable.greyc);
                pc.setImageResource(R.drawable.greyc);
                blc.setImageResource(R.drawable.greyc);
            }
        });


    }

    private void btnBackground(){

        ImageView bl = (ImageView) findViewById(R.id.bl);
        ImageView br = (ImageView) findViewById(R.id.br);
        ImageView g = (ImageView) findViewById(R.id.g);
        ImageView p = (ImageView) findViewById(R.id.p);
        ImageView r = (ImageView) findViewById(R.id.r);

        ImageView blc = (ImageView) findViewById(R.id.blc);
        ImageView brc = (ImageView) findViewById(R.id.brc);
        ImageView gc = (ImageView) findViewById(R.id.gc);
        ImageView pc = (ImageView) findViewById(R.id.pc);
        ImageView rc = (ImageView) findViewById(R.id.rc);

        bl.setImageResource(R.drawable.yellow);
        br.setImageResource(R.drawable.brown);
        g.setImageResource(R.drawable.green);
        p.setImageResource(R.drawable.pink);
        r.setImageResource(R.drawable.white);

        blc.setImageResource(R.drawable.bluec);
        brc.setImageResource(R.drawable.brownc);
        gc.setImageResource(R.drawable.greenc);
        pc.setImageResource(R.drawable.pinkc);
        rc.setImageResource(R.drawable.redc);

    }

    public void saveMed(){

        final CollectionReference medications = mDatabase.collection("medications");
        EditText editTxtType = (EditText) findViewById(R.id.editTextType);
        String type = editTxtType.getText().toString();
        EditText editTxtName = (EditText) findViewById(R.id.editTextName);
        String name = editTxtName.getText().toString();
        EditText editTxtDos = (EditText) findViewById(R.id.editTextDos);
        String dosage = editTxtDos.getText().toString();
/*
        Map<String, Object> med = new HashMap<>();
        med.put("type",type);
        med.put("name",name);
        med.put("color", color);
        med.put("shape", shape);
*/
        Med med = new Med(type,name,color,dosage);
        String uid = type+name;
        medications.document(uid).set(med);

    }

    @Override
    public void onResume(){
        super.onResume();
    }

}
