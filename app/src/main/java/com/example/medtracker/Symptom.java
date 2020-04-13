package com.example.medtracker;

public class Symptom{
    public String symptom;
    public String date;


    public Symptom(){

    }

    public Symptom(String symptom, String date){
        this.symptom = symptom;
        this.date = date;

    }

    public String getDate() {
        return date;
    }

    public String getSymptom() {
        return symptom;
    }
}
