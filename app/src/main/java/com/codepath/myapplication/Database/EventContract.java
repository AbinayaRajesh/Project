package com.codepath.myapplication.Database;

import android.provider.BaseColumns;

public final class EventContract {


    private EventContract() {}
    public static final class EventEntry implements BaseColumns {

        /* Name of database table for events */
        public final static String TABLE_NAME = "events";

        /* Unique ID number for the event (only for use in the database table) - INTEGER */

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_EVENT_NAME ="name";
        public final static String COLUMN_EVENT_DESCRIPTION = "description";
        public final static String COLUMN_EVENT_URL = "url";
        public final static String COLUMN_EVENT_VENUE = "venue";
        public final static String COLUMN_EVENT_START_TIME = "start";
        public final static String COLUMN_EVENT_STOP_TIME = "stop";
        public final static String COLUMN_EVENT_UNIQUE_KEY = "key";

    }

}

