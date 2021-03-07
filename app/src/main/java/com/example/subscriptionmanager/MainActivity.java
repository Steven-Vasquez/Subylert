package com.example.subscriptionmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);
        //setContentView(R.layout.activity_main_app);
    }
    public void onClick(View v){
        Intent intent = new Intent(MainActivity.this, MainAppActivity.class);
        MainActivity.this.startActivity(intent);
    }
}