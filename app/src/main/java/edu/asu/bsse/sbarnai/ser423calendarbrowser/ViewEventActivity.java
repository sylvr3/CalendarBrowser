package edu.asu.bsse.sbarnai.ser423calendarbrowser;

/**
 * Copyright 2015 Sylvia Barnai.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the “License”);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * <p>
 * http://www.apache.org/license/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an “AS IS” BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Purpose: This activity allows the user to view information about a particular event in their calendar.
 *
 * @author Sylvia Barnai mailto:Sylvia.Barnai@asu.edu
 * @version April 30, 2015
 **/

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
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
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;


public class ViewEventActivity extends ActionBarActivity {

    private EditText eventNameTextView;
    private EditText locationTextView;
    private DatePicker startDate;
    private TimePicker startTime;
    private DatePicker endDate;
    private TimePicker endTime;
    private CheckBox allDayTextView;
    private EditText descriptionTextView;
    private Button saveChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Intent intent = getIntent();
        long eventId = intent.getLongExtra("selectedeventid", 0);

        ContentResolver cr = getContentResolver();

        eventNameTextView = (EditText) findViewById(R.id.eventNameText);
        locationTextView = (EditText) findViewById(R.id.locationText);
        startDate = (DatePicker) findViewById(R.id.datePicker);
        startTime = (TimePicker) findViewById(R.id.timePicker);
        endDate = (DatePicker) findViewById(R.id.datePicker2);
        endTime = (TimePicker) findViewById(R.id.timePicker2);
        allDayTextView = (CheckBox) findViewById(R.id.allDayCheckBox);
        descriptionTextView = (EditText) findViewById(R.id.descriptionText);
        saveChanges = (Button) findViewById(R.id.saveChanges);

        Uri calendarURI = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);

        Cursor eventCursor = cr.query(calendarURI, new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events._ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.EVENT_LOCATION, CalendarContract.Events.ALL_DAY}, null, null, null);

        System.out.println(calendarURI.toString());
        String eventName;
        String location;
        long eventBeginTime;
        long eventEndTime;
        String description;
        String allDay;

        if (eventCursor.moveToLast()) {

            eventName = eventCursor.getString(2);
            description = eventCursor.getString(3);
            eventBeginTime = eventCursor.getLong(4);
            eventEndTime = eventCursor.getLong(5);
            location = eventCursor.getString(6);
            allDay = eventCursor.getString(7);

            System.out.println("eventName: " + eventName + "\n" + " description: " + description + "\n" + " eventBeginTime: " + eventBeginTime + "\n" + " eventEndTime: " + eventEndTime + "/n" + " event location: " + location + "allDay" + allDay);
            eventNameTextView.setText(eventName);
            locationTextView.setText(location);
            descriptionTextView.setText(description);

            Date startDateConv = convertDate(eventBeginTime, startDate, startTime);
            Date endDateConv = convertDate(eventEndTime, endDate, endTime);

            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            //return new Date();
            Toast.makeText(getApplicationContext(), "The new start date time is : " + sdf.format(startDateConv), Toast.LENGTH_LONG).show();
            Toast.makeText(getApplicationContext(), "The new end date time is : " + sdf.format(endDateConv), Toast.LENGTH_LONG).show();




            //  int offset = cal.getTimeZone().getOffset(cal.getTimeInMillis());
            //  Date da = new Date(eventBeginTime-(long)offset);
            //   String dateString = formatter.format(da);
            //String dateString = formatter.format(new Date(eventBeginTime));

            eventCursor.close();
        }

    }

    private Date convertDate(Long time, DatePicker dp, TimePicker tp) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date convertDate  = null;

        String dateString = formatter.format(new Date(time));
        try {
            Date ds = formatter.parse(dateString);
            TimeZoneHandler tzh = new TimeZoneHandler();
            convertDate = tzh.changeUTCToLOCAL(ds);

            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(convertDate.getTime());
            dp.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
            tp.setCurrentHour(cal.get(Calendar.HOUR_OF_DAY));
            tp.setCurrentMinute(cal.get(Calendar.MINUTE));


        } catch (ParseException pe)


        {


            pe.printStackTrace();


        }
        return convertDate;

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_event, menu);
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

    public void backToMainActivity(View v) {
        Intent newActivityIntent = new Intent(ViewEventActivity.this, MainActivity.class);
        startActivity(newActivityIntent);
    }

    private static final String DATE_TIME_FORMAT = "yyyy MMM dd, HH:mm:ss";

    // Same as createEvent, except you are modifying existing event
    public void saveChanges(View v) {

        Intent intent = getIntent();
        long eventId = intent.getLongExtra("selectedeventid", 0);

        Calendar beginCal = Calendar.getInstance();

        Calendar endCal = Calendar.getInstance();

        ContentValues event = new ContentValues();

        event.put(CalendarContract.Events.CALENDAR_ID, 18);

        event.put("title", String.valueOf(eventNameTextView.getText()));

        event.put("description", String.valueOf(descriptionTextView.getText()));
        event.put("eventLocation", String.valueOf(locationTextView.getText()));

        if (allDayTextView.isChecked() == false) {
            beginCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), startTime.getCurrentHour(), startTime.getCurrentMinute());
            long beginTime = beginCal.getTimeInMillis();
            event.put("dtstart", beginTime);

            endCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), endTime.getCurrentHour(), endTime.getCurrentMinute());
            long endingTime = endCal.getTimeInMillis();
            event.put("dtend", endingTime);
            event.put(CalendarContract.Events.ALL_DAY, 0);
        } else {

            startTime.setCurrentHour(0);
            startTime.setCurrentMinute(0);
            endTime.setCurrentHour(0);
            endTime.setCurrentMinute(0);
            beginCal.set(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth(), 0, 0);
            endCal.set(endDate.getYear(), endDate.getMonth(), endDate.getDayOfMonth(), 0, 0);
            long beginTime = beginCal.getTimeInMillis();
            long endingTime = endCal.getTimeInMillis();
            event.put("dtstart", beginTime);
            event.put("dtend", endingTime);
            event.put(CalendarContract.Events.ALL_DAY, 1);

        }

        event.put("eventTimezone", TimeZone.getDefault().getID());


        Uri updateURI = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        int rows = getContentResolver().update(updateURI, event, null, null);

        Intent newActivityIntent = new Intent(ViewEventActivity.this, MainActivity.class);
        startActivity(newActivityIntent);

    }

    public void deleteEvent(View v) {
        Intent intent = getIntent();
        long eventId = intent.getLongExtra("selectedeventid", 0);
        Uri deleteUri = ContentUris.withAppendedId(CalendarContract.Events.CONTENT_URI, eventId);
        int rows = getContentResolver().delete(deleteUri, null, null);
        Intent newActivityIntent = new Intent(ViewEventActivity.this, MainActivity.class);
        startActivity(newActivityIntent);

    }
}