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
package com.codepath.myapplication.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class EventContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private EventContract() {}

    /**
     * Inner class that defines constant values for the pets database table.
     * Each entry in the table represents a single pet.
     */
    public static final class EventEntry implements BaseColumns {

        /** Name of database table for pets */
        public final static String TABLE_NAME = "events";

        /**
         * Unique ID number for the pet (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        /**
         * Name of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_EVENT_NAME ="name";

        /**
         * Breed of the pet.
         *
         * Type: TEXT
         */
        public final static String COLUMN_EVENT_DESCRIPTION = "description";


        public final static String COLUMN_EVENT_URL = "url";

        /**
         * Weight of the pet.
         *
         * Type: INTEGER
         */
        public final static String COLUMN_EVENT_VENUE = "venue";

        public final static String COLUMN_EVENT_START_TIME = "start";
        public final static String COLUMN_EVENT_STOP_TIME = "stop";
        public final static String COLUMN_EVENT_UNIQUE_KEY = "key";


    }

}

