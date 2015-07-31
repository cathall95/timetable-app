package com.example.cathal.ultimetable;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.File;

public class MainActivity extends Activity {
    SQLiteDatabase mydatabase;
    boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydatabase = this.openOrCreateDatabase("characters", android.content.Context.MODE_PRIVATE, null);
        mydatabase.execSQL("CREATE TABLE IF NOT EXISTS timetables(id VARCHAR PRIMARY KEY,timetable VARCHAR);");
        // Check for existing timetable
        File savedTimetable = new File("ULTimetable");
        if(savedTimetable.exists()) {
            Intent goToTimetable = new Intent(MainActivity.this, DisplayTimetable.class);
            MainActivity.this.startActivity(goToTimetable);
        }
        Button btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                EditText txtID = (EditText) findViewById(R.id.txtID);
                String ID = (txtID.getText()).toString();
                String regexID = "[0-9]{8}";
                final Cursor cursor;
                cursor = mydatabase.rawQuery("Select count(*) from timetables where id='" + ID + "';",null);
                cursor.moveToFirst();
                    if(cursor.getInt(0) == 1) {
                        valid = true;
                    }
                cursor.close();
                boolean hasInternet = false;
                if(!valid) {
                    final CheckWifi cd = new CheckWifi(getApplicationContext());
                    hasInternet = cd.isConnectingToInternet(); // Checks for internet connection
                }

                if (ID.matches(regexID) && (hasInternet || valid)) {
                    Toast.makeText(getApplicationContext(), "Loading timetable...", Toast.LENGTH_SHORT).show();
                    String exists = "0";
                    if(valid )
                        exists = "1";
                    txtID.setText(""); // Clear Student ID text field
                    Intent goToTimetable = new Intent(MainActivity.this, DisplayTimetable.class);
                    goToTimetable.putExtra("id", ID);
                    goToTimetable.putExtra("exists", exists);
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
}
