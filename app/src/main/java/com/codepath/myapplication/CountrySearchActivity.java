package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Country.CountryAdapter;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

public class CountrySearchActivity extends AppCompatActivity {

    ArrayList<Country> countries;
    RecyclerView rvCountries;
    CountryAdapter adapter;
    AsyncHttpClient client;
    ArrayList<Country> searchCountries;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_search);
        String query = getIntent().getExtras().getString("search");
        countries = getIntent().getExtras().getParcelableArrayList("countries");
        context = this;
        client = new AsyncHttpClient();
        searchCountries = new ArrayList<>();
        adapter = new CountryAdapter(searchCountries);
        rvCountries = (RecyclerView) findViewById(R.id.rvCountry);
        rvCountries.setLayoutManager(new LinearLayoutManager(this));
        rvCountries.setAdapter(adapter);
        getCountryList(query);
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

    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, MapActivity.class);
        startActivity(i);
    }
    private void getCountryList(String query) {
        for (int i = 0; i < countries.size(); i++) {
            if (countries.get(i).getName().contains(query)) {
                searchCountries.add(countries.get(i));
                adapter.notifyItemInserted(searchCountries.size() - 1);
            }
        }
    }
    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }
}
