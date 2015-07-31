package com.example.cathal.ultimetable;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

/**
 * Created by Cathal on 28/05/2015.
 */
public class DisplayTimetable extends ActionBarActivity{
    SQLiteDatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetabledisplay);
        mydatabase = this.openOrCreateDatabase("characters", android.content.Context.MODE_PRIVATE, null);
        String ID = "";
        String exists = "";
            String[] colours = {"#ff0000", "#0000ff", "#008000", "#ffff00", "#ffa500"};
            String[] modules = new String[5];
            Bundle extras = getIntent().getExtras();
            if(extras!=null)

            {
                ID = extras.getString("id");
                exists = extras.getString("exists");
            }

            String textToPrint = "";
            try
            {
                if (exists.equals("0")) {
                    GetTimetable scraper = new GetTimetable(ID); // Scrapes UL Timetable website
                    textToPrint = scraper.getTextOutput(); // Get formatted timetable as a string
                    mydatabase.execSQL("INSERT INTO timetables VALUES('" + ID + "','" + textToPrint + "');");
                } else {
                    final Cursor cursor;
                    cursor = mydatabase.rawQuery("Select timetable from timetables where id='" + ID + "';", null);
                    cursor.moveToFirst();
                    if (cursor == null) {
                        return;
                    } else {
                        while (!cursor.isAfterLast()) {
                            textToPrint = cursor.getString(0);
                            cursor.moveToNext();
                        }
                    }
                    cursor.close();
                }
                final String ids = ID;
                Button reset = (Button) findViewById(R.id.reset);

                reset.setOnClickListener(new Button.OnClickListener() {
                    public void onClick(View v) {
                        boolean hasInternet = false;
                        final CheckWifi cd = new CheckWifi(getApplicationContext());
                        hasInternet = cd.isConnectingToInternet();
                        if (hasInternet) {
                            mydatabase.delete("timetables", "id='" + ids + "';",null);
                            GetTimetable scraper = new GetTimetable(ids); // Scrapes UL Timetable website
                            String textout = scraper.getTextOutput(); // Get formatted timetable as a string
                            mydatabase.execSQL("INSERT INTO timetables VALUES('" + ids + "','" + textout + "');");
                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "You need internet access to refresh your timetable", Toast.LENGTH_SHORT).show();
                    }
                });
                String regex = "[-]";
                String line;
                int x, y;
                x = 0;
                y = 0;
                int modulecount = 0;
                String colours2[][] = new String[5][9];
                String split[] = new String[5];
                String[][] clTime = new String[5][9];
                BufferedReader BufferedReader = new BufferedReader(new StringReader(textToPrint));
                while ((line = BufferedReader.readLine()) != null) {
                    boolean duplicate = false;
                    split = line.split(regex);
                    for (int i = 0; i < 5; i++) {
                        if (split[2].equals(modules[i])) {
                            duplicate = true;
                        }
                    }
                    if (duplicate == false) {
                        modules[modulecount] = split[2];
                        modulecount++;
                    }
                    String color = "";
                    if (modules[0].equals(split[2]))
                        color = colours[0];
                    else if (modules[1].equals(split[2]))
                        color = colours[1];
                    else if (modules[2].equals(split[2]))
                        color = colours[2];
                    else if (modules[3].equals(split[2]))
                        color = colours[3];
                    else if (modules[4].equals(split[2]))
                        color = colours[4];
                    switch (split[0]) {
                        case "Monday":
                            x = 0;
                            break;
                        case "Tuesday":
                            x = 1;
                            break;
                        case "Wednesday":
                            x = 2;
                            break;
                        case "Thursday":
                            x = 3;
                            break;
                        case "Friday":
                            x = 4;
                            break;
                    }
                    switch (split[1]) {
                        case "9:00":
                            y = 0;
                            break;
                        case "10:00":
                            y = 1;
                            break;
                        case "11:00":
                            y = 2;
                            break;
                        case "12:00":
                            y = 3;
                            break;
                        case "13:00":
                            y = 4;
                            break;
                        case "14:00":
                            y = 5;
                            break;
                        case "15:00":
                            y = 6;
                            break;
                        case "16:00":
                            y = 7;
                            break;
                        case "17:00":
                            y = 8;
                            break;

                    }
                    clTime[x][y] = split[2] + "\n" + split[3] + " " + split[4];
                    colours2[x][y] = color;


                }
                //9:00 to 10:00
                if (clTime[0][0] != null) {
                    TextView M9 = (TextView) findViewById(R.id.M9);
                    M9.setText(clTime[0][0]);
                    M9.setBackgroundColor(Color.parseColor(colours2[0][0]));
                }
                if (clTime[1][0] != null) {
                    TextView Tue9 = (TextView) findViewById(R.id.Tue9);
                    Tue9.setText(clTime[1][0]);
                    Tue9.setBackgroundColor(Color.parseColor(colours2[1][0]));
                }
                if (clTime[2][0] != null) {
                    TextView W9 = (TextView) findViewById(R.id.W9);
                    W9.setText(clTime[2][0]);
                    W9.setBackgroundColor(Color.parseColor(colours2[2][0]));
                }
                if (clTime[3][0] != null) {
                    TextView Thur9 = (TextView) findViewById(R.id.Thur9);
                    Thur9.setText(clTime[3][0]);
                    Thur9.setBackgroundColor(Color.parseColor(colours2[3][0]));
                }
                if (clTime[4][0] != null) {
                    TextView F9 = (TextView) findViewById(R.id.F9);
                    F9.setText(clTime[4][0]);
                    F9.setBackgroundColor(Color.parseColor(colours2[4][0]));
                }
                //10:00 to 11:00
                if (clTime[0][1] != null) {
                    TextView M10 = (TextView) findViewById(R.id.M10);
                    M10.setText(clTime[0][1]);
                    M10.setBackgroundColor(Color.parseColor(colours2[0][1]));
                }
                if (clTime[1][1] != null) {
                    TextView Tue10 = (TextView) findViewById(R.id.Tue10);
                    Tue10.setText(clTime[1][1]);
                    Tue10.setBackgroundColor(Color.parseColor(colours2[1][1]));
                }
                if (clTime[2][1] != null) {
                    TextView W10 = (TextView) findViewById(R.id.W10);
                    W10.setText(clTime[2][1]);
                    W10.setBackgroundColor(Color.parseColor(colours2[2][1]));
                }
                if (clTime[3][1] != null) {
                    TextView Thur10 = (TextView) findViewById(R.id.Thur10);
                    Thur10.setText(clTime[3][1]);
                    Thur10.setBackgroundColor(Color.parseColor(colours2[3][1]));
                }
                if (clTime[4][1] != null) {
                    TextView F10 = (TextView) findViewById(R.id.F10);
                    F10.setText(clTime[4][1]);
                    F10.setBackgroundColor(Color.parseColor(colours2[4][1]));
                }
                //11:00 to 12:00
                if (clTime[0][2] != null) {
                    TextView M11 = (TextView) findViewById(R.id.M11);
                    M11.setText(clTime[0][2]);
                    M11.setBackgroundColor(Color.parseColor(colours2[0][2]));
                }
                if (clTime[1][2] != null) {
                    TextView Tue11 = (TextView) findViewById(R.id.Tue11);
                    Tue11.setText(clTime[1][2]);
                    Tue11.setBackgroundColor(Color.parseColor(colours2[1][2]));
                }
                if (clTime[2][2] != null) {
                    TextView W11 = (TextView) findViewById(R.id.W11);
                    W11.setText(clTime[2][2]);
                    W11.setBackgroundColor(Color.parseColor(colours2[2][2]));
                }
                if (clTime[3][2] != null) {
                    TextView Thur11 = (TextView) findViewById(R.id.Thur11);
                    Thur11.setText(clTime[3][2]);
                    Thur11.setBackgroundColor(Color.parseColor(colours2[3][2]));
                }
                if (clTime[4][2] != null) {
                    TextView F11 = (TextView) findViewById(R.id.F11);
                    F11.setText(clTime[4][2]);
                    F11.setBackgroundColor(Color.parseColor(colours2[4][2]));
                }
                //12:00 to 13:00
                if (clTime[0][3] != null) {
                    TextView M12 = (TextView) findViewById(R.id.M12);
                    M12.setText(clTime[0][3]);
                    M12.setBackgroundColor(Color.parseColor(colours2[0][3]));
                }
                if (clTime[1][3] != null) {
                    TextView Tue12 = (TextView) findViewById(R.id.Tue12);
                    Tue12.setText(clTime[1][3]);
                    Tue12.setBackgroundColor(Color.parseColor(colours2[1][3]));
                }
                if (clTime[2][3] != null) {
                    TextView W12 = (TextView) findViewById(R.id.W12);
                    W12.setText(clTime[2][3]);
                    W12.setBackgroundColor(Color.parseColor(colours2[2][3]));
                }
                if (clTime[3][3] != null) {
                    TextView Thur12 = (TextView) findViewById(R.id.Thur12);
                    Thur12.setText(clTime[3][3]);
                    Thur12.setBackgroundColor(Color.parseColor(colours2[3][3]));
                }
                if (clTime[4][3] != null) {
                    TextView F12 = (TextView) findViewById(R.id.F12);
                    F12.setText(clTime[4][3]);
                    F12.setBackgroundColor(Color.parseColor(colours2[4][3]));
                }
                //13:00 to 14:00
                if (clTime[0][4] != null) {
                    TextView M13 = (TextView) findViewById(R.id.M13);
                    M13.setText(clTime[0][4]);
                    M13.setBackgroundColor(Color.parseColor(colours2[0][4]));
                }
                if (clTime[1][4] != null) {
                    TextView Tue13 = (TextView) findViewById(R.id.Tue13);
                    Tue13.setText(clTime[1][4]);
                    Tue13.setBackgroundColor(Color.parseColor(colours2[1][4]));
                }
                if (clTime[2][4] != null) {
                    TextView W13 = (TextView) findViewById(R.id.W13);
                    W13.setText(clTime[2][4]);
                    W13.setBackgroundColor(Color.parseColor(colours2[2][4]));
                }
                if (clTime[3][4] != null) {
                    TextView Thur13 = (TextView) findViewById(R.id.Thur13);
                    Thur13.setText(clTime[3][4]);
                    Thur13.setBackgroundColor(Color.parseColor(colours2[3][4]));
                }
                if (clTime[4][4] != null) {
                    TextView F13 = (TextView) findViewById(R.id.F13);
                    F13.setText(clTime[4][4]);
                    F13.setBackgroundColor(Color.parseColor(colours2[4][4]));
                }
                //14:00 to 15:00
                if (clTime[0][5] != null) {
                    TextView M14 = (TextView) findViewById(R.id.M14);
                    M14.setText(clTime[0][5]);
                    M14.setBackgroundColor(Color.parseColor(colours2[0][5]));
                }
                if (clTime[1][5] != null) {
                    TextView Tue14 = (TextView) findViewById(R.id.Tue14);
                    Tue14.setText(clTime[1][5]);
                    Tue14.setBackgroundColor(Color.parseColor(colours2[1][5]));
                }
                if (clTime[2][5] != null) {
                    TextView W14 = (TextView) findViewById(R.id.W14);
                    W14.setText(clTime[2][5]);
                    W14.setBackgroundColor(Color.parseColor(colours2[2][5]));
                }
                if (clTime[3][5] != null) {
                    TextView Thur14 = (TextView) findViewById(R.id.Thur14);
                    Thur14.setText(clTime[3][5]);
                    Thur14.setBackgroundColor(Color.parseColor(colours2[3][5]));
                }
                if (clTime[4][5] != null) {
                    TextView F14 = (TextView) findViewById(R.id.F14);
                    F14.setText(clTime[4][5]);
                    F14.setBackgroundColor(Color.parseColor(colours2[4][5]));
                }
                //15:00 to 16:00
                if (clTime[0][6] != null) {
                    TextView M15 = (TextView) findViewById(R.id.M15);
                    M15.setText(clTime[0][6]);
                    M15.setBackgroundColor(Color.parseColor(colours2[0][6]));
                }
                if (clTime[1][6] != null) {
                    TextView Tue15 = (TextView) findViewById(R.id.Tue15);
                    Tue15.setText(clTime[1][6]);
                    Tue15.setBackgroundColor(Color.parseColor(colours2[1][6]));
                }
                if (clTime[2][6] != null) {
                    TextView W15 = (TextView) findViewById(R.id.W15);
                    W15.setText(clTime[2][6]);
                    W15.setBackgroundColor(Color.parseColor(colours2[2][6]));
                }
                if (clTime[3][6] != null) {
                    TextView Thur15 = (TextView) findViewById(R.id.Thur15);
                    Thur15.setText(clTime[3][6]);
                    Thur15.setBackgroundColor(Color.parseColor(colours2[3][6]));
                }
                if (clTime[4][6] != null) {
                    TextView F15 = (TextView) findViewById(R.id.F15);
                    F15.setText(clTime[4][6]);
                    F15.setBackgroundColor(Color.parseColor(colours2[4][6]));
                }
                //16:00 to 17:00
                if (clTime[0][7] != null) {
                    TextView M16 = (TextView) findViewById(R.id.M16);
                    M16.setText(clTime[0][7]);
                    M16.setBackgroundColor(Color.parseColor(colours2[0][7]));
                }
                if (clTime[1][7] != null) {
                    TextView Tue16 = (TextView) findViewById(R.id.Tue16);
                    Tue16.setText(clTime[1][7]);
                    Tue16.setBackgroundColor(Color.parseColor(colours2[1][7]));
                }
                if (clTime[2][7] != null) {
                    TextView W16 = (TextView) findViewById(R.id.W16);
                    W16.setText(clTime[2][7]);
                    W16.setBackgroundColor(Color.parseColor(colours2[2][7]));
                }
                if (clTime[3][7] != null) {
                    TextView Thur16 = (TextView) findViewById(R.id.Thur16);
                    Thur16.setText(clTime[3][7]);
                    Thur16.setBackgroundColor(Color.parseColor(colours2[3][7]));
                }
                if (clTime[4][7] != null) {
                    TextView F16 = (TextView) findViewById(R.id.F16);
                    F16.setText(clTime[4][7]);
                    F16.setBackgroundColor(Color.parseColor(colours2[4][7]));
                }
                //17:00 to 18:00
                if (clTime[0][8] != null) {
                    TextView M17 = (TextView) findViewById(R.id.M17);
                    M17.setText(clTime[0][8]);
                    M17.setBackgroundColor(Color.parseColor(colours2[0][8]));
                }
                if (clTime[1][8] != null) {
                    TextView Tue17 = (TextView) findViewById(R.id.Tue17);
                    Tue17.setText(clTime[1][8]);
                    Tue17.setBackgroundColor(Color.parseColor(colours2[1][8]));
                }
                if (clTime[2][8] != null) {
                    TextView W17 = (TextView) findViewById(R.id.W17);
                    W17.setText(clTime[2][8]);
                    W17.setBackgroundColor(Color.parseColor(colours2[2][8]));
                }
                if (clTime[3][8] != null) {
                    TextView Thur17 = (TextView) findViewById(R.id.Thur17);
                    Thur17.setText(clTime[3][8]);
                    Thur17.setBackgroundColor(Color.parseColor(colours2[3][8]));
                }
                if (clTime[4][8] != null) {
                    TextView F17 = (TextView) findViewById(R.id.F17);
                    F17.setText(clTime[4][8]);
                    F17.setBackgroundColor(Color.parseColor(colours2[4][8]));
                }
            }

            catch(
            Exception e
            )

            {

            }
        }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.timetabledisplaymenu, menu);
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
            // Delete ULTimetable file
            deleteFile("ULTimetable");
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
