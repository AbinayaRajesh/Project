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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.myapplication.LanguageActivity;
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
import static com.codepath.myapplication.Options.OptionsActivity.pausePlayer;
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
    static PhrasesAdapter adapter;
    ImageView tts;
    String textToBeSpoken;
    ImageView speechToText;
    SpeechRecognizer sr;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        pausePlayer();

        try {
            i = getArguments().getInt("f");
        }
        catch(Exception e){
            i = 1;
        }

        switch (i)
        {
            case 0: {
                language=TranslateFragment.language;
                rootView = inflater.inflate(R.layout.activity_translate, container, false);

                translateText = new ArrayList<>();
                queryTranslate = new TranslateTextRequest();
                input = (EditText) rootView.findViewById(R.id.etInputText);
                translateButton = (Button) rootView.findViewById(R.id.btnTranslate);
                translatedLanguage = (TextView) rootView.findViewById(R.id.tvTranslatedText);

                tts = (ImageView) rootView.findViewById(R.id.btnTTS);
                speechToText = (ImageView) rootView.findViewById(R.id.btnSTT);
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
            case 1: {
                language=TranslateFragment.language;
                rootView = inflater.inflate(R.layout.activity_common_phrases, container, false);

                translateText = new ArrayList<>();
                queryTranslate = new TranslateTextRequest();

                String title;
                String s;  // Text
                // Greetings
                int option = ((LanguageActivity) getActivity()).option; // ((LanguageActivity) getActivity()).getOption();



                if (option==2){


                    // At the Airport

                    ArrayList<Phrase> AirportPhrases = new ArrayList<Phrase>();

                    s = "I would like ... ";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "What time is my flight?";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "What airline am I flying?";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is my gate?";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the restroom?";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How much does the magazine cost?";
                    AirportPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(AirportPhrases);
                }

                else if (option==3) {

                    // On the Airplane

                    ArrayList<Phrase> AirplanePhrases = new ArrayList<Phrase>();

                    s = "Are meals included?";
                    AirplanePhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "May I have something to eat/drink?";
                    AirplanePhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "May I purchase headphones?";
                    AirplanePhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "What time is it?";
                    AirplanePhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(AirplanePhrases);
                }

                else if (option==4) {

                    // At Customs

                    ArrayList<Phrase> CustomsPhrases = new ArrayList<Phrase>();

                    title = "After the airport and the airplane comes the most stressful experiences for" +
                            " travelers: customs. This is the part where you have to explain why you " +
                            "have arrived in a country and tell officers what your intentions are. " +
                            "But don’t stress! These GreetingPhrases will help you out.";

                    s = "I have a connecting flight.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I am traveling for leisure.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I am traveling for work.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I will be here for ___ days.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I am visiting family.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I am staying at _____.";
                    CustomsPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(CustomsPhrases);

                }

                else if (option==5) {

                    // Arriving at Your Destination

                    ArrayList<Phrase> DestinationPhrases = new ArrayList<Phrase>();

                    title = "After the air travel comes the real fun part: your destination (the " +
                            "place where you are visiting). These common GreetingPhrases will help you get " +
                            "around and explore.";

                    s = "Do you have a map?";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the currency exchange?";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the bus stop?";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where can I find a taxi?";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like to go to _____.";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Do you know where this hotel is?";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I don’t understand.";
                    DestinationPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(DestinationPhrases);

                }

                else if (option==6) {

                    // At the hotel

                    ArrayList<Phrase> HotelPhrases = new ArrayList<Phrase>();

                    title = "Aside from your flight, the next more important thing while abroad is " +
                            "your accommodation, and if you’re staying in a hotel and not with " +
                            "friends or family, the following GreetingPhrases will come in handy.";

                    s = "Does the room have a bathroom?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How many beds are in the room?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like one queen bed, please.";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like two double beds, please.";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "What floor am I on?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where are the elevators?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How do I access the Internet?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Is there free breakfast?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "My room needs towels.";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "My room is messy, and I would like it cleaned.";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How do I call for room service?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How do I call down to the front desk?";
                    HotelPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(HotelPhrases);

                }

                else if (option==7) {

                    // Around Town

                    ArrayList<Phrase> TownPhrases = new ArrayList<Phrase>();

                    title = "Vocabulary for the airport and your hotel is fine, but you traveled " +
                            "to visit a new place! Check out these GreetingPhrases to help you out while " +
                            "you’re exploring.";

                    s = "Where can I find a grocery store?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the hospital?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where can I find a restaurant?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the bank?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How do you get to ____?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How far is it to _____?";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "It’s to the right.";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "It’s to the left.";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "It’s straight ahead.";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "It’s at the corner.";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "It’s two blocks ahead (or three blocks, or four ...).";
                    TownPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(TownPhrases);

                }

                else if (option==8) {

                    // At a restaurant

                    ArrayList<Phrase> RestaurantPhrases = new ArrayList<Phrase>();

                    title = "After a long day exploring, food is always a welcome break. Check " +
                            "out these helpful restaurant GreetingPhrases and this post about ordering food.";

                    s = "A table for two/four.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like to drink ... ";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "May I see a menu?";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like to order ____.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Fill in the blank with an item off of the menu or one of these items: ";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I’ll have soup.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I’ll have a salad.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I’ll have a hamburger.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I’ll have chicken.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I’ll have an appetizer.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I would like dessert.";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "May I have the bill?";
                    RestaurantPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(RestaurantPhrases);

                }
                else if (option==9){

                    // Common Problems

                    ArrayList<Phrase> ProblemPhrases = new ArrayList<Phrase>();

                    title = "Even with careful planning and these GreetingPhrases, you may encounter some " +
                            "problems. Here are some GreetingPhrases to help you out if something bad happens.";

                    s = "I have lost my passport. ";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Where is the embassy for _____?";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Someone stole my money.";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Help!";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "I feel really sick";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Could you help me get to the hospital?";
                    ProblemPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(ProblemPhrases);
                }

                else {

                    ArrayList<Phrase> GreetingPhrases = new ArrayList<Phrase>();

                    s = "Good morning";
                    GreetingPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Good afternoon";
                    GreetingPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Good evening";
                    GreetingPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "Hello / Hi / Hey";
                    GreetingPhrases.add(new Phrase(s, TranslateWord(s)));
                    s = "How are you? / How is it going?";
                    GreetingPhrases.add(new Phrase(s, TranslateWord(s)));

                    adapter = new PhrasesAdapter(GreetingPhrases);
                }


                //resolve the recycler view and connect a layout manager and the adapter
                rvPhrases = (RecyclerView) rootView.findViewById(R.id.rvPhrases);
                rvPhrases.setLayoutManager(new LinearLayoutManager(getContext()));
                rvPhrases.setAdapter(adapter);

                break;
            }
        }
        return rootView;
    }



    //    @Override
