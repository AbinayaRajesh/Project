package com.codepath.myapplication.Tourism;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;
import com.codepath.myapplication.TourismFragments.TourismPagerAdapter;

import org.parceler.Parcels;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

public class TourismActivity extends AppCompatActivity  {

    Context context;
    LocationManager locationManager;
    ViewPager vpPager;
    Bundle bundle;
    TourismPagerAdapter pageAdapter;
    String filter;
    Double longitude;
    Double latitude;
    String lat;
    String lng;
    String ll;
    String countryName;
    public static Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);


        bundle = new Bundle();
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        countryName = country.getName();
        pageAdapter = new TourismPagerAdapter(getSupportFragmentManager(), this);
        context = this;
        Bundle bundleB = getIntent().getExtras();
        ll = bundleB.getString("ll");
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(3);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menufilter, menu);
        setMenuVolume(menu,3);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, TourismSearchActivity.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }

    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, MapActivity.class);
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

    public boolean onOptionsItemSelected(MenuItem item) {
        bundle.clear();
        int id = item.getItemId();
        switch (id) {
            case R.id.action_dropdown1: {
                bundle.putString("filter", "popularity");

                filter = "popularity";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown2: {
                bundle.putString("filter", "date");
                filter = "date";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown3: {
                bundle.putString("filter", "relevance");
                filter = "relevance";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public String getFilter() {
        return filter;
    }
    public String getCountryName(){
        return countryName;
    }
    public String getLL() {
        return ll;
    }
}


