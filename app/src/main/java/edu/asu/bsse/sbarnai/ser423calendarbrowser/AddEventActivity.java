package edu.asu.bsse.sbarnai.ser423calendarbrowser;

/**
 * Copyright 2015 Sylvia Barnai.
 *
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/license/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Purpose: The main activity for calendar application. It allows the user to add a new event and view existing events in their calendar.
 *
 * @author Sylvia Barnai mailto:Sylvia.Barnai@asu.edu
 * @version April 30, 2015
 **/

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class AddEventActivity extends ActionBarActivity {

    private Button createNewEvent;
    private EditText eventName;
    private EditText location;
    private DatePicker startDate;
    private TimePicker startTime;
    private DatePicker endDate;
    private TimePicker endTime;
    private CheckBox allDay;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);
        createNewEvent = (Button) findViewById(R.id.createNewEvent);
        eventName = (EditText) findViewById(R.id.eventNameText);
        location = (EditText) findViewById(R.id.locationText);
        startDate = (DatePicker) findViewById(R.id.datePicker);
        startTime = (TimePicker) findViewById(R.id.timePicker);
        endDate = (DatePicker) findViewById(R.id.datePicker2);
        endTime = (TimePicker) findViewById(R.id.timePicker2);
        allDay = (CheckBox)findViewById(R.id.allDayCheckBox);
        description = (EditText) findViewById(R.id.descriptionText);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event, menu);
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

    // todo: convert time to GMT
    public void createNewEvent(View v) {

        Calendar beginCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        ContentValues event = new ContentValues();
        event.put(CalendarContract.Events.CALENDAR_ID, 1);
        event.put("title", String.valueOf(eventName.getText()));
        event.put("description", String.valueOf(description.getText()));
        event.put("eventLocation", String.valueOf(location.getText()));

        if (allDay.isChecked()==false) {
            beginCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), startTime.getCurrentHour(), startTime.getCurrentMinute());

            endCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endTime.getCurrentHour(), endTime.getCurrentMinute());
            //long endingTime = endCal.getTimeInMillis();
            //event.put("dtend", String.valueOf(endingTime));

            event.put(CalendarContract.Events.ALL_DAY, 0);
        }

        else {

            startTime.setCurrentHour(0);
            startTime.setCurrentMinute(0);
            endTime.setCurrentHour(0);
            endTime.setCurrentMinute(0);
            beginCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0);
            endCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0);

            // may need to comment out:

            event.put(CalendarContract.Events.ALL_DAY, 1);

        }

        Date d1 = setDate(beginCal, startDate, startTime);
        Date d2 = setDate(endCal, endDate, endTime);

        long beginTime = d1.getTime();
        event.put("dtstart", String.valueOf(beginTime));

        long endingTime = d2.getTime();
        event.put("dtend", String.valueOf(endingTime));

        event.put("eventTimezone", TimeZone.getTimeZone("UTC").getID());

        Uri uri = getContentResolver().insert(CalendarContract.Events.CONTENT_URI, event);

        Intent newActivityIntent = new Intent(AddEventActivity.this, MainActivity.class);
        startActivity(newActivityIntent);
        //newActivityIntent.setType("vnd.android.cursor.item/event");

        // long eventID = Long.parseLong(uri.getLastPathSegment());


    }

    private Date setDate(Calendar cal, DatePicker dp, TimePicker tp) {

        cal.set(Calendar.YEAR, dp.getYear());
        cal.set(Calendar.MONTH, dp.getMonth());
        cal.set(Calendar.DATE, dp.getDayOfMonth());
        cal.set(Calendar.HOUR_OF_DAY, tp.getCurrentHour());
        cal.set(Calendar.MINUTE, tp.getCurrentMinute());
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
/*
DateFormat df = DateFormat.getTimeInstance();
df.setTimeZone(TimeZone.getTimeZone("gmt"));
String gmtTime = df.format(new Date());
Date newDate = df.parse(gmtTime);

    cal.setTimeZone(TimeZone.getTimeZone("UTC"));

    Date date = cal.getTime();
    cal.getTimeZone();
    */

        Date date = cal.getTime();
        TimeZoneHandler tzh = new TimeZoneHandler();
        String date1 = tzh.dateToUTC(date);
        return new Date();



    }

}
