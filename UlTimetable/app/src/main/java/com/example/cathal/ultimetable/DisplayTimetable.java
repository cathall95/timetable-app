package com.example.cathal.ultimetable;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

/**
 * Created by Cathal on 28/05/2015.
 */
public class DisplayTimetable extends ActionBarActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timetabledisplay);

        try {
            String regex = "[-]";
            String line;
            int x,y;
            x = 0;
            y = 0;
            String split[] = new String[5];
            String[][] clTime = new String[5][9];

            FileInputStream in = openFileInput("ULTimetable");
            InputStreamReader inputStreamReader = new InputStreamReader(in);
            BufferedReader BufferedReader = new BufferedReader(inputStreamReader);
            while((line = BufferedReader.readLine()) != null) {

                split = line.split(regex);
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

            }
            //9:00 to 10:00
            if (clTime[0][0] != null) {
                TextView M9 = (TextView) findViewById(R.id.M9);
                M9.setText(clTime[0][0]);
            }if (clTime[1][0] != null) {
                TextView Tue9 = (TextView) findViewById(R.id.Tue9);
                Tue9.setText(clTime[1][0]);
            }if (clTime[2][0] != null) {
                TextView W9 = (TextView) findViewById(R.id.W9);
                W9.setText(clTime[3][0]);
            }if (clTime[3][0] != null) {
                TextView Thur9 = (TextView) findViewById(R.id.Thur9);
                Thur9.setText(clTime[3][0]);
            }if (clTime[4][0] != null) {
                TextView F9 = (TextView) findViewById(R.id.F9);
                F9.setText(clTime[4][0]);
            }
            //10:00 to 11:00
            if (clTime[0][1] != null) {
                TextView M10 = (TextView) findViewById(R.id.M10);
                M10.setText(clTime[0][1]);
            }if (clTime[1][1] != null) {
                TextView Tue10 = (TextView) findViewById(R.id.Tue10);
                Tue10.setText(clTime[1][1]);
            }if (clTime[2][1] != null) {
                TextView W10 = (TextView) findViewById(R.id.W10);
                W10.setText(clTime[2][1]);
            }if (clTime[3][1] != null) {
                TextView Thur10 = (TextView) findViewById(R.id.Thur10);
                Thur10.setText(clTime[3][1]);
            }if (clTime[4][1] != null) {
                TextView F10 = (TextView) findViewById(R.id.F10);
                F10.setText(clTime[4][1]);
            }
            //11:00 to 12:00
            if (clTime[0][2] != null) {
                TextView M11 = (TextView) findViewById(R.id.M11);
                M11.setText(clTime[0][2]);
            }if (clTime[1][2] != null) {
                TextView Tue11 = (TextView) findViewById(R.id.Tue11);
                Tue11.setText(clTime[1][2]);
            }if (clTime[2][2] != null) {
                TextView W11 = (TextView) findViewById(R.id.W11);
                W11.setText(clTime[2][2]);
            }if (clTime[3][2] != null) {
                TextView Thur11 = (TextView) findViewById(R.id.Thur11);
                Thur11.setText(clTime[3][2]);
            }if (clTime[4][2] !=null) {
                TextView F11 = (TextView) findViewById(R.id.F11);
                F11.setText(clTime[4][2]);
            }
            //12:00 to 13:00
            if (clTime[0][3] != null) {
                TextView M12 = (TextView) findViewById(R.id.M12);
                M12.setText(clTime[0][3]);
            }if (clTime[1][3] != null) {
                TextView Tue12 = (TextView) findViewById(R.id.Tue12);
                Tue12.setText(clTime[1][3]);
            }if (clTime[2][3] != null) {
                TextView W12 = (TextView) findViewById(R.id.W12);
                W12.setText(clTime[2][3]);
            }if (clTime[3][3] != null) {
                TextView Thur12 = (TextView) findViewById(R.id.Thur12);
                Thur12.setText(clTime[3][3]);
            }if (clTime[4][3] != null) {
                TextView F12 = (TextView) findViewById(R.id.F12);
                F12.setText(clTime[4][3]);
            }
            //13:00 to 14:00
            if (clTime[0][4]!= null) {
                TextView M13 = (TextView) findViewById(R.id.M13);
                M13.setText(clTime[0][4]);
            }if (clTime[1][4] != null) {
                TextView Tue13 = (TextView) findViewById(R.id.Tue13);
                Tue13.setText(clTime[1][4]);
            }if (clTime[2][4] != null) {
                TextView W13 = (TextView) findViewById(R.id.W13);
                W13.setText(clTime[2][4]);
            }if (clTime[3][4] != null) {
                TextView Thur13 = (TextView) findViewById(R.id.Thur13);
                Thur13.setText(clTime[3][4]);
            }if (clTime[4][4] != null) {
                TextView F13 = (TextView) findViewById(R.id.F13);
                F13.setText(clTime[4][4]);
            }
            //14:00 to 15:00
            if (clTime[0][5] != null) {
                TextView M14 = (TextView) findViewById(R.id.M14);
                M14.setText(clTime[0][5]);
            }if (clTime[1][5] != null) {
                TextView Tue14 = (TextView) findViewById(R.id.Tue14);
                Tue14.setText(clTime[1][5]);
            }if (clTime[2][5] != null) {
                TextView W14 = (TextView) findViewById(R.id.W14);
                W14.setText(clTime[2][5]);
            }if (clTime[3][5] !=null) {
                TextView Thur14 = (TextView) findViewById(R.id.Thur14);
                Thur14.setText(clTime[3][5]);
            }if (clTime[4][5] != null) {
                TextView F14 = (TextView) findViewById(R.id.F14);
                F14.setText(clTime[4][5]);
            }
            //15:00 to 16:00
            if (clTime[0][6] != null) {
                TextView M15 = (TextView) findViewById(R.id.M15);
                M15.setText(clTime[0][6]);
            }if (clTime[1][6] != null) {
                TextView Tue15 = (TextView) findViewById(R.id.Tue15);
                Tue15.setText(clTime[1][6]);
            }if (clTime[2][6] != null) {
                TextView W15 = (TextView) findViewById(R.id.W15);
                W15.setText(clTime[2][6]);
            }if (clTime[3][6] != null) {
                TextView Thur15 = (TextView) findViewById(R.id.Thur15);
                Thur15.setText(clTime[3][6]);
            }if (clTime[4][6] != null) {
                TextView F15 = (TextView) findViewById(R.id.F15);
                F15.setText(clTime[4][6]);
            }
            //16:00 to 17:00
            if (clTime[0][7] != null) {
                TextView M16 = (TextView) findViewById(R.id.M16);
                M16.setText(clTime[0][7]);
            }if (clTime[1][7] !=null) {
                TextView Tue16 = (TextView) findViewById(R.id.Tue16);
                Tue16.setText(clTime[1][7]);
            }if (clTime[2][7] != null) {
                TextView W16 = (TextView) findViewById(R.id.W16);
                W16.setText(clTime[2][7]);
            }if (clTime[3][7] != null) {
                TextView Thur16 = (TextView) findViewById(R.id.Thur16);
                Thur16.setText(clTime[3][7]);
            }if (clTime[4][7] != null) {
                TextView F16 = (TextView) findViewById(R.id.F16);
                F16.setText(clTime[4][7]);
            }
            //17:00 to 18:00
            if (clTime[0][8] != null) {
                TextView M17 = (TextView) findViewById(R.id.M17);
                M17.setText(clTime[0][8]);
            }if (clTime[1][8] != null) {
                TextView Tue17 = (TextView) findViewById(R.id.Tue17);
                Tue17.setText(clTime[1][8]);
            }if (clTime[2][8] != null) {
                TextView W17 = (TextView) findViewById(R.id.W17);
                W17.setText(clTime[2][8]);
            }if (clTime[3][8] != null) {
                TextView Thur17 = (TextView) findViewById(R.id.Thur17);
                Thur17.setText(clTime[3][8]);
            }if (clTime[4][8] != null) {
                TextView F17 = (TextView) findViewById(R.id.F17);
                F17.setText(clTime[4][8]);
            }
        }

        catch(Exception e) {

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
