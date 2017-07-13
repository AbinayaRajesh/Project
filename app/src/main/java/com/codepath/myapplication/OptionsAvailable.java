package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class OptionsAvailable extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options_available);


        GridView gridview = (GridView) findViewById(R.id.gridview);
        // gridview.setLayoutParams(new GridView.LayoutParams(300, 300));
        gridview.setAdapter(new ImageAdapter(this));



    }



}
