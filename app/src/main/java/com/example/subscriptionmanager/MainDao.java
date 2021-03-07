package com.example.subscriptionmanager;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface MainDao {
    //Insert query
    @Insert(onConflict = REPLACE)
    void insert(MainData mainData);

    //Delete query
    @Delete
    void delete(MainData mainData);

    //Delete all query
    @Delete
    void reset(List<MainData> mainData);

    //Update query
    @Query("UPDATE table_name SET text = :sName, price = :sPrice, frequency = :sFrequency WHERE ID = :sID")
    void update(int sID, String sName, double sPrice, int sFrequency);

    //Get all data query
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}