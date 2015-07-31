
package com.example.cathal.ultimetable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetTimetable implements Runnable {

    private String studentID;
    private String textOutput;

    public GetTimetable(String studentID) {
        this.studentID = studentID;
        Thread myThread = new Thread(this);
        myThread.start();
        try {
            myThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            Document doc = Jsoup.connect("http://www.timetable.ul.ie/tt2.asp").data("T1", studentID).post();
            Elements paragraphs = doc.select("p:has(font[size=1]),td[valign=top]");

            int daySepCount = -1;
            String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
            String output = "";

            for(Element src : paragraphs) {
                if(src.tagName().contains("td")) {
                    daySepCount++;
                }
                else if(daySepCount < 5){
                    String scratch = src.text();
                    int endOfLocation = scratch.length();
                    if(scratch.contains("Wks:"))
                        endOfLocation = scratch.indexOf("Wks:");

                    String toAdd = days[daySepCount] + "-" + scratch.substring(0,5)+ "-" + scratch.substring(14,20) + "-" + scratch.substring(23,26) + "-" + scratch.substring(31,endOfLocation).trim() + "\n";
                    output += toAdd;

                    // Will add 1 to start hour if a double period
                    if(checkIfDouble(scratch)) {
                        toAdd = days[daySepCount] + "-" + ((Integer.parseInt(scratch.substring(0,2))) + 1) + ":00" + "-" + scratch.substring(14,20) + "-" + scratch.substring(23,26) + "-" + scratch.substring(31,endOfLocation).trim() + "\n";
                        output += toAdd;
                    }
                }
            }
            this.textOutput = output;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfDouble(String text) {
        int firstHour = Integer.parseInt(text.substring(0, 2));
        int secondHour = Integer.parseInt(text.substring(8,10));

        if ((secondHour - firstHour) > 1)
            return true;
        else
            return false;
    }

    public String getTextOutput() {
        return this.textOutput;
    }
}
