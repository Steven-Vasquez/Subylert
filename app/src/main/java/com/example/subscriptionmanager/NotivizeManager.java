package com.example.subscriptionmanager;

import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

class Helper extends TimerTask
{
    private String test="go";
    private String subscriptionName;
    private int frequency;
    private String frequencyType;
    private double price;
    private int counter;
    public Helper(String name, int frequency, String frequencyType, double price){
        subscriptionName=name;
        this.frequency =frequency;
        this.frequencyType=frequencyType;
        this.price=price;
        counter=0;

    }
    public static int i = 0;
    public void run()
    {
        counter++;
        URL url = null;
        try {
            url = new URL("https://events-api.notivize.com/applications/1c9d0629-5e74-45be-9825-7642f88c7ace/event_flows/b0936dd0-25f5-4e28-bb4a-0cfc48b0daef/events");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection)url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            con.setRequestMethod("POST");
        } catch (ProtocolException e) {
            e.printStackTrace();
        }
        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.setDoOutput(true);
        String jsonInputString = "{\"email\": \"adam0shafi@gmail.com\",\n" +
                "    \"sub-frequency\": \"" +frequency +"\",\n" +
                "    \"sub-frequency-type\": \"" +frequencyType +"\",\n" +
                "    \"sub-name\": \"" +subscriptionName +"\",\n" +
                "    \"sub-price\": \""+ "$"+price +"\",\n" +
                "    \"test\": \""+test+"\","+
                "    \"user_sub_id\": \""+counter+"\"\n" +
                "}";
        try(OutputStream os = con.getOutputStream()) {
            byte[] input = jsonInputString.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println(response.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d("NotivizeManager",("Timer ran " + subscriptionName+  ++i));
    }
}

public class NotivizeManager
{
    private Timer timer = new Timer();
    private static int frequency;
    private int f;
    private String subscriptionName;
    private String frequencyType;
    private double price;
    public NotivizeManager (int frequency, String frequencyType, String name, double price) {
        this.frequency=frequency;
        this.f = frequency*1000;
        subscriptionName=name;
        this.price =price;
        this.frequencyType=frequencyType;
        if(this.frequencyType.equals("Day(s)"))
            this.f*=1;
        else if (this.frequencyType.equals("Week(s)"))
            this.f*=7;
        else if(this.frequencyType.equals("Month(s)"))
            this.f*=30;
        else
            this.f*=365;
        createTimer();
    }
    public void createTimer()
    {


        TimerTask task = new Helper(subscriptionName, frequency, frequencyType,price);

        timer.schedule(task, f, f);

    }
    public void killTimer(){
        timer.cancel();
    }
}