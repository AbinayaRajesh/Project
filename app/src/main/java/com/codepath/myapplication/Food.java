package com.codepath.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
<<<<<<< HEAD
import org.parceler.Parcel;
=======
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/13/17.
 */
<<<<<<< HEAD
@Parcel
public class Food {
    String name;
    String imageUrl;
    int rating;

    public Food(JSONObject object) throws JSONException {
        name = object.getString("title");
        imageUrl = object.getString("smallImageUrls");
    }

    public String getName() {
        return name;
    }

    public Food(){}

=======

public class Food {
    String name;
    public Food(JSONObject object) throws JSONException {
        name = object.getString("title");
    }
    public Food(){}
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0
    public static Food fromJson(JSONObject jsonObject) {
        Food food = new Food();
        try {
            // Deserialize json into object fields
<<<<<<< HEAD
            food.name = jsonObject.getString("recipeName");
            food.rating = jsonObject.getInt("rating");
            //food.imageUrl = jsonObject.getString("imageUrlsBySize");
            food.imageUrl = jsonObject.getJSONArray("smallImageUrls").getString(0);

=======

            food.name = jsonObject.has("title") ? jsonObject.getString("title_suggest") : "";
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return food;
    }


    public static ArrayList<Food> fromJson(JSONArray jsonArray) {
        ArrayList<Food> recipes = new ArrayList<>(jsonArray.length());
        // Process each result in json array, decode and convert to business
        // object
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject foodJson;
            try {
                foodJson = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            Food book = Food.fromJson(foodJson);
            if (book != null) {
                recipes.add(book);
            }
        }
        return recipes;
    }
<<<<<<< HEAD

    public String getImageUrl() {
        return imageUrl;
    }

    public int getRating() {
        return rating;
    }
=======
>>>>>>> 2c032b7bf21d5da11df89e5f8ff2ac4f966cfaf0
}
