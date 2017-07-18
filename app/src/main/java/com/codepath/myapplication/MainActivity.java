package com.codepath.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Country.CountryAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {
    ArrayList<Country> countries;
    public final static String API_BASE_URL = "http://countryapi.gear.host/v1/Country/getCountries";
    public final static String API_KEY_PARAM = "api_key";
    public final static String TAG = "CountryList";

    RecyclerView rvCountries;
    CountryAdapter adapter;
    AsyncHttpClient client;


    Button eventButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        client = new AsyncHttpClient();
        countries = new ArrayList<>();
        adapter = new CountryAdapter(countries);
        rvCountries = (RecyclerView) findViewById(R.id.rvCountry);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.setAdapter(adapter);
        getCountryList();



        eventButton = (Button) findViewById(R.id.bttnEvent);
        context = this;
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, MusicActivity.class);
                i.putExtra("query", "food");
                startActivity(i);
            }

        });



    }
    private void getCountryList(){
        String url = API_BASE_URL;
        RequestParams params = new RequestParams();
        //params.put(API_KEY_PARAM, getString(R.string.api_key));
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray results = response.getJSONArray("Response");
                    for(int i =0; i<results.length(); i++){
                       // Country country = new Country(results.getJSONObject(i));
                        Country country = Country.fromJSON(results.getJSONObject(i));
                        countries.add(country);
                        //notify adapter
                        adapter.notifyItemInserted(countries.size()-1);
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

    private void logError(String message, Throwable error, boolean alertUser){
        Log.e(TAG, message, error);
        //alert user to avoid silent error
        if(alertUser){
            // show long toast
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }// log error
}
