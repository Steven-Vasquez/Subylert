package com.example.subscriptionmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

public class MainAppActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);

    }
    @Override
    protected void onStart() {
        super.onStart();
        updateViews();
    }
    public void updateViews(){
        TextView totalSubscriptions = (TextView) findViewById(R.id.totalSubscriptions);
        totalSubscriptions.setText("$400"+"\n"+"for 50 subscriptions");
    }
    public void addNewSubscription(View v) {
        Intent intent = new Intent(this, AddSubscriptionActivity.class);
        this.startActivity(intent);
    }
}
