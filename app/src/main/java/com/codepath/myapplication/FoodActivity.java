package com.codepath.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.FoodFragments.FoodPagerAdapter;



public class FoodActivity extends AppCompatActivity {
    Context context;
    ViewPager vpPager;
    FoodPagerAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        pageAdapter = new FoodPagerAdapter(getSupportFragmentManager(), this);
        context = this;
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpagerFood);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabsFood);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(2);
    }
}
