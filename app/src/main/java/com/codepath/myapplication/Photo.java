package com.codepath.myapplication;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilylroth on 7/24/17.
 */

public class Photo {
    String id;
    String secret;
    String server;
    int farm;

    public Photo(){}
    public static Photo fromJson(JSONObject jsonObject) {
        Photo photo = new Photo();
        try {
            // Deserialize json into object fields
            photo.id = jsonObject.getString("id");
            photo.secret = jsonObject.getString("secret");
            photo.farm = jsonObject.getInt("farm");
            photo.server = jsonObject.getString("server");


        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return photo;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public int getFarm() {
        return farm;
    }

    public String getServer() {
        return server;
    }
}
