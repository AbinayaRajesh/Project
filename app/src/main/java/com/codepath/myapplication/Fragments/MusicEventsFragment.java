package com.codepath.myapplication.Fragments;

import android.os.Bundle;

import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.EventActivity;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = new AsyncHttpClient();
        filter = ((EventActivity) getActivity()).getFilter();
        getSportsEvents();
    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "china");
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
    public void changeFilter(String filtered){
        filter = filtered;
    }
}
