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
 * Purpose: This activity allows the user to view a list of events that fall on a particular day in the calendar.
 *
 * @author Sylvia Barnai mailto:Sylvia.Barnai@asu.edu
 * @version April 30, 2015
 **/

import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class ListOfEventsActivity extends Activity {

    private ListView listView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_events);

        listView1 = (ListView)findViewById(R.id.list);
        ArrayList<CalendarEvent> eventsList = displayEvents();
        final ArrayAdapter<CalendarEvent> eventAdapter = new EventAdapter(this,
                R.layout.listview_item_row, eventsList);

        listView1.setAdapter(eventAdapter);


        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                CalendarEvent calendarEvent = eventAdapter.getItem(position);
                Intent newActivityIntent = new Intent(ListOfEventsActivity.this, ViewEventActivity.class);
                newActivityIntent.putExtra("selectedeventid",calendarEvent.getEventId());
                startActivity(newActivityIntent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_of_events, menu);
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


    public ArrayList<CalendarEvent> displayEvents() {
        ArrayList<String> eventList = new ArrayList<String>();
        Uri calendarURI = CalendarContract.Events.CONTENT_URI;

        Uri.Builder builder = Uri.parse(
                "content://com.android.calendar/instances/when")
                .buildUpon();
        ContentUris.appendId(builder, Long.MIN_VALUE);
        ContentUris.appendId(builder, Long.MAX_VALUE);

        String selection = CalendarContract.Events.CALENDAR_ID + " = ?";
        String[] selectionArgs = new String[]{""+CalendarContract.Events.CALENDAR_ID};


        Cursor cursor = getContentResolver().query(builder.build(),new String[]{"event_id", "title"}, null,null, CalendarContract.Events.TITLE+" ASC");

        ArrayList<CalendarEvent> listOfEvents = new ArrayList<CalendarEvent>();
        if (cursor != null)

        {
            cursor.moveToFirst();

            while (cursor.moveToNext()) {
                String eventName = cursor.getString(1);
                CalendarEvent calendarEvent = new CalendarEvent(cursor.getLong(0), cursor.getString(1));
                listOfEvents.add(calendarEvent);

            }
        }
        return listOfEvents;
    }


}

