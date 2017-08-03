package com.codepath.myapplication.Event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class EventSearchActivity extends AppCompatActivity {
    Context context;
    public final static String API_BASE_URL = "http://api.eventful.com/json/";
    //API key parameter name
    public final static String API_KEY_PARAM = "95JSGDKWtDtWRRgx";
    AsyncHttpClient client = new AsyncHttpClient();
    ArrayList<Event> events;
    Country country;
    String ll;
    RecyclerView rvEvents;
    //the adapter wired to the recycler view
    EventCardAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_search);

        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        ll = getIntent().getStringExtra("ll");

        String query = getIntent().getExtras().getString("search");
        // allows for optimizations
        rvEvents = (RecyclerView) findViewById(R.id.rvEvents);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);
        rvEvents.setHasFixedSize(true);
        events = new ArrayList<>();
        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(EventSearchActivity.this, 2);
        rvEvents.setLayoutManager(layout);
        adapter = new EventCardAdapter(EventSearchActivity.this, events);
        // Bind adapter to list
        rvEvents.setAdapter(adapter);
        getSportsEvents(query);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusearch, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                Intent i = new Intent(context, EventSearchActivity.class);
                i.putExtra("search", query);
                startActivity(i);
                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, tempDemoActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }
    private void getSportsEvents(String query){
        String url = API_BASE_URL + "events/search?";
        String put = "China%20" + query;
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "china");
        params.put("keywords", query);
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    events.clear();
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < eventArray.length(); i++){
                        Event event = Event.fromJson(i, eventArray.getJSONObject(i));
                        events.add(event);
                        //notify adapter that a row was added
                        adapter.notifyItemChanged(events.size()-1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
//http://api.eventful.com/rest/events/search?app_key=95JSGDKWtDtWRRgx&keywords=fun
    }
}
