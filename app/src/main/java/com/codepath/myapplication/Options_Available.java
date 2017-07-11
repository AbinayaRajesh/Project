package com.codepath.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Options_Available extends AppCompatActivity {

    TextView tvTourism;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options__available);

        tvTourism = (TextView) findViewById(R.id.tvTourism);
        tvTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Options_Available.this, TourismActivity.class);
                startActivity(i);
            }
        });
    }
}
