package com.codepath.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Event.Event;
import com.codepath.myapplication.Event.EventDetail;

import java.util.ArrayList;

// Provide the underlying view for an individual list item.
public class EventCardAdapter extends RecyclerView.Adapter<EventCardAdapter.VH> {
    private Activity mContext;
    private ArrayList<Event> mEvents;
    // public tempFOOD optionsActivity;

    public EventCardAdapter(Activity context, ArrayList<Event> events) {
        mContext = context;
        if (events == null) {
            throw new IllegalArgumentException("options must not be null");
        }
        mEvents = events;
    }

    // Inflate the view based on the viewType provided.
    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Event event = mEvents.get(position);
        holder.rootView.setTag(event);
        holder.tvTitle.setText(event.getEventName());
        Glide.with(mContext).load(event.getEventUrl()).centerCrop().into(holder.ivProfile);
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    // Provide a reference to the views for each contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvTitle;
        final View vPalette;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            vPalette = itemView.findViewById(R.id.vPalette);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Event e = (Event)v.getTag();
                    if (e != null) {
                        // Fire an intent when a option is selected
                        // Pass option object in the bundle and populate details activity.
                        // first parameter is the context, second is the class of the activity to launch
                        Intent i = new Intent(context, EventDetail.class);
                        Event event = mEvents.get(e.getId());
                        i.putExtra("event", event);
                        context.startActivity(i); // brings up the second activity
                    }
                }
            });
        }
    }
}
