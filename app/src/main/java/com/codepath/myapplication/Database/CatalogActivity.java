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

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Event.EventAdapter;
import com.codepath.myapplication.R;

import java.util.ArrayList;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class CatalogActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private EventDbHelper mDbHelper;

    EventAdapter adapter;
    RecyclerView rvEvents;
    ArrayList<Event> aEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        aEvent = new ArrayList<>();
        adapter = new EventAdapter(aEvent);

        rvEvents= (RecyclerView) findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);


        // Setup FAB to open EditorActivity
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

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

        TextView displayView = (TextView) findViewById(R.id.text_view_pet);

        try {
            // Create a header in the Text View that looks like this:
            //
            // The pets table contains <number of rows in Cursor> pets.
            // _id - name - breed - gender - weight
            //
            // In the while loop below, iterate through the rows of the cursor and display
            // the information from each column in this order.
            displayView.setText("The pets table contains " + cursor.getCount() + " pets.\n\n");
            displayView.append(EventEntry._ID + "    " +
                    EventEntry.COLUMN_EVENT_NAME + " \n " +
                    EventEntry.COLUMN_EVENT_DESCRIPTION + " \n " +
                    EventEntry.COLUMN_EVENT_URL + " \n " +
                    EventEntry.COLUMN_EVENT_VENUE + " \n " +
                    EventEntry.COLUMN_EVENT_START_TIME + "    " +
                    EventEntry.COLUMN_EVENT_STOP_TIME + " \n " +
                    EventEntry.COLUMN_EVENT_UNIQUE_KEY + "\n");

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

                // Display the values from each column of the current row in the cursor in the TextView
//                displayView.append(("\n" + currentID + "    " +
//                        currentName + " \n " +
//                        currentDescription + " \n " +
//                        currentUrl + " \n " +
//                        currentVenue + " \n " +
//                        currentStart + "    " +
//                        currentStop + " \n " +
//                        currentKey));
                Event e = new Event(currentName, currentDescription, currentUrl, currentVenue, currentStart,
                        currentStop, 0, 0, (byte) 0);
                aEvent.add(e);
                //notify adapter
                adapter.notifyItemInserted(aEvent.size()-1);
            }
        } finally {
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/menu_catalog.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Respond to a click on the "Insert dummy data" menu option
            case R.id.action_insert_dummy_data:
                // insertPet();
                insertEvent();
                displayDatabaseInfo();
                return true;
            // Respond to a click on the "Delete all entries" menu option
            case R.id.action_delete_all_entries:
                // Do nothing for now
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    private void insertEvent() {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        // mNameEditText.getText().toString().trim();
        String nameString = "NAME";
        String descriptionString = "DES";
        String urlString = "URL";
        String venueString = "VENUE";
        String startString = "START";
        String stopString = "STOP";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_EVENT_NAME, nameString);
        values.put(EventEntry.COLUMN_EVENT_DESCRIPTION, descriptionString);
        values.put(EventEntry.COLUMN_EVENT_URL, urlString);
        values.put(EventEntry.COLUMN_EVENT_VENUE, venueString);
        values.put(EventEntry.COLUMN_EVENT_START_TIME, startString);
        values.put(EventEntry.COLUMN_EVENT_STOP_TIME, stopString);
        values.put(EventEntry.COLUMN_EVENT_UNIQUE_KEY, 1);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(EventEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving pet", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Pet saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

}
