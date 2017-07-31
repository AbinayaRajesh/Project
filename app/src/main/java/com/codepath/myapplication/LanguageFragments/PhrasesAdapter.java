package com.codepath.myapplication.LanguageFragments;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.R;

import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by eyobtefera on 7/11/17.
 */

public class PhrasesAdapter extends RecyclerView.Adapter<PhrasesAdapter.ViewHolder> {

    private ArrayList<Phrase> mPhrases;
    Context context;
    TextToSpeech textTalk;

    public PhrasesAdapter(ArrayList<Phrase> phrases) {
        this.mPhrases = phrases;
    }

    //pass in the Tweets array in the constructor
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_translate_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //get the data according to position
        Phrase phrase = mPhrases.get(position);
        //populate the views according to this data
        holder.tvPhrase.setText(phrase.getPhrase());
        holder.tvTranslation.setText(phrase.getTranslation());

    }
    @Override
    public int getItemCount() {
        return mPhrases.size();
    }
    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView tvPhrase;
        public TextView tvTranslation;
        public ImageView ivPlay;
        public RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            tvPhrase = (TextView) itemView.findViewById(R.id.tvPhrase);
            tvTranslation = (TextView) itemView.findViewById(R.id.tvTranslation);
            ivPlay = (ImageView) itemView.findViewById(R.id.ivPlay);
            ivPlay.setOnClickListener(this);

        }
        public void onClick(View v) {

            Glide.with(context) .load("") .error(R.drawable.play_pressed) .into(ivPlay);

            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {

                Phrase phrase = mPhrases.get(position);
                //SpeechSynthesis("hello, how are you");


            }

            Glide.with(context) .load("") .error(R.drawable.ic_play) .into(ivPlay);
        }
    }

    public void SpeechSynthesis(final String textToBeSpoken){

        textTalk = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
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

                        if (textToBeSpoken==null || "".equals(textToBeSpoken)){
                            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
                        }
                        else{
                            textTalk.speak(textToBeSpoken, TextToSpeech.QUEUE_FLUSH, null);
                        }
                    }
                }
            }
        });

    }

//    public String TranslateWord(String query) {
//        final TranslateRequestInitializer API_KEY = new TranslateRequestInitializer("AIzaSyC2FnmN0yck7fkTgtWLi1D6WfXpMZDfw30");
//        HttpTransport httpTransport =  new com.google.api.client.http.javanet.NetHttpTransport();
//        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
//        final ArrayList<String> translateText;
//        TranslateTextRequest queryTranslate;
//        translateText = new ArrayList<>();
//        String translated;
//        queryTranslate = new TranslateTextRequest();
//        //if (translateText != null || (!translateText.equals(""))) {
//        translateText.clear();
//        //}
//        String toBeReturned;
//        translateText.add(query);
//        queryTranslate.setQ(translateText);
//        queryTranslate.setSource("en");
//        queryTranslate.setTarget(language);
//        // queryTranslate.setTarget("fr");
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
//                        //textToBeSpoken = translatedLanguage.getText().toString();
//                    }
//                }
//                catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        }).start();
//        toBeReturned = translated;
//        return toBeReturned;
//    }
}
