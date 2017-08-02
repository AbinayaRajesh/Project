package com.codepath.myapplication.Database;

import android.provider.BaseColumns;

public final class FoodContract {

    private FoodContract() {}
    public static final class FoodEntry implements BaseColumns {

        /** Name of database table for recipes */
        public final static String TABLE_NAME = "recipes";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_FOOD_NAME ="name";
        public final static String COLUMN_FOOD_URL = "url";
        public final static String COLUMN_FOOD_RATING = "rating";


    }

}

