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

public class FestivalsEventsFragment extends EventsListFragment  {
    AsyncHttpClient client;
    Boolean distance;
    Byte y;
    String countryName;
    Context context;
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
        getSportsEvents();
        context = getActivity();
    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        if(distance) {
            params.put("within", 100);
            params.put("location", ll);
        }
        params.put("keywords", countryName);
        params.put("category", "festivals_parades");
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
                        if (CheckIsDataAlreadyInDBorNot("events", "venue", "\""+event.getEventVenue()+ "\"")) {
                            y = 0;
                            event.setFavourite(y);
                        }
                        else {
                            y = 1;
                            event.setFavourite(y);
                        }
                        events.add(event);
                        adapter.notifyDataSetChanged();
                        //notify adapter that a row was added
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//http://api.eventful.com/rest/events/search?app_key=95JSGDKWtDtWRRgx&keywords=fun
    }


    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue) {

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;

    }
    }
