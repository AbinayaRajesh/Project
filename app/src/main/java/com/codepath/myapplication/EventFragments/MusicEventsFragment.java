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

/**
 * Created by eyobtefera on 7/18/17.
 */

public class MusicEventsFragment extends EventsListFragment {
    AsyncHttpClient client;

    Byte y;
    public final static String COLUMN_EVENT_VENUE = "venue";

    String countryName;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            countryName = bundle.getString("country", "");
        }

        filter = ((EventActivity) getActivity()).getFilter();
        ll = ((EventActivity) getActivity()).getFilter();

        getSportsEvents();
    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("ll", ll);
        params.put("within", 100);
        params.put("keywords", countryName);
        params.put("category", "music");
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
                        if (CheckIsDataAlreadyInDBorNot("events", "venue", "\""+event.getEventVenue()+ "\"", getContext())) {
                            y = 0;
                            event.setFavourite(y);
                        }
                        else {
                            y = 1;
                            event.setFavourite(y);
                        }
                        events.add(event);
                        //notify adapter that a row was added
                        adapter.notifyItemChanged(events.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue, Context c) {

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(c);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

}
