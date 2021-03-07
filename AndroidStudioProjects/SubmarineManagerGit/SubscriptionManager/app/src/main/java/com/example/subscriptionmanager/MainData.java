package com.example.subscriptionmanager;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

//Define table name
@Entity(tableName = "table_name")
public class MainData implements Serializable {

    //Create id column
    @PrimaryKey(autoGenerate =  true)
    private int ID;

    //Create text column
    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "price")
    private double price;

    @ColumnInfo(name = "frequency")
    private int frequency;

    @ColumnInfo(name = "startMonth")
    private int startMonth;

    @ColumnInfo(name = "startDate")
    private int startDate;

    @ColumnInfo(name = "startYear")
    private int startYear;


    @ColumnInfo(name="frequencyType")
    private String frequencyType;

    //getters and setters
    public int getID() {
        return ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public int getStartMonth() { return startMonth; }

    public int getStartDate() { return startDate; }

    public int getStartYear() { return startYear; }

    public void setStartMonth(int startMonth) {this.startMonth=startMonth;}
    public void setStartDate(int startDate) {this.startDate=startDate;}
    public void setStartYear(int startYear) {this.startMonth=startYear;}

    public void setDate(Date startDate) {
        startMonth = startDate.getYear();
        startYear = startDate.getMonth()+1;
        this.startDate = startDate.getDate();

    }

    public String getFrequencyType() { return frequencyType; }

    public void setFrequencyType(String frequencyType) { this.frequencyType = frequencyType; }

    public double getPrice() { return this.price; }

    public void setPrice(double price) { this.price = price; }

    public int getFrequency() { return frequency; }

    public void setFrequency(int frequency) { this.frequency = frequency; }
}
