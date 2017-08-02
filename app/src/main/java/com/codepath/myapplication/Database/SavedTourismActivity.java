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

import com.codepath.myapplication.Database.TourismContract.TourismEntry;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.R;
import com.codepath.myapplication.Tourism.VenueAdapter;

import java.util.ArrayList;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class SavedTourismActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private EventDbHelper mDbHelper;

    VenueAdapter adapter;
    RecyclerView  rvTourism;
    ArrayList<Venue> aVenue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism_favourites);
        setTitle("Saved Tourist spots");
        aVenue = new ArrayList<>();
        adapter = new VenueAdapter(aVenue);
        adapter.notifyDataSetChanged();

        rvTourism = (RecyclerView) findViewById(R.id.rvTourism);
        rvTourism.setLayoutManager(new LinearLayoutManager(this));
        rvTourism.setAdapter(adapter);


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
                TourismEntry._ID,
                TourismEntry.COLUMN_TOURISM_NAME,
                TourismEntry.COLUMN_TOURISM_URL,
                TourismEntry.COLUMN_TOURISM_LAT,
                TourismEntry.COLUMN_TOURISM_LNG,
                TourismEntry.COLUMN_TOURISM_CITY,
                TourismEntry.COLUMN_TOURISM_STATE,
                TourismEntry.COLUMN_TOURISM_DISTANCE};

        // Perform a query on the pets table
        Cursor cursor = db.query(
                TourismEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(TourismEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_NAME);
            int urlColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_URL);
            int latColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_LAT);
            int lngColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_LNG);
            int cityColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_CITY);
            int stateColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_STATE);
            int distColumnIndex = cursor.getColumnIndex(TourismEntry.COLUMN_TOURISM_DISTANCE);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentUrl = cursor.getString(urlColumnIndex);
                float currentLat = cursor.getFloat(latColumnIndex);
                float currentLng = cursor.getFloat(lngColumnIndex);
                String currentCity = cursor.getString(cityColumnIndex);
                String currentState = cursor.getString(stateColumnIndex);
                int currentDist = cursor.getInt(distColumnIndex);

                Byte y = 1;

                    Venue v = Venue.consVenue(currentName, currentUrl, currentLat, currentLng, currentCity,
                            currentState, currentDist, y);
                    aVenue.add(v);
                    //notify adapter
                    adapter.notifyItemInserted(aVenue.size() - 1);

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

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


}
