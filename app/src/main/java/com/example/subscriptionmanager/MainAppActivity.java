package com.example.subscriptionmanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.Html;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainAppActivity extends AppCompatActivity {
    DecimalFormat money = new DecimalFormat("$0.00");
    private int totalPrice=400;
    private int totalSubscriptions=50;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_app);
        findViewById(R.id.signOutButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.signOutButton:
                        signOut();
                        break;
                }
            }
        });
    }
    private void signOut() {

        MainActivity.mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        closeSubscription();
                        //revokeAccess();

                    }
                });
    }

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
    public void closeSubscription() {
        Intent intent = new Intent(this, MainActivity.class);
        this.finish();
        this.startActivity(intent);
    }
}
