package com.codepath.myapplication;

/**
 * Created by emilylroth on 7/21/17.
 */

public class PhotoClient {
    // String to create Flickr API urls
    private static final String FLICKR_BASE_URL = "http://api.flickr.com/services/rest/?method=";
    private static final String FLICKR_PHOTOS_SEARCH_STRING = "flickr.photos.search";
    private static final String FLICKR_GET_SIZES_STRING = "flickr.photos.getSizes";
    private static final int FLICKR_PHOTOS_SEARCH_ID = 1;
    private static final int FLICKR_GET_SIZES_ID = 2;
    private static final int NUMBER_OF_PHOTOS = 20;

    //You can set here your API_KEY
    private static final String APIKEY_SEARCH_STRING = "&api_key=64c0f179f8aec0444033c8b2c57a7db0";

    private static final String TAGS_STRING = "&tags=";
    private static final String PHOTO_ID_STRING = "&photo_id=";
    private static final String FORMAT_STRING = "&format=json";
    public static final int PHOTO_THUMB = 111;
    public static final int PHOTO_LARGE = 222;

    private static String createURL(String parameter) {
        String method_type = "";
        String url = null;
        method_type = FLICKR_PHOTOS_SEARCH_STRING;
        url = FLICKR_BASE_URL + method_type + APIKEY_SEARCH_STRING + TAGS_STRING + parameter + FORMAT_STRING + "&per_page="+NUMBER_OF_PHOTOS+"&media=photos";

        return url;
    }

}
