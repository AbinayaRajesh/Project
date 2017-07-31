package com.codepath.myapplication.LanguageFragments;

import org.parceler.Parcel;

/**
 * Created by arajesh on 6/22/17.
 */
@Parcel
public class Phrase {

    // values from API
    String phrase;
    String translation;

    // initialize from JSON data
    public Phrase(String p, String t) {
        phrase = p;
        translation = t;
    }

    public Phrase() {}

    public String getPhrase() {
        return phrase;
    }

    public String getTranslation() {
        return translation;
    }
}
