package com.codepath.myapplication.LanguageFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by eyobtefera on 7/18/17.
 */

public class LanguagePagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private TranslateFragment tf;
    private CommonPhrasesFragment cpf;

    private String tableTitle[] = new String[]{"Translate", "Common Phrases"};

    public LanguagePagerAdapter(FragmentManager fm, Context Context) throws GeneralSecurityException, IOException {
        super(fm);
        cpf = new CommonPhrasesFragment();
        tf = new TranslateFragment();


    }

    public Fragment getItem(int position) {
        if (position == 0) {

            Bundle args1 = new Bundle();
            args1.putInt("f", 1);
            cpf.setArguments(args1);
            return cpf;
        } else
        {


                Bundle args = new Bundle();
                args.putInt("f", 0);
                cpf.setArguments(args);


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
