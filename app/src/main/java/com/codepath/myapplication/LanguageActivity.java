package com.codepath.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.LanguageFragments.LanguagePagerAdapter;

import org.parceler.Parcels;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by arajesh on 7/21/17.
 */

public class LanguageActivity extends AppCompatActivity{


    Context context;
    ViewPager vpPager;
    LanguagePagerAdapter pageAdapter;
    public static Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        try {
            pageAdapter = new LanguagePagerAdapter(getSupportFragmentManager(), this);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        context = this;
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);
        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(2);
    }

}


