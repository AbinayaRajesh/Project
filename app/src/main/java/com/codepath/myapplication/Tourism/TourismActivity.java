package com.codepath.myapplication.Tourism;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TourismActivity extends AppCompatActivity {

    AsyncHttpClient client;

    ArrayList<Venue> venues;

    // Base Url for API
    public final static String API_BASE_URL = "https://api.foursquare.com/v2";
    // parameter name for API key
    public final static String API_KEY_PARAM = "client_id";
    public final static String API_SECRET_PARAM = "client_secret";
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        client = new AsyncHttpClient();
        venues = new ArrayList<>();


//        client.getSearch( new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                super.onSuccess(statusCode, headers, response);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                super.onSuccess(statusCode, headers, response);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
//                super.onFailure(statusCode, headers, throwable, errorResponse);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                super.onFailure(statusCode, headers, responseString, throwable);
//            }
//        });
//




        getNowPlaying();

    }


    // get the list of currently playing movies from the API
    private void getNowPlaying() {
        // create the url
        String url = API_BASE_URL+"/venues/search";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put("ll", "40.7,-74");
        params.put(API_KEY_PARAM, getString(R.string.api_key));  // Always needs API key
        params.put(API_SECRET_PARAM, getString(R.string.api_secret));
        params.put("v", "20170713");
        // request a GET response expecting a JSON object response
        client.get(url,params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // load the results into movies list

                try {
                    JSONObject resp = response.getJSONObject("response");
                    JSONArray results = resp.getJSONArray("venues");
                    for(int i=0; i<results.length(); i++){
                        Venue venue = new Venue(results.getJSONObject(i));
                        venues.add(venue);

//                        Movie movie = new Movie(results.getJSONObject(i));
//                        movies.add(movie);
//                        //notify adapter that a row was added
//                        adapter.notifyItemInserted(movies.size()-1);

                    }
                    // Log.i(TAG, String.format("Loaded %s movies", results.length()));

                } catch (JSONException e) {
                    // logError("Failed to pase now_playing endpoint", e, true);
                }


            }


        });
    }


}
