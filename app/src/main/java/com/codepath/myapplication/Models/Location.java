package com.codepath.myapplication.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by arajesh on 7/14/17.
 */

@Parcel
public class Location {

    // values from API
    double lat;
    double lng;
    int distance;
    String city;
    String state;
    String[] formattedAddress;





    // initialize from JSON data
    public static Location fromJSON (JSONObject object) throws JSONException {

        Location location = new Location();

        location.city = object.getString("city");
        location.state = object.getString("state");
        location.lat = object.getDouble("lat");
        location.lng = object.getDouble("lng");
        location.distance =object.getInt("distance");

        JSONArray a =object.getJSONArray("formattedAddress");
        location.formattedAddress = new String[a.length()];
        for(int i = 0; i < a.length(); i++)
            location.formattedAddress[i] = a.getString(i);

        return location;


    }

    public Location() {}

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public int getDistance() {
        return distance;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String[] getFormattedAddress() {
        return formattedAddress;
    }
}

//{
//        address: "Furman St",
//        crossStreet: "Brooklyn Bridge Park Greenway",
//        lat: 40.69957016220183,
//        lng: -73.99793274204788,
//        labeledLatLngs: [
//        {
//        label: "display",
//        lat: 40.69957016220183,
//        lng: -73.99793274204788
//        }
//        ],
//        distance: 180,
//        postalCode: "11201",
//        cc: "US",
//        city: "Brooklyn",
//        state: "NY",
//        country: "United States",
//        formattedAddress: [
//        "Furman St (Brooklyn Bridge Park Greenway)",
//        "Brooklyn, NY 11201",
//        "United States"
//        ]
//        },
