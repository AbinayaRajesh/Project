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
    String id;
    String imageUrl;


    // initialize from JSON data
    public Venue(JSONObject object) throws JSONException {
        title = object.getString("name");
        location = Location.fromJSON(object.getJSONObject("location"));
        id = object.getString("id");
        //imageUrl = "https://api.foursquare.com/v2/venues/"+id;


    }

    public Venue() {}

    public String getTitle() {
        return title;
    }

    public Location getLocation() {
        return location;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
