package com.codepath.myapplication.LanguageFragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.myapplication.R;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.translate.Translate;
import com.google.api.services.translate.TranslateRequestInitializer;
import com.google.api.services.translate.model.TranslateTextRequest;

import java.util.ArrayList;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.facebook.FacebookSdk.getApplicationContext;

public class LanguageMainActivity extends Fragment {

    final TranslateRequestInitializer API_KEY = new TranslateRequestInitializer("AIzaSyC2FnmN0yck7fkTgtWLi1D6WfXpMZDfw30");
    HttpTransport httpTransport =  new com.google.api.client.http.javanet.NetHttpTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

    String language;
    View rootView;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    EditText input;
    TextView translatedLanguage;
    Button translateButton;
    ArrayList<String> translateText;
    TranslateTextRequest queryTranslate;
    String translated;
    TextToSpeech textTalk;
    int i;
    RecyclerView rvPhrases;
    PhrasesAdapter adapter;
    Button tts;
    String textToBeSpoken;
    Button speechToText;
    SpeechRecognizer sr;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        try {
            i = getArguments().getInt("f");
        }
        catch(Exception e){
            i = 1;
        }

        switch (i)
        {
            case 1: {
                language=TranslateFragment.language;
                rootView = inflater.inflate(R.layout.activity_translate, container, false);
                translateText = new ArrayList<>();
                queryTranslate = new TranslateTextRequest();
                input = (EditText) rootView.findViewById(R.id.etInputText);
                translateButton = (Button) rootView.findViewById(R.id.btnTranslate);
                translatedLanguage = (TextView) rootView.findViewById(R.id.tvTranslatedText);

                tts = (Button) rootView.findViewById(R.id.btnTTS);
                speechToText = (Button) rootView.findViewById(R.id.btnSTT);
                textTalk = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int i) {
                        if(i == TextToSpeech.SUCCESS)
                        {
                           int result = textTalk.setLanguage(Locale.US);
                            if(result==TextToSpeech.LANG_MISSING_DATA ||
                                    result==TextToSpeech.LANG_NOT_SUPPORTED){
                                Log.e("error", "This Language is not supported");
                            }
                            else{
                                SpeechSynthesis();
                            }
                        }
                    }
                });
                translateButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TranslateText();
                    }
                });
                tts.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view) {
                        SpeechSynthesis();
                    }
                });
                speechToText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        promptSpeechInput();
                    }
                });
                break;
            }
            case 0: {
                rootView = inflater.inflate(R.layout.activity_common_phrases, container, false);
                ArrayList<Phrase> phrases = new ArrayList<Phrase>();
                //String s1 = "How are you? / How is it going? ";
                //String t1 = Translate(s1);
                //Phrase p1 = new Phrase(s1,t1);
                //phrases.add(p1);
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



    public void TranslateText() {
        translateText.clear();
        String query = String.valueOf(input.getText());
        translateText.add(query);
        queryTranslate.setQ(translateText);
        queryTranslate.setSource("en");
        queryTranslate.setTarget(language);

        // queryTranslate.setTarget("fr");
        final Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
                .setApplicationName("My First Project")
                .setTranslateRequestInitializer(API_KEY)
                .build();
        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    translated = String.valueOf(translate.translations().list(translateText, language).execute());
                    if (translated !=null ){
                        translated = translated.substring(translated.indexOf("t\":\""));
                        translated = translated.substring(4, translated.length()-4);
                        translatedLanguage.setText(translated);
                        textToBeSpoken = translated;
                        //textToBeSpoken = translatedLanguage.getText().toString();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

//    public String Translate(String query) {
//        translateText.clear();
//        translateText.add(query);
//        queryTranslate.setQ(translateText);
//        queryTranslate.setSource("en");
//        queryTranslate.setTarget(language);
//
//        final Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
//                .setApplicationName("My First Project")
//                .setTranslateRequestInitializer(API_KEY)
//                .build();
//        new Thread(new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    translated = String.valueOf(translate.translations().list(translateText, language).execute());
//                    if (translated !=null ){
//                        translated = translated.substring(translated.indexOf("t\":\""));
//                        translated = translated.substring(4, translated.length()-4);
//                    }
//                }
//                catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }).start();
//
//        return translated;
//    }

    public void SpeechSynthesis(){
        textToBeSpoken = translatedLanguage.getText().toString();
        if (textToBeSpoken==null || "".equals(textToBeSpoken)){
            textToBeSpoken = "Type something to be translated";
            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Say less");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    "speech not supported sorry friend",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    final ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    input.setText(result.get(0));

                    result.clear();
                }
                break;
            }

        }
    }



}
