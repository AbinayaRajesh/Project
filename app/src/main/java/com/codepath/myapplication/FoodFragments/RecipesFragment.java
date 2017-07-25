package com.codepath.myapplication.FoodFragments;

import android.os.Bundle;

/**
 * Created by eyobtefera on 7/19/17.
 */

public class RecipesFragment extends FoodListFragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fetchFood("indian");
    }
}
