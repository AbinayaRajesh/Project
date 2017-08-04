package com.codepath.myapplication.Models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

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
    Byte favourite;

    public static String [] alternateUrls = {
            "http://www.westgrove.co.uk/wp-content/uploads/2013/08/shopping-centre-2.jpg",
            "https://static.pexels.com/photos/132037/pexels-photo-132037.jpeg",
            "http://www.floraterra.com/_data/docs/gallery/corporate-building-8-600.jpg",
            "https://tclf.org/sites/default/files/thumbnails/image/Freeway%20Park%2C%20Seattle%2C%20WA%2C%202016.%20Photograph%20%C2%A9%20Aaron%20Leitz%2C%20courtesy%20The%20Cultural%20Landscape%20Foundation-LO%20RES.jpg"
    };



    // initialize from JSON data
    public Venue(int n, JSONObject object) throws JSONException {
        num = n;
        title = object.getString("name");
        location = Location.fromJSON(object.getJSONObject("location"));
        id = object.getString("id");
        Byte y = 0;
        favourite = y;
        int rnd = new Random().nextInt(alternateUrls.length);
        imageUrl = alternateUrls[rnd];
    }

    public Byte isFavourite() {
        return favourite;
    }

    public void setFavourite(Byte favourite) {
        this.favourite = favourite;
    }

    public static Venue consVenue (String name, String url, float lat, float lng,
                                   String city, String state, int dist, Byte fav) {
        Venue v = new Venue();
        Location l = Location.consLocation(lat, lng, city, state, dist);
        v.title = name;
        v.imageUrl = url;
        v.location = l;
        v.favourite = fav;
        return v;
    }


    public Venue() {}

    public void setLocation(Location location) {
        this.location = location;
    }

    protected Venue(Parcel in) {
        title = in.readString();
        id = in.readString();
        imageUrl = in.readString();
        favourite = in.readByte();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(imageUrl);
        dest.writeByte(favourite);
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

    public int getNum() {
        return num;
    }
}
