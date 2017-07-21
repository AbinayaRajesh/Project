package com.codepath.myapplication.LanguageFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    RecyclerView rvPhrases;
    PhrasesAdapter adapter;

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
                ArrayList<Phrase> phrases = new ArrayList<Phrase>();
                Phrase p1 = new Phrase("How are you? / How is it going? ", "Comment allez-vous?");
                phrases.add(p1);
                Phrase p2 = new Phrase("Where is the restroom?", "Où sont les toilettes?");
                phrases.add(p2);
                Phrase p3 = new Phrase("How much does the magazine cost?", "Combien coûte le magazine?");
                phrases.add(p3);
                Phrase p4 = new Phrase("What time is it? ", "Quelle heure est-il?");
                phrases.add(p4);
                Phrase p5 = new Phrase("I am visiting family. ", "Je visite la famille");
                phrases.add(p5);
                Phrase p6 = new Phrase("Where is the currency exchange? ", "Où est l'échange de devises?");
                phrases.add(p6);
                adapter = new PhrasesAdapter(phrases);
                //resolve the recycler view and connect a layout manager and the adapter
                rvPhrases = (RecyclerView) rootView.findViewById(R.id.rvPhrases);
                rvPhrases.setLayoutManager(new LinearLayoutManager(getContext()));
                rvPhrases.setAdapter(adapter);
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
