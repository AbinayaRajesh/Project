package com.codepath.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Fragments.EventsPagerAdapter;

public class EventActivity extends AppCompatActivity {

    Context context;
    private SwipeRefreshLayout swipeContainer;
    ViewPager vpPager;
    EventsPagerAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        pageAdapter = new EventsPagerAdapter(getSupportFragmentManager(), this);
        context = this;
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(3);

    }
}
