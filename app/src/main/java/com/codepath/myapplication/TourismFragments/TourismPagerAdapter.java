package com.codepath.myapplication.TourismFragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by eyobtefera on 7/20/17.
 */

public class TourismPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private EntertainmentTourismFragment etf;
    private StaycationTourismFragment stf;
    private ShoppingTourismFragment shtf;
    private String tableTitle[] = new String[]{"Entertainment", "Shopping", "Staycation"};
    public TourismPagerAdapter(FragmentManager fm, Context Context) {
        super(fm);
        etf = new EntertainmentTourismFragment();
        stf = new StaycationTourismFragment();
        shtf = new ShoppingTourismFragment();
    }
    public Fragment getItem(int position) {
        if (position == 0) {
            return etf;
        } else if (position == 1)
        {
           return shtf;
        } else if (position == 2) {
            return stf;
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
