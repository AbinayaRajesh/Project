package com.codepath.myapplication.Tourism;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.R;
import com.codepath.myapplication.TourismFragments.TourismPagerAdapter;

public class TourismActivity extends AppCompatActivity {

    Context context;
    ViewPager vpPager;
    TourismPagerAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);
        pageAdapter = new TourismPagerAdapter(getSupportFragmentManager(), this);
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
