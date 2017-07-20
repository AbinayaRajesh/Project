package com.codepath.myapplication;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Models.Venue;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class NearbyActivity extends AppCompatActivity {

    //base url for API call
    public final static String API_BASE_URL = "http://api.eventful.com/json/";
    //API key parameter name
    public final static String API_KEY_PARAM = "95JSGDKWtDtWRRgx";
    AsyncHttpClient client = new AsyncHttpClient();
    public ArrayList<Event> events;

    //image config
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);


        // EVENTS

        events = new ArrayList<>();

        //new EventRetriever().execute();
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "food");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < 10; i++){
                        Event event = Event.fromJson(eventArray.getJSONObject(i));
                        events.add(event);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        // TOURISM

        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        client = new AsyncHttpClient();
        venues = new ArrayList<>();

        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            String lat = String.valueOf((int) latitude);
            String lng = String.valueOf((int) longitude);
            ll = lat+","+lng;
        }
        else {
            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            String lat = String.valueOf((int) latitude);
            String lng = String.valueOf((int) longitude);
            ll = lat+","+lng;
        }

        getNowPlaying();





    }


    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 13;


    ArrayList<Venue> venues;



    // Base Url for API
    public final static String API_BASE_URL1 = "https://api.foursquare.com/v2";
    // parameter name for API key
    public final static String API_KEY_PARAM1 = "client_id";
    public final static String API_SECRET_PARAM = "client_secret";

    Country country;


    double latitude;
    double longitude;
    boolean b = false;
    int j = 0;


    String ll;

    // get the list of currently playing movies from the API
    private void getNowPlaying() {
        // create the url
        String url = API_BASE_URL1 + "/venues/search";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put("ll", "40,-74");
        params.put(API_KEY_PARAM1, getString(R.string.api_key));  // Always needs API key
        params.put(API_SECRET_PARAM, getString(R.string.api_secret));
        params.put("v", "20170713");
        //params.put("query", country.getName());
        // request a GET response expecting a JSON object response
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movies list

                try {
                    JSONObject resp = response.getJSONObject("response");
                    JSONArray results = resp.getJSONArray("venues");
                    for (int i = 0; i < results.length(); i++) {
                        Venue venue = new Venue(results.getJSONObject(i));
                        venues.add(venue);

                    }
                    // Log.i(TAG, String.format("Loaded %s movies", results.length()));

                } catch (JSONException e) {
                    // logError("Failed to pase now_playing endpoint", e, true);
                }




            }

        });


  }



}
