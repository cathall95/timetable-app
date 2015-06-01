package com.example.cathal.ultimetable;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check for existing timetable
        File savedTimetable = new File("ULTimetable");
        if(savedTimetable.exists()) {
            Intent goToTimetable = new Intent(MainActivity.this, DisplayTimetable.class);
            MainActivity.this.startActivity(goToTimetable);
        }

        final CheckWifi cd = new CheckWifi(getApplicationContext());

        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);
        EditText txtID;

        btnSubmit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText txtID = (EditText) findViewById(R.id.txtID);
                String ID = (txtID.getText()).toString();
                String regexID = "[0-9]{8}";

                boolean hasInternet = cd.isConnectingToInternet(); // Checks for internet connection

                if (ID.matches(regexID) && hasInternet) {
                    Toast.makeText(getApplicationContext(), "Loading timetable...", Toast.LENGTH_SHORT).show();

                    GetTimetable scraper = new GetTimetable(ID); // Scrapes UL Timetable website
                    String textToPrint = scraper.getTextOutput(); // Get formatted timetable as a string

                    printToFile(textToPrint); // Print formatted timetable to private file
                    txtID.setText(""); // Clear Student ID text field

                    Intent goToTimetable = new Intent(MainActivity.this, DisplayTimetable.class);
                    MainActivity.this.startActivity(goToTimetable);
                }

                // Student ID in wrong format
                else if (!ID.matches(regexID)) {
                    Toast.makeText(getApplicationContext(), "Invalid Student ID", Toast.LENGTH_SHORT).show();
                }

                // No internet access
                else {
                    Toast.makeText(getApplicationContext(), "No Internet Access", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void printToFile(String text) {
        String fileName = "ULTimetable";
        FileOutputStream outputStream = null;
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
