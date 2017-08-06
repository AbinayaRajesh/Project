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

import com.codepath.myapplication.Database.FoodContract.FoodEntry;
import com.codepath.myapplication.Food;
import com.codepath.myapplication.FoodAdapter;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;

import java.util.ArrayList;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

/**
 * Displays list of pets that were entered and stored in the app.
 */
public class SavedRecipesActivity extends AppCompatActivity {

    /** Database helper that will provide us access to the database */
    private EventDbHelper mDbHelper;

    FoodAdapter adapter;
    RecyclerView rvRecipes;
    ArrayList<Food> aFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_favourites);
        setTitle("Saved Recipes");

        aFood = new ArrayList<>();
        adapter = new FoodAdapter(aFood);
        adapter.notifyDataSetChanged();

        rvRecipes= (RecyclerView) findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);

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
                FoodEntry._ID,
                FoodEntry.COLUMN_FOOD_NAME,
                FoodEntry.COLUMN_FOOD_RATING,
                FoodEntry.COLUMN_FOOD_URL};


        // Perform a query on the pets table
        Cursor cursor = db.query(
                FoodEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order


        try {

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(FoodEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_NAME);
            int ratingColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_RATING);
            int urlColumnIndex = cursor.getColumnIndex(FoodEntry.COLUMN_FOOD_URL);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentRating = cursor.getInt(ratingColumnIndex);
                String currentUrl = cursor.getString(urlColumnIndex);

                Byte y = 1;
                Food f = Food.consFood(currentName, currentRating, currentUrl, 0, y);
                aFood.add(f);
                //notify adapter
                adapter.notifyItemInserted(aFood.size() - 1);

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
        getMenuInflater().inflate(R.menu.menu_favourites, menu);
        setMenuVolume(menu,2);
        return true;
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
