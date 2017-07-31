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
                String title;
                Phrase p;  // Phrase
                String s;  // Text
                String t = "Translation";  // Translation

                p = new Phrase("Where is the restroom?", "Où sont les toilettes?");
                phrases.add(p);

                // Greetings

                title = "Greetings (things that you say at the beginning of a conversation) seem " +
                        "straightforward and easy for English learners. However, they are essential " +
                        "to conversation. You need good greetings to be understood well while you are " +
                        "traveling an English-speaking country. Check out these most common ones.";


                s = "Good morning";
                phrases.add(new Phrase(s, TranslateWord(s)));
                s = "Good afternoon";
                s = "Good evening";
                s = "Hello / Hi / Hey";
                s = "How are you? / How is it going?";

                // At the Airport

                title = "When you go to an English-speaking country, your first encounters of " +
                        "English will probably be at an airport. Check out these phrases to make " +
                        "checking into your flight (letting the airline know you have arrived) a " +
                        "breeze!";

                s = "I would like ... ";
                s = "What time is my flight?";
                s = "What airline am I flying?";
                s = "Where is my gate?";
                s = "Where is the restroom?";
                s = "How much does the magazine cost?";

                // On the Airplane

                title = "So, you’ve made it through the airport, and you’re on the airplane. " +
                        "Check out these phrases for having a good flight.\n";

                s = "Are meals included?";
                s = "May I have something to eat/drink?";
                s = "May I purchase headphones?";
                s = "What time is it?";

                // At Customs

                title = "After the airport and the airplane comes the most stressful experiences for" +
                        " travelers: customs. This is the part where you have to explain why you " +
                        "have arrived in a country and tell officers what your intentions are. " +
                        "But don’t stress! These phrases will help you out.";

                s = "I have a connecting flight.";
                s = "I am traveling for leisure.";
                s = "I am traveling for work.";
                s = "I will be here for ___ days.";
                s = "I am visiting family.";
                s = "I am staying at _____.";

                // Arriving at Your Destination

                title = "After the air travel comes the real fun part: your destination (the " +
                        "place where you are visiting). These common phrases will help you get " +
                        "around and explore.";

                s = "Do you have a map?";
                s = "Where is the currency exchange?";
                s = "Where is the bus stop?";
                s = "Where can I find a taxi?";
                s = "I would like to go to _____.";
                s = "Do you know where this hotel is?";
                s = "I don’t understand.";

                // At the hotel

                title = "Aside from your flight, the next more important thing while abroad is " +
                        "your accommodation, and if you’re staying in a hotel and not with " +
                        "friends or family, the following phrases will come in handy.";
                s = "Does the room have a bathroom?";

                s = "How many beds are in the room?";
                s = "I would like one queen bed, please.";
                s = "I would like two double beds, please.";
                s = "What floor am I on?";
                s = "Where are the elevators?";
                s = "How do I access the Internet?";
                s = "Is there free breakfast?";
                s = "My room needs towels.";
                s = "My room is messy, and I would like it cleaned.";
                s = "How do I call for room service?";
                s = "How do I call down to the front desk?";

                // Around Town

                title = "Vocabulary for the airport and your hotel is fine, but you traveled " +
                        "to visit a new place! Check out these phrases to help you out while " +
                        "you’re exploring.";

                s = "Where can I find a grocery store?";
                s = "Where is the hospital?";
                s = "Where can I find a restaurant?";
                s = "Where is the bank?";
                s = "How do you get to ____?";
                s = "How far is it to _____?";
                s = "It’s to the right.";
                s = "It’s to the left.";
                s = "It’s straight ahead.";
                s = "It’s at the corner.";
                s = "It’s two blocks ahead (or three blocks, or four ...).";

                // At a restaurant

                title = "After a long day exploring, food is always a welcome break. Check " +
                        "out these helpful restaurant phrases and this post about ordering food.";

                s = "A table for two/four.";
                s = "I would like to drink ... ";
                s = "May I see a menu?";
                s = "I would like to order ____.";
                s = "Fill in the blank with an item off of the menu or one of these items: ";
                s = "I’ll have soup.";
                s = "I’ll have a salad.";
                s = "I’ll have a hamburger.";
                s = "I’ll have chicken.";
                s = "I’ll have an appetizer.";
                s = "I would like dessert.";
                s = "May I have the bill?";

                // Common Problems

                title = "Even with careful planning and these phrases, you may encounter some " +
                        "problems. Here are some phrases to help you out if something bad happens.";

                s = "I have lost my passport. ";
                s = "Where is the embassy for _____?";
                s = "Someone stole my money.";
                s = "Help!";
                s = "I feel really sick";
                s = "Could you help me get to the hospital?";





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
    public String TranslateWord(String query) {
        translateText.clear();
        String toBeReturned;
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
                        //textToBeSpoken = translatedLanguage.getText().toString();
                    }
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
        toBeReturned = translated;
        return toBeReturned;
    }
    public void SpeechSynthesis(String talk){
        textToBeSpoken = talk;
        if (textToBeSpoken==null || "".equals(textToBeSpoken)){
            textToBeSpoken = "Type something to be translated";
            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
        }
        else{
            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
        }
    }
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
