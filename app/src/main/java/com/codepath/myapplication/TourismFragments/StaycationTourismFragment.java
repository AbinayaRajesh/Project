package com.codepath.myapplication.TourismFragments;

import android.os.Bundle;

/**
 * Created by eyobtefera on 7/20/17.
 */

public class StaycationTourismFragment extends TourismListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVenue("4d4b7105d754a06375d81259,4d4b7105d754a06377d81259,4d4b7105d754a06376d81259");
    }

}
