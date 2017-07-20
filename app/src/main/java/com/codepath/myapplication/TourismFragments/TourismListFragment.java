package com.codepath.myapplication.TourismFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.R;
import com.codepath.myapplication.VenueAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TourismListFragment extends Fragment {

    AsyncHttpClient client;
    ArrayList<Venue> venues;
    RecyclerView rvVenues;
    ArrayList<String> venueIds;
    VenueAdapter adapter;
    boolean b = false;
    int j = 0;
    // Base Url for API
    public final static String API_BASE_URL = "https://api.foursquare.com/v2";
    // parameter name for API key
    public final static String API_KEY_PARAM = "client_id";
    public final static String API_SECRET_PARAM = "client_secret";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_tourism_list_fragment, container, false);
        client = new AsyncHttpClient();
        venues = new ArrayList<>();
        venueIds = new ArrayList<>();
        //initialize the adapter -- movies array cannot be reinitialized after this point
        adapter = new VenueAdapter(venues);
        // the recycler view
        rvVenues = (RecyclerView) v.findViewById(R.id.rvVenues);

        //resolve the recycler view and connect a layout manager and the adapter
        //rvMovies = (RecyclerView) findViewById(rvMovies);
        rvVenues.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVenues.setAdapter(adapter);
        return v;
        // LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//
//        if ( ContextCompat.checkSelfPermission( this, android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
//            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
//                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//            String lat = String.valueOf((int) latitude);
//            String lng = String.valueOf((int) longitude);
//            ll = lat+","+lng;
//        }
//        else {
//            ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  },
//                    MY_PERMISSION_ACCESS_COURSE_LOCATION );
//            LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//            longitude = location.getLongitude();
//            latitude = location.getLatitude();
//            String lat = String.valueOf((int) latitude);
//            String lng = String.valueOf((int) longitude);
//            ll = lat+","+lng;
//        }
    }
    public void getRestaraunt(String categoryID, String query){
        // create the url
        String url = API_BASE_URL + "/venues/search";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put("ll", "40,-74");
        params.put(API_KEY_PARAM, getString(R.string.api_key));  // Always needs API key
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
                        venueIds.add(venue.getId());
                        b = false;
                        //notify adapter that a row was added
                        adapter.notifyItemInserted(venues.size() - 1);
                    }
                    // Log.i(TAG, String.format("Loaded %s movies", results.length()));
                } catch (JSONException e) {
                    // logError("Failed to pase now_playing endpoint", e, true);
                }
                getPictures();
            }

        });
    }
    public void getPictures() {
        for (int i = 0; i < venueIds.size(); i++) {
            String venueUrl = API_BASE_URL + "/venues/" + venueIds.get(i);
            RequestParams params = new RequestParams();
            params = new RequestParams();
            //params.put("ll", ll);
            params.put(API_KEY_PARAM, getString(R.string.api_key));  // Always needs API key
            params.put(API_SECRET_PARAM, getString(R.string.api_secret));
            params.put("v", "20170713");
            client.get(venueUrl, params, new JsonHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    // load the results into movies list
                    Log.d("Heree", "woww");
                    try {
                        JSONObject resp = response.getJSONObject("response");
                        JSONObject ven = resp.getJSONObject("venue");
                        JSONObject photos = ven.getJSONObject("photos");
                        JSONObject groups = photos.getJSONArray("groups").getJSONObject(0);
                        JSONObject items = groups.getJSONArray("items").getJSONObject(0);
                        String prefix = items.getString("prefix");
                        String suffix = items.getString("suffix");
                        String imgUrl = prefix + "300x300" + suffix;
                        venues.get(j).setImageUrl(imgUrl);
                        adapter.notifyDataSetChanged();
                        j++;
                    } catch (JSONException e) {
                        venues.get(j).setImageUrl("");
                        adapter.notifyDataSetChanged();
                        j++;
                        // logError("Failed to pase now_playing endpoint", e, true);
                    }
                }


            });
        }
    }

}