package com.example.subscriptionmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class AddSubscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar myCalendar = Calendar.getInstance();
    private EditText edittext;
    String[] courses = {"Day(s)", "Week(s)", "Month(s)", "Year(s)"};
    EditText subName;
    EditText subPrice;
    EditText subFrequency;
    EditText subCalendar;
    Button btSave;
    private int startMonth;
    private int startDate;
    private int startYear;

    private String format1="MM";
    private String format2="0000";


    List<MainData> dataList = new ArrayList<>();
    RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);
        setUpDatePicker();
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
// Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, courses);
        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);

//        AutoCompleteTextView textView = (AutoCompleteTextView)
//                findViewById(R.id.periodMenuText);
//        textView.setAdapter(ad);

        subName = findViewById(R.id.subscriptionName);
        subPrice = findViewById(R.id.cost);
        subFrequency = findViewById(R.id.frequency);

        btSave = findViewById(R.id.save_add_subscription);


        //Initialize database
        database = RoomDB.getInstance(this);
        //Store database value in data list
        dataList = database.mainDao().getAll();

        //Initialize linear layout manager


    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.period_menu, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle item selection
//        switch (item.getItemId()) {
//            case R.id.periodMenu:
//                newGame();
//                return true;
//            case R.id.help:
//                showHelp();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    // Performing action when ItemSelected
    // from spinner, Overriding onItemSelected method
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        // make toast of name of course
        // which is selected in spinner
        Toast.makeText(getApplicationContext(),
                courses[position],
                Toast.LENGTH_LONG)
                .show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // Auto-generated method stub
    }

    public void onSave(View v) {
        Spinner spin = findViewById(R.id.spinner);
        if(!subName.getText().equals("") &&!subPrice.getText().toString().equals("") &&!subFrequency.getText().toString().equals("") &&myCalendar.getTime()!=null) {
            String sName = subName.getText().toString().trim();
            double sPrice = Double.parseDouble(subPrice.getText().toString().trim());
            int sFrequency = Integer.parseInt(subFrequency.getText().toString().trim());
            String sFrequencyType = spin.getSelectedItem().toString();
            Date sStartDate = myCalendar.getTime();

            //Check condition

            //When text is not empty
            //Initialize main data
            MainData data = new MainData();
            //Set text on main data
            data.setText(sName);
            data.setPrice(sPrice);
            data.setFrequency(sFrequency);
            data.setFrequencyType(sFrequencyType);
            data.setDate(sStartDate);
//                data.setStartMonth(startMonth);
//                data.setStartDate(startDate);
//                data.setStartYear(startYear);
            Log.d("AddSubscriptionActivity","month:" + (data.getStartMonth())+" date: " + data.getStartDate()+ " year: "+data.getStartYear());

            //Insert text in database
            database.mainDao().insert(data);
            //clear edit text
            subName.setText("");
            subPrice.setText("");
            subFrequency.setText("");

            //Notify when data is inserted
            dataList.clear();
            dataList.addAll(database.mainDao().getAll());



        }
        Intent intent = new Intent(this, MainAppActivity.class);
        this.startActivity(intent);
    }


    public void setUpDatePicker() {
        edittext = (EditText) findViewById(R.id.starting_date);
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
//                startMonth = monthOfYear+1;
//                startDate = dayOfMonth;
//                startYear = year;
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //myCalendar.set(year, monthOfYear, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(AddSubscriptionActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void updateLabel() {
        String myFormat = "MM/DD/YYYY"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        String test = format1.format(Integer.toString(myCalendar.get(Calendar.MONTH)+1))+"/"
                +format1.format(Integer.toString(myCalendar.get(Calendar.DATE)))+"/"
                +format2.format(Integer.toString(myCalendar.get(Calendar.YEAR)));
        Log.d("MainActivity", test);
        //edittext.setText(sdf.format(myCalendar.getTime()));

        edittext.setText(
                test);
    }

}