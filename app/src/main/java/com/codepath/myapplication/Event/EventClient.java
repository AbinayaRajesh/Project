package com.codepath.myapplication.Event;

import android.content.Context;

import com.codepath.myapplication.R;
import com.codepath.oauth.OAuthBaseClient;
import com.github.scribejava.core.builder.api.BaseApi;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by eyobtefera on 7/14/17.
 */

public class EventClient extends OAuthBaseClient {
    public static final BaseApi REST_API_INSTANCE = EventApi.instance(); // Change this
    public static final String REST_URL = "http://api.eventful.com/rest"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "d5abf8442e0d8d7efd59";       // Change this
    public static final String REST_CONSUMER_SECRET = "e8cd8e6c13a01a9cd186"; // Change this

    // Landing page to indicate the OAuth flow worked in case Chrome for Android 25+ blocks navigation back to the app.
    public static final String FALLBACK_URL = "https://codepath.github.io/android-rest-client-template/success.html";

    // See https://developer.chrome.com/multidevice/android/intents
    public static final String REST_CALLBACK_URL_TEMPLATE = "intent://%s#Intent;action=android.intent.action.VIEW;scheme=%s;package=%s;S.browser_fallback_url=%s;end";

    public EventClient(Context context) {
        super(context, REST_API_INSTANCE,
                REST_URL,
                REST_CONSUMER_KEY,
                REST_CONSUMER_SECRET,
                String.format(REST_CALLBACK_URL_TEMPLATE, context.getString(R.string.intent_host),
                        context.getString(R.string.intent_scheme), context.getPackageName(), FALLBACK_URL));
    }

    // CHANGE THIS
    // DEFINE METHODS for different API endpoints here
    public void getEvents(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("event/search");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("keywords", "Japan");
        apiUrl+="app_key=95JSGDKWtDtWRRgx";
        client.get(apiUrl, handler);
    }
}