package com.example.medtracker;

import android.app.Application;

import java.util.Vector;

public class Globals extends Application {

    private Vector<Med> medVector = new Vector<Med>(100);

    public Vector<Med> getMedVector(){
        return medVector;
    }
    public void setMedVector(Vector<Med> x){
        medVector = x;
    }

    private int medicationsSize;

    public void setMedicationsSize(int size){
        medicationsSize= size;
    }
    public int getMedicationsSize(){
        return medicationsSize;
    }


}
