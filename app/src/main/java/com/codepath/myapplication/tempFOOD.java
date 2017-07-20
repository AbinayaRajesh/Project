package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class tempFOOD extends AppCompatActivity {
    FoodClient client;
    ArrayList<Food> afood;
    FoodAdapter adapter;
    RecyclerView rvRecipes;
    FoodCardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_temp);

        afood = new ArrayList<>();
        // adapter = new FoodAdapter(afood);

        rvRecipes= (RecyclerView) findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);
        fetchFood("indian");

        // allows for optimizations
        rvRecipes.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(tempFOOD.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvRecipes.setLayoutManager(layout);

        // get data
        // options = Option.getContacts();



        // Create an adapter
        mAdapter = new FoodCardAdapter(tempFOOD.this, afood);
        // mAdapter.tempFOOD = this;

        // Bind adapter to list
        rvRecipes.setAdapter(mAdapter);



    }

    private void fetchFood(String query){
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
                                Food recipe = Food.fromJson(i, results.getJSONObject(i));
                                afood.add(recipe);
                                //notify adapter
                                mAdapter.notifyItemInserted(afood.size()-1);
                            }
                        }
                JSONArray docs;



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
