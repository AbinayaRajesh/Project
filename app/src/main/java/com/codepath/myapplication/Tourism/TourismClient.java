package com.codepath.myapplication.Tourism;

import android.content.Context;

import com.codepath.myapplication.R;
import com.codepath.myapplication.YelpApi;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


/**
 * Created by arajesh on 7/11/17.
 */

public class TourismClient extends OAuthBaseClient {

    public static final BaseApi REST_API_INSTANCE = YelpApi.instance(); // Change this
    public static final String REST_URL = "https://api.yelp.com/v3"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "nqSR8ScqNdLoWXauJendGg";       // Change this
    public static final String REST_CONSUMER_SECRET = "d4pD4AjRACmMhtiaedeJ8riJAua2RVp9f68b3QcZaEHeJPCROeppyTZCDPlIs5Jb"; // Change this

    // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
    public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

    // See https://developer.chrome.com/multidevice/android/intents
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public TourismClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
                        context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
    }
    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here

    public void getSearch(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("businesses/search.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("latitude", 50);
        params.put("longitude", 50);
        client.get(apiUrl, params, handler);
    }


}
