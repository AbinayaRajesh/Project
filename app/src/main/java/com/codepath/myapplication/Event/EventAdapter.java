package com.codepath.myapplication.Event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by eyobtefera on 7/11/17.
 */

public class EventAdapter extends ArrayAdapter<Event> {

    private List<Event> mEvents;
    EventBriteClient client;
    Context context;
    //pass in the Tweets array in the constructor
    public EventAdapter(Context context, ArrayList<Event> events) {
            super(context, 0, events);
        }
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Event event = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_event, parent, false);
        }

        // Lookup view for data population
        TextView tvEventName = (TextView) convertView.findViewById(R.id.tvEventName);
        TextView tvEventCapacity = (TextView) convertView.findViewById(R.id.tvEventCapacity);
        TextView tvEventDescription = (TextView) convertView.findViewById(R.id.tvEventDescription);
        ImageView ivEventImage = (ImageView) convertView.findViewById(R.id.ivEventImage);
        // Populate the data into the template view using the data object
        tvEventName.setText(event.eventName);
        tvEventCapacity.setText(event.eventCapacity);
        tvEventDescription.setText(event.eventDescription);
        Picasso.with(context).load(event.getEventUrl()).into(ivEventImage);

        // Return the completed view to render on screen
        return convertView;
    }

}
