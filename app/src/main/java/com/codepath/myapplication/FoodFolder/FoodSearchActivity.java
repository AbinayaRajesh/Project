package com.codepath.myapplication.FoodFolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Food;
import com.codepath.myapplication.FoodClient;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

public class FoodSearchActivity extends AppCompatActivity {

    FoodClient client;
    ArrayList<Food> afood;
    FoodCardAdapter adapter;
    RecyclerView rvFood;
    Context context;
    Country country;
    String ll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        ll = getIntent().getStringExtra("ll");
        String query = getIntent().getExtras().getString("search");
        // allows for optimizations
        context = getBaseContext();
        rvFood = (RecyclerView) findViewById(R.id.rvFood);
        rvFood.setLayoutManager(new LinearLayoutManager(this));
        rvFood.setAdapter(adapter);
        rvFood.setHasFixedSize(true);
        afood = new ArrayList<>();
        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(FoodSearchActivity.this, 2);
        rvFood.setLayoutManager(layout);
        adapter = new FoodCardAdapter(FoodSearchActivity.this, afood);
        // Bind adapter to list
        rvFood.setAdapter(adapter);
        fetchFood("indian", query);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusearch, menu);
        setMenuVolume(menu,3);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, FoodSearchActivity.class);
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
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }
    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void fetchFood(String query, String querytwo){

        client = new FoodClient();
        client.getRecipes(query, querytwo, new JsonHttpResponseHandler(){
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
}
