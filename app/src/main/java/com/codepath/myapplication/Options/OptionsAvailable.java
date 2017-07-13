package com.codepath.myapplication.Options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.ImageAdapter;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

public class OptionsAvailable extends AppCompatActivity {
        Country country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_available);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));



        GridView gridview = (GridView) findViewById(R.id.gridview);
        // gridview.setLayoutParams(new GridView.LayoutParams(300, 300));
        gridview.setAdapter(new ImageAdapter(this));



    }



}
