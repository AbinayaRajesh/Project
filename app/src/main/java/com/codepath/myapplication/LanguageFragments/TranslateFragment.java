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




        // setContentView(R.layout.activity_language);
//        translateText = new ArrayList<>();
//        queryTranslate = new TranslateTextRequest();
//        input = (EditText) findViewById(R.id.etInputText);
//        translateButton = (Button) findViewById(R.id.btnTranslate);
//        translatedLanguage = (TextView) findViewById(R.id.tvTranslatedText);


    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menumain, menu);
//        return true;
//    }
//    public void onMaps(MenuItem item) {
//        Intent i = new Intent(this, NearbyActivity.class);
//        startActivity(i);
//    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menumain, menu);
    }




}
