package com.codepath.myapplication.LanguageFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.myapplication.R;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.TranslateTextRequest;

import java.util.ArrayList;

public class LanguageMainActivity extends Fragment {

    final TranslateRequestInitializer API_KEY = new TranslateRequestInitializer("AIzaSyC2FnmN0yck7fkTgtWLi1D6WfXpMZDfw30");
    HttpTransport httpTransport =  new com.google.api.client.http.javanet.NetHttpTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    View rootView;

    EditText input;
    TextView translatedLanguage;
    Button translateButton;
    ArrayList<String> translateText;
    TranslateTextRequest queryTranslate;
    String translated;
    int i;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        try {
            i = getArguments().getInt("f");
        }
        catch(Exception e){
            i = 1;
        }

        switch (i)
        {
            case 0: {
                rootView = inflater.inflate(R.layout.activity_translate, container, false);
                translateText = new ArrayList<>();
                queryTranslate = new TranslateTextRequest();
                input = (EditText) rootView.findViewById(R.id.etInputText);
                translateButton = (Button) rootView.findViewById(R.id.btnTranslate);
                translatedLanguage = (TextView) rootView.findViewById(R.id.tvTranslatedText);
                translateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TranslateText(view);
                    }
                });
                break;
            }
            case 1: {
                rootView = inflater.inflate(R.layout.activity_common_phrases, container, false);
                break;
            }
        }
        return rootView;
    }

    public void TranslateText(View view) {
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
