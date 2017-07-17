package com.codepath.myapplication.Event;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

/**
 * Created by eyobtefera on 7/14/17.
 */


public class EventApi extends DefaultApi10a {

    private static final String AUTHORIZE_URL = "http://eventful.com/oauth/authorize";
    private static final String REQUEST_TOKEN_RESOURCE = "www.eventful.com/oauth/request_token";
    private static final String ACCESS_TOKEN_RESOURCE = "www.eventful.com/oauth/access_token";

    protected EventApi() {
    }

    private static class InstanceHolder {
        private static final EventApi INSTANCE = new EventApi();
    }

    public static EventApi instance() {
        return InstanceHolder.INSTANCE;
    }

    @Override
    public String getAccessTokenEndpoint() {
        return "https://" + ACCESS_TOKEN_RESOURCE;
    }

    @Override
    public String getRequestTokenEndpoint() {
        return "https://" + REQUEST_TOKEN_RESOURCE;
    }

    @Override
    public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
        return String.format(AUTHORIZE_URL, requestToken.getToken());
    }
}
