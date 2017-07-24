package com.codepath.myapplication.Fragments;

import android.os.Bundle;

import com.codepath.myapplication.Event.Event;
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

public class FestivalsEventsFragment extends EventsListFragment {
    AsyncHttpClient client;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();
        getSportsEvents();
    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "china");
        params.put("category", "festivals_parades");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < 10; i++){
                        Event event = Event.fromJson(i, eventArray.getJSONObject(i));
                        events.add(event);
                        //notify adapter that a row was added
                        adapter.notifyItemChanged(events.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//http://api.eventful.com/rest/events/search?app_key=95JSGDKWtDtWRRgx&keywords=fun
    }
}
