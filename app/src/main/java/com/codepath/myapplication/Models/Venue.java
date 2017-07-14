package com.codepath.myapplication.Models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by arajesh on 6/22/17.
 */
@Parcel
public class Venue {

    // values from API
    String title;
    Location location;


    // initialize from JSON data
    public Venue(JSONObject object) throws JSONException {
        title = object.getString("name");
        location = Location.fromJSON(object.getJSONObject("location"));


    }

    public Venue() {}

    public String getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }
}
