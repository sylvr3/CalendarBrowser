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

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.app.Activity;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class MainActivity extends Activity {
    CalendarView calendarView;
    Button addEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calendarView = (CalendarView) findViewById(R.id.calendar);
        initializeCalendar();
        addEventButton = (Button)findViewById(R.id.addEventButton);
        ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR);
        ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_CALENDAR);

    }

    public void initializeCalendar() {
        calendarView.setOnDateChangeListener(new OnDateChangeListener() {
            // display the selected date as a toast message
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int day) {

                try {
                    Calendar startTime = Calendar.getInstance();
                    startTime.set(year, month, day, 0, 0, 0);
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(year, month, day + 1, 0, 0, 0);
                    Context context = getApplicationContext();
                    Uri content = Uri.parse("content://com.android.calendar/events");
                    String[] vec = new String[]{"calendar_id", "title", "description", "dtstart", "dtend", "allDay", "eventLocation"};
                    String selectionClause = "(dtstart >= ? AND dtend <= ?) OR (dtstart >= ? AND allDay = ?)";
                    String[] selectionsArgs = new String[]{"" + calendarView.getDate(), "" + calendarView.getDate(), "" + calendarView.getDate(), "0"};
                    System.out.println(calendarView.getDate());
                    String[] projection = new String[]{CalendarContract.Events.CALENDAR_ID, CalendarContract.Events.TITLE, CalendarContract.Events.DESCRIPTION, CalendarContract.Events.DTSTART, CalendarContract.Events.DTEND, CalendarContract.Events.ALL_DAY, CalendarContract.Events.EVENT_LOCATION};
                    String selection = "(( " + CalendarContract.Events.DTSTART + " >= " + startTime.getTimeInMillis() + " ) AND ( " + CalendarContract.Events.DTSTART + " <= " + endTime.getTimeInMillis() + " ))";
                    ContentResolver contentResolver = context.getContentResolver();
                    Cursor cursor = getBaseContext().getContentResolver().query(CalendarContract.Events.CONTENT_URI, projection, selection, null, null);

                    if (cursor.moveToLast() && cursor != null) {

                        Toast.makeText(getApplicationContext(), cursor.getString(1) + " on " + (new Date(cursor.getLong(3))).toString(), Toast.LENGTH_LONG).show();

                    }

                    String eventName = cursor.getString(1);
                }
                catch (CursorIndexOutOfBoundsException ce)
                {
                    ce.printStackTrace();
                }

            }






        });
    }



    public void addEvent(View v) {
        Intent newActivityIntent = new Intent(MainActivity.this, AddEventActivity.class);
        startActivity(newActivityIntent);

    }


    public void viewListOfEvents(View v) {
        Intent newActivityIntent = new Intent(MainActivity.this, ListOfEventsActivity.class);
        startActivity(newActivityIntent);

    }

}
