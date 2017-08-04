package com.codepath.myapplication.Options;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.ImageAdapterSwipe;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Photo;
import com.codepath.myapplication.PhotoClient;
import com.codepath.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OptionsActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private RecyclerView rvOptions;
    private OptionsAdapter mAdapter;
    private List<Option> options;
    public Country country;
    public ArrayList<Photo> photos;
    AsyncHttpClient client;
    public static List<String> urls;
    Context context;
    ImageAdapterSwipe sAdapter;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    CarouselView carouselView;
    Double longitude;
    Double latitude;
    String ll;
    public final static String TAG = "CountryList";


    //  public PhotoClient photoClient;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        urls = new ArrayList<>();

        context = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_options);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        setTitle(country.getName());
        // Find RecyclerView and bind to adapter
        rvOptions = (RecyclerView) findViewById(R.id.rvOptions);

        // allows for optimizations
        rvOptions.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(OptionsActivity.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvOptions.setLayoutManager(layout);

        // geAt data
        options = Option.getContacts();
        this.client = new AsyncHttpClient();
        url = PhotoClient.createURL(country.getName());
     //   getPhoto(url);

        // Create an adapter
        mAdapter = new OptionsAdapter(OptionsActivity.this, options);
        mAdapter.optionsActivity = this;

        // Bind adapter to list
        rvOptions.setAdapter(mAdapter);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(5);
        carouselView.setImageListener(imageListener);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, final ImageView imageView) {
            client.get(url, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONObject results = response.getJSONObject("photos");
                        for (int i=0; i<5; i++) {
                            JSONArray photoStream = results.getJSONArray("photo");
                            Photo photo = Photo.fromJson(photoStream.getJSONObject(i));

                            photos.add(photo);
                            urls.add("https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg");
                            //notify adapter
                            // adapter.notifyItemInserted(countries.size()-1);
                            //      Glide.with(context)
                            //              .load("https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg")
                            //              .into((ImageView) findViewById(R.id.ivOptionsImage));
                        }
                        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

                        sAdapter = new ImageAdapterSwipe(OptionsActivity.this);
                        viewPager.setAdapter(sAdapter);
                        Glide.with(context)
                                .load(OptionsActivity.urls.get(position))
                                .into(imageView);

                    } catch (JSONException e) {
                        // logError("Failed to parse now playing movies", e, true);
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
    };




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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain, menu);

        return true;
    }


    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            longitude = mLocation.getLongitude();
            latitude = mLocation.getLatitude();
            String lat = String.valueOf(latitude);
            String lng = String.valueOf(longitude);
            ll = lat + "," + lng;
            mAdapter.changeLL(ll);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(2*1000)
                .setFastestInterval(2000);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        ll = lat + "," + lng;
    }
    public String getLL() {
        return ll;
    }
}




