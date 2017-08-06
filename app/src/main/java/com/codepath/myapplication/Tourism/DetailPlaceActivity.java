package com.codepath.myapplication.Tourism;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.SavedTourismActivity;
import com.codepath.myapplication.Database.TourismContract.TourismEntry;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Models.Location;
import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

public class DetailPlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    Venue venue;

    TextView tvTitle;
    TextView tvDistance;
    TextView tvAddress;
    ImageView ivImg;
    Context context = this;
    GoogleMap googleMap;
    MapFragment mapFragment;
    TextView i;
    String ll;
    Country country;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_place);

        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        ll = getIntent().getStringExtra("ll");

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        ivImg = (ImageView) findViewById(R.id.ivImg);
        location = (Location) getIntent().getParcelableExtra("location");
        venue = (Venue) getIntent().getParcelableExtra("venue");



        venue.setLocation(location);

        tvTitle.setText(venue.getTitle());
        // tvDistance.setText(String.valueOf(location.getDistance()));
        String s = "";
        for (int i=0; i<location.getFormattedAddress().length; i++) {
            s += location.getFormattedAddress()[i] + " \n";
        }
        tvAddress.setText(s);

        Glide.with(context)
                .load(venue.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 35, 0))
                .into(ivImg);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Venue database stuff

        i = (TextView) findViewById(R.id.add);
        i.setText("+");
        if (venue.isFavourite()==1) {
            i.setText("_");
        }
        else {
            i.setText("+");
        }

        i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(venue.isFavourite()==0){
                    i.setText("_");
                    insertVenue(venue);
                    Byte y = 1;
                    venue.setFavourite(y);
                    Intent in = new  Intent(DetailPlaceActivity.this, SavedTourismActivity.class);
                    startActivity(in);
                }
                else {
                    i.setText("+");
                    deleteVenue(venue);
                    Byte y = 0;
                    venue.setFavourite(y);
                    Intent in = new  Intent(DetailPlaceActivity.this, SavedTourismActivity.class);
                    startActivity(in);
                }
            }
        });

        // FACEBOOK SHARING

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
                    ShareLinkContent Content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("www.google.com"))
                            .setShareHashtag(new ShareHashtag.Builder()
                                    .setHashtag("#Staycation")
                                    .build())

                            .build();

                    shareButton.setShareContent(Content);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();




    }

    @Override
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //

        LatLng placeLocation = new LatLng(venue.getLocation().getLat(), venue.getLocation().getLng()); //Make them global
        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title(venue.getTitle()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        setMenuVolume(menu,3);
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


    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }


    private void insertVenue(Venue venue) {

        deleteVenue(venue);

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        // mNameEditText.getText().toString().trim();


        String nameString = venue.getTitle();
        String urlString = venue.getImageUrl();
        String cityString = venue.getLocation().getCity();
        String stateString = venue.getLocation().getState();
        float lat = (float) venue.getLocation().getLat();
        float lng = (float) venue.getLocation().getLng();
        int dist = venue.getLocation().getDistance();

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(TourismEntry.COLUMN_TOURISM_NAME, nameString);
        values.put(TourismEntry.COLUMN_TOURISM_URL, urlString);
        values.put(TourismEntry.COLUMN_TOURISM_CITY, cityString);
        values.put(TourismEntry.COLUMN_TOURISM_STATE, stateString);
        values.put(TourismEntry.COLUMN_TOURISM_LAT, lat);
        values.put(TourismEntry.COLUMN_TOURISM_LNG, lng);
        values.put(TourismEntry.COLUMN_TOURISM_DISTANCE, dist);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(TourismEntry.TABLE_NAME, null, values);



        // pass back data

        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        int t = venue.getNum();
        data.putExtra("num", t);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }

    private void deleteVenue(Venue venue) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + TourismEntry.TABLE_NAME +
                " WHERE " + TourismEntry.COLUMN_TOURISM_NAME + " = \"" + venue.getTitle() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }





}
