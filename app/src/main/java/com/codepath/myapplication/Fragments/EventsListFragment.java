package com.codepath.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.EventCardAdapter;
import com.codepath.myapplication.R;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;

public class EventsListFragment extends Fragment {

    ArrayList<Event> events = new ArrayList<>();

    public final static String API_BASE_URL = "http://api.eventful.com/json/";
    //API key parameter name
    public final static String API_KEY_PARAM = "95JSGDKWtDtWRRgx";
    AsyncHttpClient client = new AsyncHttpClient();
    //ArrayList<Event> events;
    RecyclerView rvEvents;
    //the adapter wired to the recycler view
    EventCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_events_list_fragment, container, false);

        adapter = new EventCardAdapter(getActivity(), events);
        //resolve the recycler view and connect a layout manager and the adapter
        rvEvents = (RecyclerView) v.findViewById(R.id.rvMovies);
        rvEvents.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        rvEvents.setAdapter(adapter);


        return v;
    }

}
