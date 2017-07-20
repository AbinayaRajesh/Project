package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Models.Venue;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.parceler.Parcels;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_place);

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDistance = (TextView) findViewById(R.id.tvDistance);
        tvAddress = (TextView) findViewById(R.id.tvAddress);
        ivImg = (ImageView) findViewById(R.id.ivImg);


        venue = (Venue) Parcels.unwrap(getIntent().getParcelableExtra("venue"));

        tvTitle.setText(venue.getTitle());
        tvDistance.setText(venue.getLocation().getDistance());
        tvAddress.setText(venue.getLocation().getCity());

        Glide.with(context)
                .load(venue.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 35, 0))
                .into(ivImg);


        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



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




}
