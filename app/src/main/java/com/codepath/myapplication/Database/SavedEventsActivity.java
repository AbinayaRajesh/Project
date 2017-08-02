/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codepath.myapplication.Database;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Event.EventAdapter;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.R;

import java.util.ArrayList;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class SavedEventsActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private EventDbHelper mDbHelper;

    EventAdapter adapter;
    RecyclerView rvEvents;
    ArrayList<Event> aEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_favourites);
        setTitle("Saved Events");
        aEvent = new ArrayList<>();
        adapter = new EventAdapter(aEvent);
        adapter.notifyDataSetChanged();

        rvEvents= (RecyclerView) findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);


        // To access our database, we instantiate our subclass of SQLiteOpenHelper
        // and pass the context, which is the current activity.
        mDbHelper = new EventDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();

    }

    /**
     * Temporary helper method to display information in the onscreen TextView about the state of
     * the pets database.
     */
    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                EventEntry._ID,
                EventEntry.COLUMN_EVENT_NAME,
                EventEntry.COLUMN_EVENT_DESCRIPTION,
                EventEntry.COLUMN_EVENT_URL,
                EventEntry.COLUMN_EVENT_VENUE,
                EventEntry.COLUMN_EVENT_START_TIME,
                EventEntry.COLUMN_EVENT_STOP_TIME,
                EventEntry.COLUMN_EVENT_UNIQUE_KEY};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                EventEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(EventEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_NAME);
            int descriptionColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_DESCRIPTION);
            int urlColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_URL);
            int venueColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_VENUE);
            int startColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_START_TIME);
            int stopColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_STOP_TIME);
            int keyColumnIndex = cursor.getColumnIndex(EventEntry.COLUMN_EVENT_UNIQUE_KEY);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentDescription = cursor.getString(descriptionColumnIndex);
                String currentUrl = cursor.getString(urlColumnIndex);
                String currentVenue = cursor.getString(venueColumnIndex);
                String currentStart = cursor.getString(startColumnIndex);
                String currentStop = cursor.getString(stopColumnIndex);
                int currentKey = cursor.getInt(keyColumnIndex);


                    Event e = Event.consEvent(currentName, currentDescription, currentUrl, currentVenue, currentStart,
                            currentStop, 0, 0, (byte) 0, currentKey);
                    aEvent.add(e);
                    //notify adapter
                    adapter.notifyItemInserted(aEvent.size() - 1);

            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }




}
