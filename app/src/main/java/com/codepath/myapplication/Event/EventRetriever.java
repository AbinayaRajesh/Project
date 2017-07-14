package com.codepath.myapplication.Event;

import android.os.AsyncTask;

import com.github.scribejava.core.builder.ServiceBuilder;
import com.github.scribejava.core.model.OAuth1AccessToken;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.model.OAuthRequest;
import com.github.scribejava.core.model.Response;
import com.github.scribejava.core.model.Verb;
import com.github.scribejava.core.oauth.OAuth10aService;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * Created by eyobtefera on 7/13/17.
 */

public class EventRetriever extends AsyncTask<String, Void, Void> {
    @Override
    protected Void doInBackground(String... strings) {
        final OAuth10aService service = new ServiceBuilder()
                .apiKey("95JSGDKWtDtWRRgx")
                .apiSecret("e8cd8e6c13a01a9cd186")
                .debug()
                .build(EventbriteApi.instance());
        try {
            final OAuth1RequestToken requestToken = service.getRequestToken();
            String authUrl = service.getAuthorizationUrl(requestToken);
            final OAuth1AccessToken accessToken = service.getAccessToken(requestToken, "verifier you got from the user/callback");
            final OAuthRequest request = new OAuthRequest(Verb.GET, "http://api.eventful.com/rest/users/locales/list");
            service.signRequest(accessToken, request); // the access token from step 4
            final Response response = service.execute(request);
            System.out.println(response.getBody());
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
