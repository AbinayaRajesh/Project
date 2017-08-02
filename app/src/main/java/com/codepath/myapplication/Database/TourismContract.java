package com.codepath.myapplication.Database;

import android.provider.BaseColumns;

public final class TourismContract {

    private TourismContract() {}

    public static final class TourismEntry implements BaseColumns {

        /** Name of database table for tourism */
        public final static String TABLE_NAME = "tourism";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_TOURISM_NAME ="name";
        public final static String COLUMN_TOURISM_URL = "url";
        public final static String COLUMN_TOURISM_LAT = "l_lat";
        public final static String COLUMN_TOURISM_LNG = "l_lng";
        public final static String COLUMN_TOURISM_DISTANCE = "l_distance";
        public final static String COLUMN_TOURISM_CITY = "l_city";
        public final static String COLUMN_TOURISM_STATE = "l_state";

    }

}

