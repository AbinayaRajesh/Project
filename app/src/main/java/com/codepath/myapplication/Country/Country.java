package com.codepath.myapplication.Country;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilylroth on 7/11/17.
 */

public class Country {
    String name;
    String continent;
    String language;
    String flagUrl;
    String nativeName;
    public Country(){}

    public static Country fromJSON(JSONObject object) throws JSONException{
        Country country = new Country();
        country.name = object.getString("Name");
        country.continent = object.getString("Region");
        country.language = object.getString("NativeLanguage");
        country.flagUrl = object.getString("FlagPng");
        country.nativeName = object.getString("NativeName");
        return country;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getLanguage() {
        return language;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public String getNativeName() {
        return nativeName;
    }
}
