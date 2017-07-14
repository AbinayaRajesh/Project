package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Event.EventRetriever;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Events");
        new EventRetriever().execute();

    }
}
