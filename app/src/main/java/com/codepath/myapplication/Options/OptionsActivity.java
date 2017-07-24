package com.codepath.myapplication.Options;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.NearbyActivity;
import com.codepath.myapplication.Photo;
import com.codepath.myapplication.PhotoClient;
import com.codepath.myapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class OptionsActivity extends AppCompatActivity {
    private RecyclerView rvOptions;
    private OptionsAdapter mAdapter;
    private List<Option> options;
    public Country country;
    public ArrayList<Photo> photos;
    AsyncHttpClient client;
    Context context;

  //  public PhotoClient photoClient;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        context = this;

        setContentView(R.layout.activity_options);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        setTitle(country.getName());
        // Find RecyclerView and bind to adapter
        rvOptions = (RecyclerView) findViewById(R.id.rvOptions);

        // allows for optimizations
        rvOptions.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(OptionsActivity.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvOptions.setLayoutManager(layout);

        // geAt data
        options = Option.getContacts();
        this.client = new AsyncHttpClient();
        url = PhotoClient.createURL(country.getName());
        getPhoto(url);

        // Create an adapter
        mAdapter = new OptionsAdapter(OptionsActivity.this, options);
        mAdapter.optionsActivity = this;

        // Bind adapter to list
        rvOptions.setAdapter(mAdapter);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks herAe. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, NearbyActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        startActivity(i);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menumain, menu);

        return true;
    }

    public void getPhoto(String url){
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject results = response.getJSONObject("photos");
                    JSONArray photoStream = results.getJSONArray("photo");

                    Photo photo = Photo.fromJson(photoStream.getJSONObject(0));

                    photos.add(photo);
                    //notify adapter
                    // adapter.notifyItemInserted(countries.size()-1);
                    Glide.with(context)
                            .load("https://farm"+ photo.getFarm() +".staticflickr.com/" + photo.getServer()+ "/" + photo.getId() +"_" +photo.getSecret()+".jpg")
                            .into((ImageView) findViewById(R.id.ivOptionsImage));
                } catch (JSONException e) {
                    // logError("Failed to parse now playing movies", e, true);
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }



}
