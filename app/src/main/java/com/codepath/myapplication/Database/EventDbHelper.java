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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Database.FoodContract.FoodEntry;
import com.codepath.myapplication.Database.TourismContract.TourismEntry;
/**
 * Database helper for Pets app. Manages database creation and version management.
 */
public class EventDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = EventDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "shelter.db";

    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 2;

    /**
     * Constructs a new instance of {@link EventDbHelper}.
     *
     * @param context of the app
     */
    public EventDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This is called when the database is created for the first time.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the events table
        String SQL_CREATE_EVENTS_TABLE =  "CREATE TABLE " + EventEntry.TABLE_NAME + " ("
                + EventEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + EventEntry.COLUMN_EVENT_NAME + " TEXT NOT NULL, "
                + EventEntry.COLUMN_EVENT_DESCRIPTION + " TEXT, "
                + EventEntry.COLUMN_EVENT_URL + " TEXT NOT NULL, "
                + EventEntry.COLUMN_EVENT_VENUE + " TEXT NOT NULL, "
                + EventEntry.COLUMN_EVENT_START_TIME + " TEXT NOT NULL, "
                + EventEntry.COLUMN_EVENT_STOP_TIME + " TEXT NOT NULL, "
                + EventEntry.COLUMN_EVENT_UNIQUE_KEY + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

        // Create a String that contains the SQL statement to create the food table
        String SQL_CREATE_FOOD_TABLE =  "CREATE TABLE " + FoodEntry.TABLE_NAME + " ("
                + FoodEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + FoodEntry.COLUMN_FOOD_NAME + " TEXT NOT NULL, "
                + FoodEntry.COLUMN_FOOD_URL + " TEXT NOT NULL, "
                + FoodEntry.COLUMN_FOOD_RATING + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_FOOD_TABLE);

        // Create a String that contains the SQL statement to create the tourism table
        String SQL_CREATE_TOURISM_TABLE =  "CREATE TABLE " + TourismEntry.TABLE_NAME + " ("
                + TourismEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TourismEntry.COLUMN_TOURISM_NAME + " TEXT NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_URL + " TEXT NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_STATE + " TEXT NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_CITY + " TEXT NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_LAT + " REAL NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_LNG + " REAL NOT NULL, "
                + TourismEntry.COLUMN_TOURISM_DISTANCE + " INTEGER NOT NULL DEFAULT 0);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_TOURISM_TABLE);
    }

    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}