package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.TranslateTextRequest;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;


public class LanguageActivity extends AppCompatActivity {

    final TranslateRequestInitializer API_KEY = new TranslateRequestInitializer("AIzaSyC2FnmN0yck7fkTgtWLi1D6WfXpMZDfw30");
    HttpTransport httpTransport =  new com.google.api.client.http.javanet.NetHttpTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    ArrayList<String> translateText;
    EditText input;
    TextView translatedLanguage;
    Button translateButton;
    String translated;
    TranslateTextRequest queryTranslate;

    public LanguageActivity() throws GeneralSecurityException, IOException {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        translateText = new ArrayList<>();
        input = (EditText) findViewById(R.id.etInputText);
        translateButton = (Button) findViewById(R.id.btnTranslate);
        translatedLanguage = (TextView) findViewById(R.id.tvTranslatedText);
        queryTranslate = new TranslateTextRequest();
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

}
