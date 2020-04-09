package com.example.medtracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import java.util.Vector;
import java.util.List;
import java.util.ArrayList;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class StepsActivity extends AppCompatActivity {
    private LineGraphSeries<DataPoint> series;
    int order = 0;
    private FirebaseFirestore mDatabase ;
    private Query m1Query;

    private static final String NUMBER = "number";
    private static final String DATE = "date";
    Vector<Integer> dates_v = new Vector<>();
    final String TAG = "STEPS ACTIVITY";
    String[] val = new String[]{};
    String[] dates = new String[]{};

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mDatabase = FirebaseFirestore.getInstance();
        m1Query = mDatabase.collection("steps");
        for(int o = 0; o<32; o++){
            dates_v.add(0);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            val = bundle.getStringArray("list");
            dates = bundle.getStringArray("days");
        }
        Log.e(TAG,Integer.toString(val.length) +"gathered the list");
        for(int y = 0 ; y<val.length; y++){
            dates_v.set(Integer.parseInt(dates[y]), Integer.parseInt(val[y]));
        }


        GraphView graph = (GraphView) findViewById(R.id.steps_graph);

        GridLabelRenderer gridLabelRenderer = graph.getGridLabelRenderer();
        gridLabelRenderer.setHorizontalAxisTitle("Days");
        gridLabelRenderer.setVerticalAxisTitle("Steps");

        int x, y;
        x = 0;
        y = 0;
        series = new LineGraphSeries<>();
        int num_days = 31;
        for(int i = 1; i<= num_days ; i++){
            x = x+1;
            y = dates_v.elementAt(i);
            series.appendData(new DataPoint(x,y), true, 100);
        }
        graph.addSeries(series);
    }

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
            case R.id.item5:
                Intent intent5 = new Intent(getApplicationContext(), NotesActivity.class);
                startActivity(intent5);

                return true;
            default:
                return true;
        }
    }
}
