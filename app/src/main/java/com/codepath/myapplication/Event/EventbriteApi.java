package com.codepath.myapplication.Event;

/**
 * Created by eyobtefera on 7/11/17.
 */

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class EventbriteApi extends DefaultApi10a {
    private static final String AUTHORIZE_URL = "http://eventful.com/oauth/authorize";

    protected EventbriteApi() {
    }

    private static class InstanceHolder {
        private static final EventbriteApi  INSTANCE = new EventbriteApi();
    }

    public static EventbriteApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint(){
        return " http://eventful.com/oauth/access_token";
    }

    @Override
    public String getRequestTokenEndpoint() {
        return "http://eventful.com/oauth/request_token";
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}