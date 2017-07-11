package com.codepath.myapplication;

/**
 * Created by eyobtefera on 7/11/17.
 */

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;

public class EventBriteApi extends DefaultApi10a {

    private static final String AUTHORIZE_URL = "https://www.eventbrite.com/oauth/authorize";
    private static final String REQUEST_TOKEN_RESOURCE = "https://www.eventbrite.com/oauth/token";
    private static final String ACCESS_TOKEN_RESOURCE = "https://www.eventbrite.com/oauth/token";

    protected EventBriteApi() {
    }

    private static class InstanceHolder {
        private static final EventBriteApi INSTANCE = new EventBriteApi();
    }

    public static EventBriteApi instance() {
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

    /**
     * Twitter 'friendlier' authorization endpoint for OAuth.
     * <p>
     * Uses SSL.
     */
    public static class Authenticate extends EventBriteApi {

        private static final String AUTHENTICATE_URL = "https://www.eventbrite.com/oauth/authorize?response_type=code&client_id=";

        private Authenticate() {
        }

        private static class InstanceHolder {
            private static final Authenticate INSTANCE = new Authenticate();
        }

        public static Authenticate instance() {
            return InstanceHolder.INSTANCE;
        }

        @Override
        public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
            return String.format(AUTHENTICATE_URL, requestToken.getToken());
        }
    }
}