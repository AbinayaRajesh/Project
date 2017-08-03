package com.codepath.myapplication.LanguageFragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.R;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.TranslateTextRequest;

import org.parceler.Parcels;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class tempLanguage extends AppCompatActivity {

    final TranslateRequestInitializer API_KEY = new TranslateRequestInitializer("AIzaSyC2FnmN0yck7fkTgtWLi1D6WfXpMZDfw30");
    HttpTransport httpTransport =  new com.google.api.client.http.javanet.NetHttpTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    ArrayList<String> translateText;
    EditText input;
    TextView translatedLanguage;
    Button translateButton;
    String translated;
    TranslateTextRequest queryTranslate;
    Country country;
    String ll;

    public tempLanguage() throws GeneralSecurityException, IOException {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        ll = getIntent().getStringExtra("ll");


        translateText = new ArrayList<>();
        input = (EditText) findViewById(R.id.etInputText);
        translateButton = (Button) findViewById(R.id.btnTranslate);
        translatedLanguage = (TextView) findViewById(R.id.tvTranslatedText);
        queryTranslate = new TranslateTextRequest();

        input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    translateButton.performClick();
                    return true;
                }
                return false;
            }
        });
        //translateButton.requestFocus();
        /*translateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                translateText.clear();
                String query = String.valueOf(input.getText());
                translateText.add(query);
                queryTranslate.setQ(translateText);
                queryTranslate.setSource("en");
                queryTranslate.setTarget("fr");
                final Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
                        .setApplicationName("My First Project")
                        .setTranslateRequestInitializer(API_KEY)
                        .build();
                new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            translated = String.valueOf(translate.translations().list(translateText, "fr").execute());
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }).start();
                translatedLanguage.setText(translated);

                //translated = String.valueOf((translate.translations().translate(queryTranslate).execute()));
            }
        });*/
    }
    public void Translate(View view) {
        translateText.clear();
        String query = String.valueOf(input.getText());
        translateText.add(query);
        queryTranslate.setQ(translateText);
        queryTranslate.setSource("en");
        queryTranslate.setTarget("fr");
        final Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
                .setApplicationName("My First Project")
                .setTranslateRequestInitializer(API_KEY)
                .build();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    translated = String.valueOf(translate.translations().list(translateText, "fr").execute());
                    if (translated !=null ){
                        translated = translated.substring(translated.indexOf("t\":\""));
                        translated = translated.substring(4, translated.length()-4);
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        translatedLanguage.setText(translated);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, tempDemoActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

}
