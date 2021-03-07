package com.example.subscriptionmanager;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//Add database entities
@Database(entities =  {com.example.subscriptionmanager.MainData.class},version =  1, exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //Create database instance
    private static com.example.subscriptionmanager.RoomDB database;
    //Define database name
    private static  String DATABASE_NAME = "database";

    public synchronized static com.example.subscriptionmanager.RoomDB getInstance(Context context){
        //Check condition
        if (database == null) {
            //When database is null
            //Initialize database
            database = Room.databaseBuilder(context.getApplicationContext()
                    , com.example.subscriptionmanager.RoomDB.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return database;
    }

    //Create Dao (data access object)
    public abstract com.example.subscriptionmanager.MainDao mainDao();
}