package com.example.medtracker;

public class reminder {
    private String date;
    private String time;
    private String note;

    public reminder(){

    }

    public reminder(String date, String time, String note){
        this.date=date;this.time=time;this.note=note;
    }

    public String getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public String getTime() {
        return time;
    }
}
