package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
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
import com.codepath.myapplication.Fragments.EventsListFragment;
import com.codepath.myapplication.Fragments.EventsPagerAdapter;

import org.parceler.Parcels;

import java.util.Calendar;


public class EventActivity extends AppCompatActivity {

    Country country;
    Calendar c = Calendar.getInstance();
    int seconds = c.get(Calendar.SECOND);
    String dateString = c.get(Calendar.YEAR) + "-" +
            c.get(Calendar.MONTH) + "-" +
            c.get(Calendar.DAY_OF_MONTH) + " " +
            c.get(Calendar.HOUR) + ":" +
            c.get(Calendar.MINUTE) + ":" +
            c.get(Calendar.SECOND);
    Context context;
    ViewPager vpPager;
    EventsPagerAdapter pageAdapter;
    String filter;
    Bundle bundle;
    EventsListFragment fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        pageAdapter = new EventsPagerAdapter(getSupportFragmentManager(), this);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        bundle = new Bundle();
        fragment = new EventsListFragment();
        context = this;
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
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, EventSearchActivity.class);
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
        Intent i = new Intent(this, NearbyActivity.class);
        startActivity(i);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        bundle.clear();
        int id = item.getItemId();
        switch (id) {
            case R.id.action_dropdown1: {
                bundle.putString("filter", "popularity");
                fragment.setArguments(bundle);
                filter = "popularity";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown2: {
                bundle.putString("filter", "date");
                fragment.setArguments(bundle);
                filter = "date";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown3: {
                bundle.putString("filter", "relevance");
                fragment.setArguments(bundle);
                filter = "relevance";
                pageAdapter.notifyDataSetChanged();
                break;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
    public String getFilter(){
        return filter;
    }
}
