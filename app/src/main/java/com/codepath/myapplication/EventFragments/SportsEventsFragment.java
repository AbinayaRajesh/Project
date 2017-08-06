package com.codepath.myapplication.EventFragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Event.EventActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static com.codepath.myapplication.Database.EventContract.EventEntry.COLUMN_EVENT_START_TIME;
import static com.codepath.myapplication.Database.EventContract.EventEntry.COLUMN_EVENT_VENUE;

/**
 * Created by eyobtefera on 7/18/17.
 */

public class SportsEventsFragment extends EventsListFragment {
    AsyncHttpClient client;

    Byte y;
    String countryName;
    Boolean distance;
    Context context;
    int distanceFilter;

    String filter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            countryName = bundle.getString("country", "");
        }
        client = new AsyncHttpClient();
        filter = ((EventActivity) getActivity()).getFilter();
        ll = ((EventActivity) getActivity()).getLL();
        distance = ((EventActivity) getActivity()).getDistance();
        distanceFilter = ((EventActivity) getActivity()).getDistanceFiltered();
        context = getActivity();
        getSportsEvents();
    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", countryName);
        if(distance) {
            params.put("within", distanceFilter);
            params.put("location", ll);
        }
        params.put("category", "sports");
        if(filter != null) {
            if (filter.equals("popularity")) {
                params.put("sort_order", "popularity");
            } else if (filter.equals("date")) {
                params.put("sort_order", "date");
            } else if (filter.equals("relevance")) {
                params.put("sort_order", "relevance");
            }
        }
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    events.clear();
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < eventArray.length(); i++){
                        Event event = Event.fromJson(i, eventArray.getJSONObject(i));
                        if (CheckIsDataAlreadyInDBorNot("events",
                                COLUMN_EVENT_VENUE, "\""+event.getEventVenue()+ "\"",
                                COLUMN_EVENT_START_TIME, "\""+event.getStartTime()+ "\"")) {
                            y = 1;
                            event.setFavourite(y);
                        }
                        else {
                            y = 0;
                            event.setFavourite(y);
                        }
                        events.add(event);
                        adapter.notifyDataSetChanged();
                        //notify adapter that a row was added
                        //adapter.notifyItemChanged(events.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

//http://api.eventful.com/rest/events/search?app_key=95JSGDKWtDtWRRgx&keywords=fun
    }

    @Override
    public void onResume() {
        super.onResume();
        for (int i =0; i<events.size(); i++) {
            Event event = events.get(i);
            if (CheckIsDataAlreadyInDBorNot("events",
                    COLUMN_EVENT_VENUE, "\""+event.getEventVenue()+ "\"",
                    COLUMN_EVENT_START_TIME, "\""+event.getStartTime()+ "\"")) {
                y = 1;
                event.setFavourite(y);
            }
            else {
                y = 0;
                event.setFavourite(y);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield1, String fieldValue1,
                                               String dbfield2, String fieldValue2) {

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield1 + " = " + fieldValue1
                + " and " + dbfield2 + " = " + fieldValue2;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }
}

