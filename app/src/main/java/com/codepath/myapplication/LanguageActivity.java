package com.codepath.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.codepath.myapplication.LanguageFragments.LanguagePagerAdapter;

/**
 * Created by arajesh on 7/21/17.
 */

public class LanguageActivity extends AppCompatActivity{


    Context context;
    ViewPager vpPager;
    LanguagePagerAdapter pageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
    }

    }


