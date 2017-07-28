package com.codepath.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.SavedTourismActivity;
import com.codepath.myapplication.Database.TourismContract.TourismEntry;
import com.codepath.myapplication.Models.Location;
import com.codepath.myapplication.Models.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailPlaceActivity extends AppCompatActivity implements OnMapReadyCallback {
    Venue venue;

    TextView tvTitle;
    TextView tvDistance;
    TextView tvAddress;
    ImageView ivImg;
    Context context = this;
    GoogleMap googleMap;
    MapFragment mapFragment;
    ImageView i;
    Location location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_place);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        ivImg = (ImageView) findViewById(R.id.ivImg);
        //location = (Location) Parcels.unwrap(getIntent().getParcelableExtra("location"));


        venue = (Venue) getIntent().getParcelableExtra("venue");



        venue.setLocation(location);

        tvTitle.setText(venue.getTitle());
        tvDistance.setText(venue.getLocation().getDistance());
        tvAddress.setText(venue.getLocation().getCity());

        Glide.with(context)
                .load(venue.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 35, 0))
                .into(ivImg);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Venue database stuff

        i = (ImageButton) findViewById(R.id.add);
        i.setImageResource(R.drawable.add_white);
        if (venue.isFavourite()==1) {
            Glide.with(context) .load("") .error(R.drawable.remove_white) .into(i);
        }
        else {
            Glide.with(context) .load("") .error(R.drawable.add_white) .into(i);
        }

        i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(venue.isFavourite()==0){
                    i.setImageResource(R.drawable.remove_white);
                    insertVenue(venue);
                    Byte y = 1;
                    venue.setFavourite(y);
                    Intent in = new  Intent(DetailPlaceActivity.this, SavedTourismActivity.class);
                    startActivity(in);
                }
                else {
                    i.setImageResource(R.drawable.add_white);
                    deleteVenue(venue);
                    Byte y = 0;
                    venue.setFavourite(y);
                    Intent in = new  Intent(DetailPlaceActivity.this, SavedTourismActivity.class);
                    startActivity(in);
                }
            }
        });




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
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, NearbyActivity.class);
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

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving venue", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Venue saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

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
