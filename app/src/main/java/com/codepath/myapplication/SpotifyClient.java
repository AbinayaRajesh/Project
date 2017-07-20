package com.codepath.myapplication;

import android.util.Log;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Album;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by emilylroth on 7/18/17.
 */

public class SpotifyClient extends SpotifyApi{

public SpotifyClient (){
    SpotifyApi api = new SpotifyApi();

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step
    api.setAccessToken("myAccessToken");

    SpotifyService spotify = api.getService();

    spotify.getAlbum("2dIGnmEIy1WZIcZCFSj6i8", new Callback<Album>() {
        @Override
        public void success(Album album, Response response) {
            Log.d("Album success", album.name);
        }

        @Override
        public void failure(RetrofitError error) {
            Log.d("Album failure", error.toString());
        }
    });

}

// Most (but not all) of the Spotify Web API endpoints require authorisation.
// If you know you'll only use the ones that don't require authorisation you can skip this step

}
