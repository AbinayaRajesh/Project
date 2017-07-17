package com.codepath.myapplication;

import android.os.AsyncTask;

import com.yelp.clientlib.connection.YelpAPI;
import com.yelp.clientlib.connection.YelpAPIFactory;
import com.yelp.clientlib.entities.SearchResponse;

import junit.framework.Assert;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by arajesh on 7/14/17.
 */

public class SendFeedback extends AsyncTask<Void, Void, String> {


    @Override
    protected String doInBackground(Void...voids) {

        String consumerKey = "nqSR8ScqNdLoWXauJendGg";
        String consumerSecret = "d4pD4AjRACmMhtiaedeJ8riJAua2RVp9f68b3QcZaEHeJPCROeppyTZCDPlIs5Jb";
        String token = "IBQ07qj0k-F5u7Egmfh6NYjshIRYb3lwCB7gfPfzIDlT5fRfgRZR3rbQ21vyqFz0yLyF2F1376YDg8v0DBCAP_468DMpIjPBURcVB_rf0wOLyowEafB_tN1i6rNnWXYx";
        String tokenSecret = "hmac-sha1";

            Map<String, String> params = new HashMap<>();

// general params
            params.put("term", "food");
                    params.put("limit", "3");

// locale params
                    params.put("lang", "fr");


        YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
        YelpAPI yelpAPI = apiFactory.createAPI();

        Call<SearchResponse> call = yelpAPI.search("San Francisco", params);

        try {
        Response<SearchResponse> response = call.execute();
            Assert.assertEquals(200, response.code());
//            SearchResponse searchResponse = response.body();
//            Assert.assertNotNull(searchResponse);
        }
        catch (IOException e) {
        e.printStackTrace();
        }

        return null;
    }

    @Override
        protected void onPostExecute(String message) {

            //process message
        }
}

//
//    YelpAPIFactory apiFactory = new YelpAPIFactory(consumerKey, consumerSecret, token, tokenSecret);
//    YelpAPI yelpAPI = apiFactory.createAPI();
//
//    Map<String, String> params = new HashMap<>();
//
//// general params
//            params.put("term", "food");
//                    params.put("limit", "3");
//
//// locale params
//                    params.put("lang", "fr");
//
//                    Call<SearchResponse> call = yelpAPI.search("San Francisco", params);
//
//        try {
//        Response<SearchResponse> response = call.execute();
////            Assert.assertEquals(200, response.code());
////            SearchResponse searchResponse = response.body();
////            Assert.assertNotNull(searchResponse);
//        }
//        catch (IOException e) {
//        e.printStackTrace();
//        }

