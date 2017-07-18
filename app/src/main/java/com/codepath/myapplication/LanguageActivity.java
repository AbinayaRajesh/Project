package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;

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

    public LanguageActivity() throws GeneralSecurityException, IOException {
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        translateText = new ArrayList<>();
        input = (EditText) findViewById(R.id.etInputText);
        translatedLanguage = (TextView) findViewById(R.id.tvTranslatedText);
        input.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String query = String.valueOf(input.getText());
                    translateText.add(query);
                    Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
                            .setApplicationName("My First Project")
                            .setTranslateRequestInitializer(API_KEY)
                            .build();
                    try {
                        translatedLanguage.setText((CharSequence) translate.translations().list(translateText, "fr"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return true;
                }
                return false;
            }
        });
    }
}
