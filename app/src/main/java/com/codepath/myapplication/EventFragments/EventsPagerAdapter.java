package com.codepath.myapplication.EventFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.codepath.myapplication.Event.EventActivity;

/**
 * Created by eyobtefera on 7/18/17.
 */

//creates the page adapter that allows the tabbing between fragments

public class EventsPagerAdapter extends FragmentStatePagerAdapter {


    String country = EventActivity.country.getName();
    String adjective = EventActivity.country.getAdjective();
    private Context context;
    private MusicEventsFragment mef;
    private SportsEventsFragment sef;
    private FestivalsEventsFragment fef;
    private String tableTitle[] = new String[]{"Music", "Sports", "Festivals"};
    //creates everything as well as overrides a few methods to ensure fragment are created properly
    public EventsPagerAdapter(FragmentManager fm, Context Context) {
        super(fm);
        mef = new MusicEventsFragment();
        sef = new SportsEventsFragment();
        fef = new FestivalsEventsFragment();
        Bundle bundle = new Bundle();
        if (adjective == null) {
            bundle.putString("country", country);
        }
        else {
            bundle.putString("country", adjective);
        }
        mef.setArguments(bundle);
        sef.setArguments(bundle);
        fef.setArguments(bundle);
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return mef;
        } else if (position == 1) {
            return sef;
        } else if (position == 2) {
            return fef;
        } else {
            return null;
        }
    }
    @Override
    public int getItemPosition(Object object) {

        return POSITION_NONE;
    }
    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tableTitle[position];
    }
}
