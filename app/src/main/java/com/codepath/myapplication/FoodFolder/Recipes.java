package com.codepath.myapplication.FoodFolder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Food;
import com.codepath.myapplication.FoodAdapter;
import com.codepath.myapplication.FoodClient;
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


public class Recipes extends AppCompatActivity {
    FoodClient client;
    ArrayList<Food> afood;
    FoodAdapter adapter;
    RecyclerView rvRecipes;
    FoodCardAdapter mAdapter;
    Country country;
    String ll;
    Context context;
    Byte y;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_temp);

        setTitle("Recipes");

        context = getBaseContext();
        ll = getIntent().getStringExtra("ll");
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));

        afood = new ArrayList<>();
        // adapter = new FoodAdapter(afood);

        rvRecipes= (RecyclerView) findViewById(R.id.rvRecipes);
        rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        rvRecipes.setAdapter(adapter);

        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));

        if (country.getAdjective()!=null) {
            fetchFood(country.getAdjective());
        }
        else {
            fetchFood(country.getName());
        }
        rvRecipes.setHasFixedSize(true);
        final GridLayoutManager layout = new GridLayoutManager(Recipes.this, 2);
        rvRecipes.setLayoutManager(layout);
        mAdapter = new FoodCardAdapter(Recipes.this, afood);
        rvRecipes.setAdapter(mAdapter);

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tourism, menu);
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
        Intent i = new Intent(context, MapActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
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

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
        for (int i =0; i<afood.size(); i++) {
            Food recipe = afood.get(i);
            if (CheckIsRecipeAlreadyInDBorNot("recipes", "name", "\""+recipe.getName()+ "\"")) {
                y = 1;
                recipe.setFavourite(y);
            }
            else {
                y = 0;
                recipe.setFavourite(y);
            }
        }
        mAdapter.notifyDataSetChanged();
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
                                Food recipe = Food.fromJson(i, results.getJSONObject(i));
                                if (CheckIsRecipeAlreadyInDBorNot("recipes", "name", "\""+recipe.getName()+ "\"")) {
                                    y = 1;
                                    recipe.setFavourite(y);
                                }
                                else {
                                    y = 0;
                                    recipe.setFavourite(y);
                                }
                                afood.add(recipe);
                                //notify adapter
                                mAdapter.notifyItemInserted(afood.size()-1);
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

    public boolean CheckIsRecipeAlreadyInDBorNot(String TableName,
                                                 String dbfield, String fieldValue) {

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }

}
