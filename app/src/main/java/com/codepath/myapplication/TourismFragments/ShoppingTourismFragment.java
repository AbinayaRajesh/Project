package com.codepath.myapplication.TourismFragments;

import android.os.Bundle;

/**
 * Created by eyobtefera on 7/20/17.
 */

public class ShoppingTourismFragment extends TourismListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getVenue("4d4b7105d754a06378d81259", "Mall");
    }
}
