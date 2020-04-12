package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TimePicker;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class AddMedActivity extends AppCompatActivity {

    private FirebaseFirestore mDatabase;
    private String color;
    private boolean reminder = false;
    DatePickerDialog picker;
    TimePickerDialog pickerTime;
    private String reminderTime;
    private String reminderDate;

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
                    reminder = true;
                }else{
                    reminder_layout.setVisibility(View.GONE);
                    reminder = false;
                }
            }
        });



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveMed();
                Intent intent = new Intent(getApplicationContext(), MedicationsActivity.class);
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
               // setAlarm();
                createNotificationChannel();
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

        final Calendar cldr = Calendar.getInstance();
        final EditText datepicker = (EditText) findViewById(R.id.editText5);

        datepicker.setInputType(InputType.TYPE_NULL);
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(AddMedActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String date = (monthOfYear + 1) + "/" + dayOfMonth  +  "/" + year;
                                datepicker.setText(date);
                                reminderDate = date;
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        final EditText timepicker = (EditText) findViewById(R.id.editText4);


        timepicker.setInputType(InputType.TYPE_NULL);
        timepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                pickerTime = new TimePickerDialog(AddMedActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                if(sHour>12){
                                    if(sMinute<10){

                                        String Time = (sHour-12) + ":0"+sMinute + " PM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }else{
                                        String Time = (sHour-12) + ":"+sMinute + " PM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }
                                }else if(sHour==12){
                                    if(sMinute<10){

                                        String Time = (sHour) + ":0"+sMinute + " PM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }else{
                                        String Time = (sHour) + ":"+sMinute + " PM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }
                                }
                                else{
                                    if(sMinute<10){
                                        String Time = sHour + ":0"+sMinute + " AM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }else{
                                        String Time = sHour + ":"+sMinute + " AM";
                                        timepicker.setText(Time);
                                        reminderTime = Time;
                                    }
                                }


                            }
                        }, hour, minutes, false);
                pickerTime.show();
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

        if(reminder){

        }

        Med med = new Med(type,name,color,dosage);
        String uid = type+name;
        medications.document(uid).set(med);

    }


    private void setAlarm(){
        boolean alarm = (PendingIntent.getBroadcast(this, 0, new Intent("ALARM"), PendingIntent.FLAG_NO_CREATE) == null);

        if(alarm){
            Intent itAlarm = new Intent("ALARM");
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,itAlarm,0);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.add(Calendar.SECOND, 3);
            AlarmManager alarme = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarme.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),60000, pendingIntent);
        }
    }

    private void sendNoti(){
        Intent snoozeIntent = new Intent(this, AddMedActivity.class);
        //snoozeIntent.setAction();
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "50")
                .setSmallIcon(R.drawable.yellow)
                .setContentTitle("Medication Reminder")
                .setContentText("Take your damn medication")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Take your damn medication"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                 .addAction(R.drawable.rounded_corners, "Snooze",
                snoozePendingIntent)
                .addAction(R.drawable.rounded_corners, "Confirm",
                        snoozePendingIntent);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(0, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "getString(R.string.channel_name);";
            String description = "getString(R.string.channel_description)";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("50", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
        sendNoti();
    }
    @Override
    public void onResume(){
        super.onResume();
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
