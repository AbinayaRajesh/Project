package com.codepath.myapplication.LanguageFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.R;

public class CommonPhrasesFragment extends LanguageMainActivity {

    public CommonPhrasesFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_common_phrases);
    }




    public void onMaps(MenuItem item) {
        Intent i = new Intent(getContext(), tempDemoActivity.class);
        startActivity(i);
    }


    public void onEvents(MenuItem item) {
        Intent i = new Intent(getContext(), FavouriteActivity.class);
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menumain, menu);
    }




}
