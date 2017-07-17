package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
<<<<<<< HEAD
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
=======
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class FoodActivity extends AppCompatActivity {
    FoodClient client;
    ArrayList<Food> afood;
<<<<<<< HEAD
    FoodAdapter adapter;
    RecyclerView rvRecipes;

=======
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
<<<<<<< HEAD
        afood = new ArrayList<>();
        adapter = new FoodAdapter(afood);
        rvRecipes= (RecyclerView) findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);


        fetchFood("");
=======
        fetchFood("indian");
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0
    }

    private void fetchFood(String query){
        client = new FoodClient();
        client.getRecipes(query, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
<<<<<<< HEAD

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

=======
                JSONArray docs;

                    try {
                        if(response != null) {
                            docs = response.getJSONArray("results");
                            final ArrayList<Food> recipes = Food.fromJson(docs);
                        }
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0
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

    //fetch cuisine
    //
}
