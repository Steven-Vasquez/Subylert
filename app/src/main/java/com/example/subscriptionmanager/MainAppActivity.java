package com.example.subscriptionmanager;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainAppActivity extends AppCompatActivity {
    DecimalFormat money = new DecimalFormat("$0.00");
    private double totalPrice=0;
    private int totalSubscriptions=0;
    RecyclerView recyclerView;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        recyclerView = findViewById(R.id.recycler_view);
        try
        {
            this.getSupportActionBar().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();

        //Initialize linear layout manager
        linearLayoutManager = new LinearLayoutManager(this);
        //Set layout manager
        recyclerView.setLayoutManager(linearLayoutManager);
        //Initialize adapter
        adapter = new MainAdapter(com.example.subscriptionmanager.MainAppActivity.this,dataList);
        //Set adapter
        recyclerView.setAdapter(adapter);

        findViewById(R.id.settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.settings:
                        Intent intent = new Intent(MainAppActivity.this, SettingsActivity.class);
                        MainAppActivity.this.startActivity(intent);
                        break;
                }
            }
        });
//        findViewById(R.id.signOutButton).setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.signOutButton:
//                        signOut();
//                        break;
//                }
//            }
//        });
    }
//    private void signOut() {
//
//        MainActivity.mGoogleSignInClient.signOut()
//                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        closeSubscription();
//                        //revokeAccess();
//
//                    }
//                });
//    }


    //Later, come back and follow these instructions at bottom of page to remove google account info from app
    //https://developers.google.com/identity/sign-in/android/disconnect
    /*private void revokeAccess() {
        MainActivity.mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }*/
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        setTotals();
    }
    public void setTotals(){
        totalPrice = 0;
        totalSubscriptions = 0;
        database = RoomDB.getInstance(this);
        if(database!=null) {

            dataList = database.mainDao().getAll();
            for (int i = 0; i < dataList.size(); i++) {
                totalSubscriptions++;
                totalPrice += dataList.get(i).getPrice();
            }
        }
        updateViews();
    }
    public void updateViews(){
        TextView totalPriceView = (TextView) findViewById(R.id.totalPrice);
        totalPriceView.setText(money.format(totalPrice));
        TextView totalSubscriptionsView = (TextView) findViewById(R.id.totalSubscriptions);
        totalSubscriptionsView.setText("for "+ totalSubscriptions+" subscriptions.");
    }
    public void addNewSubscription(View v) {
        Intent intent = new Intent(this, AddSubscriptionActivity.class);
        this.startActivity(intent);
    }
//    public void closeSubscription() {
//        Intent intent = new Intent(this, MainActivity.class);
//        this.finish();
//        this.startActivity(intent);
//    }
}
