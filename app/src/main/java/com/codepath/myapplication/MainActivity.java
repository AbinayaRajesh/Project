package com.codepath.myapplication;



import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

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
    RecyclerView rvCountries;
    CountryAdapter adapter;
    AsyncHttpClient client;


    Button eventButton;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(this, OptionsAvailable.class));

        eventButton = (Button) findViewById(R.id.bttnEvent);
        context = this;
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, EventActivity.class);
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
                    JSONArray results = response.getJSONArray("results");
                    for(int i =0; i<results.length(); i++){
                        Country country = new Country(results.getJSONObject(i));
                        countries.add(country);
                        //notify adapter
                        adapter.notifyItemInserted(countries.size()-1);
                    }
                } catch (JSONException e) {
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }
        });

    }
}
