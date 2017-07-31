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

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class TourismContract {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
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

