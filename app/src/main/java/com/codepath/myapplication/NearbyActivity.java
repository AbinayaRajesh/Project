package com.codepath.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Maps.MarkerDemoActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by eyobtefera on 7/18/17.
 */

public class NearbyActivity extends AppCompatActivity {
    AsyncHttpClient client;
    public final static String API_BASE_URL = "http://api.eventful.com/json/";
    //API key parameter name
    public final static String API_KEY_PARAM = "95JSGDKWtDtWRRgx";
    public ArrayList<Event> Sevents;
    public ArrayList<Event> Mevents;
    public ArrayList<Event> Fevents;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Sevents = new ArrayList<Event>();
        Mevents = new ArrayList<Event>();
        Fevents = new ArrayList<Event>();
        client = new AsyncHttpClient();
        getSportsEvents();


    }
    private void getSportsEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "china");
        params.put("category", "sports");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < 10; i++){
                        Event event = Event.fromJson(eventArray.getJSONObject(i));
                        Sevents.add(event);
                        //notify adapter that a row was added
                    }

                    getMusicEvents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMusicEvents(){
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "china");
        params.put("category", "music");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < 10; i++){
                        Event event = Event.fromJson(eventArray.getJSONObject(i));
                        Mevents.add(event);
                    }
                    getFestivalsEvents();
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void getFestivalsEvents(){
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
                        Event event = Event.fromJson(eventArray.getJSONObject(i));
                        Fevents.add(event);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(NearbyActivity.this, MarkerDemoActivity.class);
                i.putExtra("Sevents", Sevents);
                i.putExtra("Mevents", Mevents);
                i.putExtra("Fevents", Fevents);
                startActivity(i);
            }
        });

    }
}
