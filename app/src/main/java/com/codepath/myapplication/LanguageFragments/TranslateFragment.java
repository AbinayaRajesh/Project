package com.codepath.myapplication.LanguageFragments;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.codepath.myapplication.R;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class TranslateFragment extends LanguageMainActivity {
    int option;
    static String language;
    public TranslateFragment() throws GeneralSecurityException, IOException {
        setHasOptionsMenu(true);
    }

    public void onCreate(Bundle savedInstanceState) {
        Bundle args = this.getArguments();
        if (args != null) {
            language = args.getString("language", "");
        }
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_translate, menu);
    }

}