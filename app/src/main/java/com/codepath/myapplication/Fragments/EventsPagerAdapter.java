package com.codepath.myapplication.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by eyobtefera on 7/18/17.
 */

public class EventsPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private MusicEventsFragment mef;
    private SportsEventsFragment sef;
    private FestivalsEventsFragment fef;
    private String tableTitle[] = new String[]{"Music", "Sports", "Festivals"};

    public EventsPagerAdapter(FragmentManager fm, Context Context) {
        super(fm);
        mef = new MusicEventsFragment();
        sef = new SportsEventsFragment();
        fef = new FestivalsEventsFragment();
    }

    public Fragment getItem(int position) {
        if (position == 0) {
            return mef;
        } else if (position == 1)
        {
            return sef;
        } else if (position == 2) {
            return fef;
        } else {
            return null;
        }
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
