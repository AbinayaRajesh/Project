package com.codepath.myapplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/13/17.
 */

public class Food {
    String name;
    public Food(JSONObject object) throws JSONException {
        name = object.getString("title");
    }
    public Food(){}
    public static Food fromJson(JSONObject jsonObject) {
        Food food = new Food();
        try {
            // Deserialize json into object fields

            food.name = jsonObject.has("title") ? jsonObject.getString("title_suggest") : "";
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
}
