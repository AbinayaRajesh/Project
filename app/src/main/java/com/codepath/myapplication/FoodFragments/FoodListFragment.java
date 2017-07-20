package com.codepath.myapplication.FoodFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.myapplication.Food;
import com.codepath.myapplication.FoodAdapter;
import com.codepath.myapplication.FoodClient;
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

public class FoodListFragment extends Fragment {
    FoodClient client;
    ArrayList<Food> afood;
    FoodAdapter adapter;
    VenueAdapter adapterVenue;
    RecyclerView rvRecipes;
    RecyclerView rvVenues;
    AsyncHttpClient clientRest = new AsyncHttpClient();
    ArrayList<Venue> venues = new ArrayList<>();
    ArrayList<String> venueIds = new ArrayList<>();
    public final static String API_BASE_URL = "https://api.foursquare.com/v2";
    public final static String API_KEY_PARAM = "client_id";
    public final static String API_SECRET_PARAM = "client_secret";
    int j = 0;
    boolean b = false;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_food_list_fragment, container, false);
        afood = new ArrayList<>();
        adapter = new FoodAdapter(afood);
        adapterVenue = new VenueAdapter(venues);
        //resolve the recycler view and connect a layout manager and the adapter
        rvRecipes = (RecyclerView) v.findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecipes.setAdapter(adapter);

        rvVenues = (RecyclerView) v.findViewById(R.id.rvVenues);
        rvVenues.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVenues.setAdapter(adapterVenue);
        return v;
    }
    public void fetchFood(String query){

        client = new FoodClient();
        client.getRecipes(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if(response != null) {
                        JSONArray results = response.getJSONArray("matches");
                        for(int i =0; i<results.length(); i++){
                            // Country country = new Country(results.getJSONObject(i));
                            //final ArrayList<Food> recipes = Food.fromJson(results);
                            Food recipe = Food.fromJson(results.getJSONObject(i));
                            afood.add(recipe);
                            //notify adapter
                            adapter.notifyItemInserted(afood.size()-1);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
    public void getRestaraunt(String query){
            // create the url
            String url = API_BASE_URL + "/venues/search";
            // set the request parameters
            RequestParams params = new RequestParams();
            params.put("ll", "40,-74");
            params.put(API_KEY_PARAM, getString(R.string.api_key));  // Always needs API key
            params.put(API_SECRET_PARAM, getString(R.string.api_secret));
            params.put("v", "20170713");
            params.put("categoryId", "4d4b7105d754a06374d81259");
            //params.put("query", country.getName());
            // request a GET response expecting a JSON object response
            clientRest.get(url, params, new JsonHttpResponseHandler() {
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
                             adapterVenue.notifyItemInserted(venues.size() - 1);
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
            clientRest.get(venueUrl, params, new JsonHttpResponseHandler() {

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
                        adapterVenue.notifyDataSetChanged();
                        j++;
                    } catch (JSONException e) {
                        venues.get(j).setImageUrl("");
                        adapterVenue.notifyDataSetChanged();
                        j++;
                        // logError("Failed to pase now_playing endpoint", e, true);
                    }
                }


            });
        }
    }
}

