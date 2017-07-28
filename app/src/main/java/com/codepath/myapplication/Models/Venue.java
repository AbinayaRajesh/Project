package com.codepath.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arajesh on 6/22/17.
 */

public class Venue implements Parcelable{

    // values from API
    String title;
    int num;
    Location location;
    String id;
    String imageUrl;


    // initialize from JSON data
    public Venue(int n, JSONObject object) throws JSONException {
        num = n;
        title = object.getString("name");
        location = Location.fromJSON(object.getJSONObject("location"));
        id = object.getString("id");
        //imageUrl = "https://api.foursquare.com/v2/venues/"+id;


    }


    public static Venue consVenue (String name, String url, float lat, float lng,
                                   String city, String state, int dist) {
        Venue v = new Venue();
        Location l = Location.consLocation(lat, lng, city, state, dist);
        v.title = name;
        v.imageUrl = url;
        v.location = l;
        return v;
    }


    public Venue() {}

    protected Venue(Parcel in) {
        title = in.readString();
        id = in.readString();
        imageUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(imageUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Venue> CREATOR = new Creator<Venue>() {
        @Override
        public Venue createFromParcel(Parcel in) {
            return new Venue(in);
        }

        @Override
        public Venue[] newArray(int size) {
            return new Venue[size];
        }
    };

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
