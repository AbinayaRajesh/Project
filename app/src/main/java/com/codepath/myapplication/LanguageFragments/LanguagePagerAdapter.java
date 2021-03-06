package com.codepath.myapplication.LanguageFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.myapplication.LanguageActivity;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by eyobtefera on 7/18/17.
 */

public class LanguagePagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private TranslateFragment tf;
    private CommonPhrasesFragment cpf;
    String countryCode = LanguageActivity.country.getLanguage();

    private String tableTitle[] = new String[]{ "Common Phrases", "Translate"};

    public LanguagePagerAdapter(FragmentManager fm, Context Context) throws GeneralSecurityException, IOException {
        super(fm);
        cpf = new CommonPhrasesFragment();
        tf = new TranslateFragment();
    }

    public Fragment getItem(int position) {
        if (position == 0) {

            Bundle args1 = new Bundle();
            args1.putInt("f", 1);
            args1.putString("language", countryCode);
            cpf.setArguments(args1);
            return cpf;
        } else
        {
                Bundle args = new Bundle();
                args.putInt("f", 0);
                args.putString("language", countryCode);
                tf.setArguments(args);
            return tf;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
    public CharSequence getPageTitle(int position) {
        // generate title based on item position
        return tableTitle[position];
    }
}
