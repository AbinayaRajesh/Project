package com.codepath.myapplication;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by emilylroth on 7/11/17.
 */


public class FoodClient {

    private static final String API_BASE_URL = "http://api.yummly.com/v1/api/recipes?";
    private AsyncHttpClient client;
    private static final String SPOON_API_BASE_URL = "";
    private String API_KEY = "aa632d11f5f23f7744820c943c788fc0";
    private String APP_ID = "f7fd02b6";


    public FoodClient() {
        this.client = new AsyncHttpClient();
    }


    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }
    private String getApiSpoonUrl(String relativeUrl) {
        return SPOON_API_BASE_URL + relativeUrl;
    }

    // Method for accessing the search API
    public void getRecipes(final String query, JsonHttpResponseHandler handler) {
        try {

            String url = getApiUrl("_app_id=" + APP_ID + "&_app_key=" + API_KEY + "&q=" + "INDIAN" + "&requirePictures=true");

            client.get(url + URLEncoder.encode(query, "utf-8"), handler);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}