package com.codepath.myapplication.FoodFragments;

import android.os.Bundle;

/**
 * Created by eyobtefera on 7/19/17.
 */

public class RestarauntFragment extends FoodListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRestaraunt("indian");

    }
}
