package com.codepath.myapplication;

import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

/**
 * Created by emilylroth on 7/21/17.
 */

public class PhotoClient {
    AsyncHttpClient client;
    ArrayList<Photo> photos;
    // String to create Flickr API urls
    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    private static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
    private static final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
    private static final int FLICKR_PHOTOS_SEARCH_ID = 1;
    private static final int FLICKR_GET_SIZES_ID = 2;
    private static final int NUMBER_OF_PHOTOS = 5;

    //You can set here your API_KEY
    private static final String APIKEY_SEARCH_STRING = "&api_key=3ac9faaab5afd4886063f4f90a52fd0e";

    private static final String TAGS_STRING = "&tags=";
    private static final String PHOTO_ID_STRING = "&photo_id=";
    private static final String FORMAT_STRING = "&format=json";
    public static final int PHOTO_THUMB = 111;
    public static final int PHOTO_LARGE = 222;

    public static String createURL(String parameter) {
        String method_type = "";
        String url = null;
        method_type = FLICKR_PHOTOS_SEARCH_STRING;
        url = FLICKR_BASE_URL + method_type + APIKEY_SEARCH_STRING + TAGS_STRING + parameter + FORMAT_STRING + "&per_page="+NUMBER_OF_PHOTOS+"&media=photos"+"&nojsoncallback=1";
        //url = "http://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=65c9d17134616d3738df5d451d59ea99&tags=china&per_page=2&format=json&nojsoncallback=1";
        return url;
    }



}