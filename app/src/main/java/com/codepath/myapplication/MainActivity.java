package com.codepath.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Country.CountryAdapter;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity  {
    //instance variables
    ArrayList<Country> countries;
    public final static String API_BASE_URL = "http://countryapi.gear.host/v1/Country/getCountries";
    public final static String API_KEY_PARAM = "api_key";
    public final static String TAG = "CountryList";
    RecyclerView rvCountries;
    CountryAdapter adapter;
    AsyncHttpClient client;
    ArrayList<Country> popularCountries;
    public String ll;
    Context context;
    @Override
    //context neccesary for multidex
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    //creates the view and populates it with countries that users can choose to vacation too
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        popularCountries = new ArrayList<>();
        client = new AsyncHttpClient();
        countries = new ArrayList<>();
        adapter = new CountryAdapter(countries);
        rvCountries = (RecyclerView) findViewById(R.id.rvCountry);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.setAdapter(adapter);
        getCountryList();
    }
    //pulls countries from an API that gets displayed
    private void getCountryList(){
        String url = API_BASE_URL;
        //for popular countries we replace the name with the adjective in searches in order to
        //better populate events
        final Map<String,String> map=new HashMap<String, String>();
        map.put("Austria", "Austrian");
        map.put("Brazil", "Brazilian");
        map.put("China", "Chinese");
        map.put("Egypt", "Egyptian");
        map.put("France", "French");
        map.put("Germany", "German");
        map.put("Greece", "Greek");
        map.put("India", "Indian");
        map.put("Italy", "Italian");
        map.put("Japan", "Japanese");
        map.put("Mexico", "Mexican");
        map.put("Morocco", "Moroccan");
        map.put("Singapore", "Singaporean");
        map.put("Spain", "Spanish");
        map.put("Turkey", "Turkish");
        RequestParams params = new RequestParams();
        //actual request to pull the countries
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("Response");
                    for(int i =0; i<results.length(); i++){
                       // Country country = new Country(results.getJSONObject(i));
                        Country country = Country.fromJSON( results.getJSONObject(i));
                        country.setAdjective(map.get(country.getName()));
                        countries.add(country);
                        //if a country is popular based on visitors to country we add it to
                        //the start of the list for greater convience for the user
                        if (countries.get(i).getName().equals("France") || countries.get(i).getName().equals("China") || countries.get(i).getName().equals("Japan") ||
                        countries.get(i).getName().equals("Italy") || countries.get(i).getName().equals("India") ||  countries.get(i).getName().equals("Spain")
                                ||  countries.get(i).getName().equals("Turkey") ||  countries.get(i).getName().equals("United Kingdom") ||  countries.get(i).getName().equals("Mexico") ||
                                countries.get(i).getName().equals("Germany") || countries.get(i).getName().equals("Brazil") ||  countries.get(i).getName().equals("Egypt") ||
                                countries.get(i).getName().equals("Greece") ||  countries.get(i).getName().equals("Australia") || countries.get(i).getName().equals("Vietnam")
                                || countries.get(i).getName().equals("Morocco") ||  countries.get(i).getName().equals("South Korea") ||  countries.get(i).getName().equals("Singapore") ||
                                countries.get(i).getName().equals("Austria") || countries.get(i).getName().equals("United States of America"))
                        {
                            popularCountries.add(countries.get(i));
                            //countries.add(0,popularCountries.get(popularCountries.size()-1));
                            //adapter.notifyItemInserted(0);
                        }
                        //notify adapter
                        adapter.notifyItemInserted(countries.size()-1);
                    }
                    for (int i = popularCountries.size()-1; i > 0; i--){
                        countries.add(0,popularCountries.get(i));
                        adapter.notifyItemInserted(0);
                    }
                } catch (JSONException e) {
                    logError("Failed to parse now playing movies", e, true);
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
    //logs error if neccesary
    private void logError(String message, Throwable error, boolean alertUser){
        Log.e(TAG, message, error);
        //alert user to avoid silent error
        if(alertUser){
            // show long toast
            // Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }// log error
    //option menu, allows user to use actionbar functionality, including country search
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, CountrySearchActivity.class);
                i.putExtra("search", query);
                i.putExtra("countries", countries);
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
    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }


    public void test (View v) {
        Intent i = new Intent(MainActivity.this, OptionsActivity.class);
        Country country = Country.consCountry();

        country.setName("Brazil");
        country.setContinent("South America");
        country.setLanguage("Portugese");
        //country.flagUrl = object.getString("FlagPng");
        //country.nativeName = object.getString("NativeName");
        i.putExtra("country", Parcels.wrap(country));
        startActivity(i);
    }

}

