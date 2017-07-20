package com.codepath.myapplication.FoodFragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by eyobtefera on 7/19/17.
 */

public class FoodPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private RecipesFragment rf;
    private RestarauntFragment rtf;

    private String tableTitle[] = new String[] {"Recipies", "Restaurant"};
    // return the total # of fragment

    public FoodPagerAdapter(FragmentManager fm, Context Context){
        super(fm);
        rf = new RecipesFragment();
        rtf = new RestarauntFragment();
        this.context = context;
    }
    @Override
    public int getCount() {
        return 2;
    }

    //  return the fragment to use depending on the position

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return rf;
        } else if (position == 1) {
            return rtf;
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tableTitle[position];
    }
    // return title
}
