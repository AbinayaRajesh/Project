package com.codepath.myapplication.LanguageFragments;

import org.parceler.Parcel;

/**
 * Created by arajesh on 6/22/17.
 */
@Parcel
public class Phrase {

    // values from API
    static String phrase;
    static String translation;

    // initialize from JSON data
    public Phrase(String phrase, String translation) {
        Phrase.phrase = phrase;
        Phrase.translation = translation;
    }

    public Phrase() {}

    public static String getPhrase() {
        return phrase;
    }

    public static String getTranslation() {
        return translation;
    }
}
