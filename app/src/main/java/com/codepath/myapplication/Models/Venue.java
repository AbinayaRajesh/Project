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
    String name;
//    String overview;
//    String posterPath; // only the path
//    String backdropPath;
//    double voteAverage;
//    Integer id;

    // initialize from JSON data
    public Venue(JSONObject object) throws JSONException {
        name = object.getString("name");
//        overview = object.getString("overview");
//        posterPath = object.getString("poster_path");
//        backdropPath = object.getString("backdrop_path");
//        voteAverage = object.getDouble("vote_average");
//        id = object.getInt("id");
    }

    public Venue() {}

    public String getName() {
        return name;
    }

//    public String getOverview() {
//        return overview;
//    }
//
//    public String getPosterPath() {
//        return posterPath;
//    }
//
//    public String getBackdropPath() { return backdropPath; }
//
//    public double getVoteAverage() {
//        return voteAverage;
//    }
//
//    public Integer getId() {
//        return id;
//    }
}
