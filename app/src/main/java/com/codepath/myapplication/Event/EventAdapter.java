package com.codepath.myapplication.Event;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by eyobtefera on 7/11/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> mEvents;
    Context context;

    public EventAdapter(ArrayList<Event> events) {
        this.mEvents = events;
    }

    //pass in the Tweets array in the constructor
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //get the data according to position
        Event event = mEvents.get(position);
        //populate the views according to this data
        holder.tvEventName.setText(event.eventName);
        holder.tvEventVenue.setText(event.eventVenue);
        holder.tvEventDescription.setText(event.eventDescription);

        Glide.with(context).
                load(event.getEventUrl()).
                bitmapTransform(new RoundedCornersTransformation(context, 15, 0)).
                into(holder.ivEventImage);
    }
    @Override
    public int getItemCount() {
        return mEvents.size();
    }
    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView ivEventImage;
        public TextView tvEventName;
        public TextView tvEventVenue;
        public TextView tvEventDescription;
        public RelativeLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivEventImage = (ImageView) itemView.findViewById(R.id.ivEventImage);
            tvEventName = (TextView) itemView.findViewById(R.id.tvEventName);
            tvEventVenue = (TextView) itemView.findViewById(R.id.tvEventVenue);
            tvEventDescription = (TextView) itemView.findViewById(R.id.tvEventDescription);
            layout = (RelativeLayout) itemView.findViewById(R.id.detailView);

        }
    }
}
