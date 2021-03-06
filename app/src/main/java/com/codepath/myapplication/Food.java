package com.codepath.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/13/17.
 */

@Parcel
public class Food {
    String name;
    String imageUrl;
    int rating;
    int id;
    Byte favourite;
    String ingredients;

    public Food(JSONObject object) throws JSONException {
        name = object.getString("title");
        imageUrl = object.getString("smallImageUrls");
    }

    public String getName() {
        return name;
    }

    public Food(){}

    public static Food consFood (String name, int rating, String url, int id, Byte fav) {
        Food f = new Food();
        f.id = id;
        f.rating = rating;
        f.imageUrl = url;
        f.name = name;
        f.favourite = fav;
        return f;
    }



    public static Food fromJson(int i, JSONObject jsonObject) {
        Food food = new Food();
        try {
            // Deserialize json into object fields
            food.id = i;
            food.name = jsonObject.getString("recipeName");
            food.rating = jsonObject.getInt("rating");
            //food.imageUrl = jsonObject.getString("imageUrlsBySize");
            food.imageUrl = jsonObject.getJSONArray("smallImageUrls").getString(0);
            for(int j = 0; j < jsonObject.getJSONArray("ingredients").length()-1; j++){
                food.ingredients = food.ingredients + jsonObject.getJSONArray("ingredients").getString(j) + "\n";
            }
            Byte y = 0;
            food.favourite = y;


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return food;
    }

    public Byte isFavourite() {
        return favourite;
    }

    public void setFavourite(Byte favourite) {
        this.favourite = favourite;
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
            Food book = Food.fromJson(i, foodJson);
            if (book != null) {
                recipes.add(book);
            }
        }
        return recipes;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getRating() {
        return rating;
    }

    public Byte getFavourite() {
        return favourite;
    }

    public String getIngredients() {
        return ingredients;
    }
}
