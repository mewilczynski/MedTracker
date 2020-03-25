package com.example.medtracker;

public class Med {
    private String type;
    private String name;
    private String color;
    private String dosage;

    public Med() {

    }

    public Med(String type, String name, String color, String dosage){
        this.type=type;this.name=name;this.color=color;this.dosage=dosage;
    }
    public String getType(){return type;}
    public String getName(){return name;}
    public String getColor(){return color;}
    public String getDosage(){return dosage;}
}
