package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.LanguageFragments.LanguagePagerAdapter;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;

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
    public int option = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        option = getIntent().getIntExtra("option", 0);
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

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_translate, menu);
//        return true;
//    }
//
//
//    public void onMaps(MenuItem item) {
//        Intent i = new Intent(this, tempDemoActivity.class);
//        startActivity(i);
//    }
//
//
//    public void onEvents(MenuItem item) {
//        Intent i = new Intent(this, FavouriteActivity.class);
//        startActivity(i);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//            case R.id.action_dropdown1: {
//                option = 1;
//                break;
//            }
//
//            case R.id.action_dropdown2: {
//                option = 2;
//                pageAdapter.notifyDataSetChanged();
//                break;
//            }
//            case R.id.action_dropdown3: {
//                option = 3;
//                pageAdapter.notifyDataSetChanged();
//                break;
//            }
//            case R.id.action_dropdown4: {
//                option = 4;
//                pageAdapter.notifyDataSetChanged();
//                break;
//            }
//            default:
//                return super.onOptionsItemSelected(item);
//
//        }
//        return true;
//    }
//
    public int getOption () {
        return option;
    }




    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, tempDemoActivity.class);
        startActivity(i);
    }


    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, OptionsActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        startActivity(i);
    }



}


