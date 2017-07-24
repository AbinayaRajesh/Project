package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Event.EventAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class temp extends AppCompatActivity {
    //base url for API call
    public final static String API_BASE_URL = "http://api.eventful.com/json/";
    //API key parameter name
    public final static String API_KEY_PARAM = "95JSGDKWtDtWRRgx";
    AsyncHttpClient client = new AsyncHttpClient();
    ArrayList<Event> events;
    RecyclerView rvEvents;
    //the adapter wired to the recycler view
    EventAdapter adapter;
    //image config
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        setTitle("Events");
        //events = new ArrayList<>();
        adapter = new EventAdapter(events);
        //resolve the recycler view and connect a layout manager and the adapter
        rvEvents = (RecyclerView) findViewById(R.id.rvMovies);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        rvEvents.setAdapter(adapter);
        //new EventRetriever().execute();
        String url = API_BASE_URL + "events/search?";
        RequestParams params = new RequestParams();
        params.put("app_key", API_KEY_PARAM);
        params.put("keywords", "food");
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONObject eventsonline = response.getJSONObject("events");
                    JSONArray eventArray = eventsonline.getJSONArray("event");
                    for (int i = 0; i < 10; i++){
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
