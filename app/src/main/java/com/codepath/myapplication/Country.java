package com.codepath.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilylroth on 7/11/17.
 */

public class Country {
    String name;
    String continent;
    String language;
    public Country(){}

    public Country(JSONObject object) throws JSONException{
        name = object.getString("name");
        continent = object.getString("continent");
        language = object.getString("language");
    }

}
