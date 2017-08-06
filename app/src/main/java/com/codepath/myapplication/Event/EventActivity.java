package com.codepath.myapplication.Event;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
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
import com.codepath.myapplication.EventFragments.EventsListFragment;
import com.codepath.myapplication.EventFragments.EventsPagerAdapter;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;

import org.parceler.Parcels;

import java.util.Calendar;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;


public class EventActivity extends AppCompatActivity  {

    public static Country country;
    Location mLastLocation;
    boolean distance;
    Calendar c = Calendar.getInstance();
    private LocationRequest mLocationRequest;
    int seconds = c.get(Calendar.SECOND);
    String dateString = c.get(Calendar.YEAR) + "-" +
            c.get(Calendar.MONTH) + "-" +
            c.get(Calendar.DAY_OF_MONTH) + " " +
            c.get(Calendar.HOUR) + ":" +
            c.get(Calendar.MINUTE) + ":" +
            c.get(Calendar.SECOND);
    String ll;
    Context context;
    ViewPager vpPager;
    EventsPagerAdapter pageAdapter;
    String filter;
    Bundle bundle;
    Location currentLocation;
    LocationManager locationManager;
    EventsListFragment fragment;
    GoogleApiClient googleApi;
    int distanceFiltered;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Events");


        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        pageAdapter = new EventsPagerAdapter(getSupportFragmentManager(), context);
        Bundle bundleB = getIntent().getExtras();
        bundle = new Bundle();
        ll = bundleB.getString("ll");
        fragment = new EventsListFragment();
        distance = true;
        context = this;
        distanceFiltered = 50;
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
        setMenuVolume(menu,3);
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

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        Intent p = new Intent(EventActivity.this, EventActivity.class);
        p.putExtra("country", Parcels.wrap(country));
        startActivity(p);

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
            }       case R.id.dist1: {
                        distanceFiltered = 5;
                        filter = "relevance";
                        pageAdapter.notifyDataSetChanged();
                        break;
                    }
                    case R.id.dist2: {
                        distanceFiltered = 10;
                        filter = "relevance";
                        pageAdapter.notifyDataSetChanged();
                        break;
                    }
                    case R.id.dist3: {
                        distanceFiltered = 15;
                        filter = "relevance";
                        pageAdapter.notifyDataSetChanged();
                        break;
                    }
                    case R.id.dist4: {
                        pageAdapter.notifyDataSetChanged();
                        filter = "relevance";
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
    public String getLL() {
        return ll;
    }
    public boolean getDistance(){
        return distance;
    }
    public int getDistanceFiltered(){
        return distanceFiltered;
    }

    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }




}
