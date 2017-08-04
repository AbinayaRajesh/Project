package com.codepath.myapplication.Tourism;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;
import com.codepath.myapplication.RestarauntSearchActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class tempTourism extends AppCompatActivity {

    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 13;
    AsyncHttpClient client;
    Context context;
    ArrayList<Venue> venues;
    ArrayList<String> venueIds;

    VenueCardAdapter adapter;

    // Base Url for API
    public final static String API_BASE_URL = "https://api.foursquare.com/v2";
    // parameter name for API key
    public final static String API_KEY_PARAM = "client_id";
    public final static String API_SECRET_PARAM = "client_secret";

    Country country;
    RecyclerView rvVenues;

    double latitude;
    double longitude;
    boolean b = false;
    int j = 0;


//    String lat = String.valueOf((int) latitude);
//    String lng = String.valueOf((int) longitude);
//    String ll = lat+","+lng;
    String ll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_temp);



        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        client = new AsyncHttpClient();
        venues = new ArrayList<>();
        venueIds = new ArrayList<>();
        context = getBaseContext();
        //initialize the adapter -- movies array cannot be reinitialized after this point
        adapter = new VenueCardAdapter(tempTourism.this, venues);

        // the recycler view
        Bundle bundleB = getIntent().getExtras();
        ll = bundleB.getString("ll");
        rvVenues = (RecyclerView) findViewById(R.id.rvVenues);

        //resolve the recycler view and connect a layout manager and the adapter
        //rvMovies = (RecyclerView) findViewById(rvMovies);
        rvVenues.setLayoutManager(new GridLayoutManager(tempTourism.this, 2));
        rvVenues.setAdapter(adapter);

        getNowPlaying();

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufilter, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, RestarauntSearchActivity.class);
                i.putExtra("search", query);
                startActivity(i);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, tempDemoActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, OptionsActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        startActivity(i);
    }

    // get the list of currently playing movies from the API
    private void getNowPlaying() {
        // create the url
        String url = API_BASE_URL + "/venues/search";
        // set the request parameters
        RequestParams params = new RequestParams();
        params.put("ll", ll);
        params.put("query", country.getName());
        params.put("radius", 100000);
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
                        Venue venue = new Venue(i, results.getJSONObject(i));
                        venues.add(venue);
                        venueIds.add(venue.getId());
                        b = false;
                        //notify adapter that a row was added
                        adapter.notifyItemInserted(venues.size() - 1);
                    }
                } catch (JSONException e) {
                }
                Pics();
            }

        });
    }

    public void Pics(){
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
                        }


                    }


                });
            }


    }


}
