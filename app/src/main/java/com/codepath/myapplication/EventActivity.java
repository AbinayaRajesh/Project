package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.EventFragments.EventsListFragment;
import com.codepath.myapplication.EventFragments.EventsPagerAdapter;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;


import org.parceler.Parcels;

import java.util.Calendar;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;


public class EventActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
   FusedLocationProviderApi fusedLocationProviderApi;
    public static Country country;
    Location mLastLocation;
    Calendar c = Calendar.getInstance();
    private LocationRequest mLocationRequest;
    int seconds = c.get(Calendar.SECOND);
    String dateString = c.get(Calendar.YEAR) + "-" +
            c.get(Calendar.MONTH) + "-" +
            c.get(Calendar.DAY_OF_MONTH) + " " +
            c.get(Calendar.HOUR) + ":" +
            c.get(Calendar.MINUTE) + ":" +
            c.get(Calendar.SECOND);
    Double longitude;
    Double latitude;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        pageAdapter = new EventsPagerAdapter(getSupportFragmentManager(), context);
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
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)
                .setFastestInterval(1 * 1000);
        fusedLocationProviderApi = LocationServices.FusedLocationApi;
        googleApi = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApi.connect();

        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                String lat = String.valueOf(latitude);
                String lng = String.valueOf(longitude);
                ll = lat + "," + lng;
            }
        };
    }

    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        ll = lat + "," + lng;
        Log.i("Geo_Location", "Latitude: " + latitude + ", Longitude: " + longitude);
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
        Intent i = new Intent(this, tempDemoActivity.class);
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

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras\
            int i = data.getExtras().getInt("num", -1);

            // Event event = data.getExtras().getParcelable("event", event);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();

        }
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

    public String getFilter() {
        return filter;
    }

    public String getLL() {
        return ll;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, mLocationRequest, EventActivity.this);
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi);

        if (mLastLocation == null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, mLocationRequest, EventActivity.this);
        }
        else {
            onLocationChanged(mLastLocation);
        };
//        mLocationRequest = LocationRequest.create();
//        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        mLocationRequest.setInterval(10000); // Update location every second
//
//        LocationServices.FusedLocationApi.requestLocationUpdates(googleApi, mLocationRequest, this);
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi);
//        }
//        else{
//            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApi);
//        }
//
//        if (mLastLocation != null) {
//            onLocationChanged(mLastLocation);
//        }
    }
    @Override
    public void onConnectionSuspended(int i) {

    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    protected void onStart() {
        super.onStart();
        googleApi.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        googleApi.disconnect();
    }
    protected void onResume() {
        super.onResume();
        googleApi.connect();
    }
}