//    public void onDestroy() {
//
//
//        //Close the Text to Speech Library
//        if(textTalk != null) {
//
//           textTalk.stop();
//            textTalk.shutdown();
//            Log.d(TAG, "TextTalk Destroyed");
//        }
//        super.onDestroy();
//    }

    public void TranslateText() {
        translateText.clear();
        String query = String.valueOf(input.getText());
        translateText.add(query);
        // queryTranslate.setFormat("text");
        queryTranslate.setQ(translateText);
        queryTranslate.setSource("en");
        queryTranslate.setTarget(language);

        // queryTranslate.setTarget("fr");
        final Translate translate = new Translate.Builder(httpTransport, jsonFactory, null)
                .setApplicationName("My First Project")
                .setTranslateRequestInitializer(API_KEY)
                .build();
        Thread t1  = new Thread(new Runnable(){
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
        });
        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public String TranslateWord(String query) {
        // Boolean b = true;
        //if (translateText != null || (!translateText.equals(""))) {
            translateText.clear();
        //}
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
        Thread t = new Thread(new Runnable(){
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
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        toBeReturned = translated;
        return toBeReturned;
    }

    public void SpeechSynthesis(){
        textToBeSpoken = translatedLanguage.getText().toString();
        if (textToBeSpoken==null || "".equals(textToBeSpoken)){
            //textToBeSpoken = "Type something to be translated";
            //textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
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
                "Talk");
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
                    TranslateText();
                    SpeechSynthesis();
                    result.clear();
                }
                break;
            }

        }
    }






}
