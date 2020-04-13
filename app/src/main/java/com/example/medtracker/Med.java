package com.example.medtracker;

public class Med {
    private String type;
    private String name;
    private String color;
    private String dosage;
    private reminder reminder;

    public Med() {

    }

    public Med(String type, String name, String color, String dosage, reminder reminder){
        this.type=type;this.name=name;this.color=color;this.dosage=dosage;this.reminder=reminder;
    }
    public String getType(){return type;}
    public String getName(){return name;}
    public String getColor(){return color;}
    public String getDosage(){return dosage;}

    public com.example.medtracker.reminder getReminder() {
        return reminder;
    }
}
