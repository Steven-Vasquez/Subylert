package com.example.subscriptionmanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.view.View;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.os.Bundle;
import android.widget.Toast;

public class AddSubscriptionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Calendar myCalendar = Calendar.getInstance();
    private EditText edittext;
    String[] courses = {"Day(s)", "Week(s)", "Month(s)", "Year(s)"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subscription);
        setUpDatePicker();
// Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        Spinner spin = findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);
        // Create the instance of ArrayAdapter
        // having the list of courses
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, courses);
        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);
    }

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
               /* myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);*/
                myCalendar.set(year, monthOfYear, dayOfMonth);
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
        String format1="MM";
        String format2="0000";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Log.d("MainActivity", format1.format(Integer.toString(myCalendar.get(Calendar.MONTH)))+"/"+format1.format(Integer.toString(myCalendar.get(Calendar.DATE)))+"/"+format2.format(Integer.toString(myCalendar.get(Calendar.YEAR))));
        //edittext.setText(sdf.format(myCalendar.getTime()));
        edittext.setText(
                format1.format(Integer.toString(myCalendar.get(Calendar.MONTH)+1))+"/"+format1.format(Integer.toString(myCalendar.get(Calendar.DATE)))+"/"+format2.format(Integer.toString(myCalendar.get(Calendar.YEAR))));
    }
}