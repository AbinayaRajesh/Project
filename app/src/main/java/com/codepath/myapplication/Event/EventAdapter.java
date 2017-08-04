package com.codepath.myapplication.Event;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.R;

import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


/**
 * Created by eyobtefera on 7/11/17.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private List<Event> mEvents;
    ViewGroup pview;
    Context context;
    Event curE;
    int curI;

    public EventAdapter(ArrayList<Event> events) {
        this.mEvents = events;
    }

    //pass in the Tweets array in the constructor
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        pview = parent;
        LayoutInflater inflater = LayoutInflater.from(context);
        View eventView = inflater.inflate(R.layout.item_view_event, parent, false);
        ViewHolder viewHolder = new ViewHolder(eventView);
        return viewHolder;
    }

    //bind the values based on the position of the element

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //get the data according to position
        Event event = mEvents.get(position);
        //populate the views according to this data
        String eventName = event.getEventName();
        holder.tvEventName.setText(event.getEventName());
        holder.tvEventVenue.setText(event.getEventVenue());
        // holder.tvEventDescription.setText(Jsoup.parse(event.eventDescription).text());

        Glide.with(context).
                load(event.getEventUrl()).
                bitmapTransform(new RoundedCornersTransformation(context, 15, 0)).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(holder.ivEventImage);

    }
    @Override
    public int getItemCount() {
        return mEvents.size();
    }
    //create ViewHolder class

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView ivEventImage;
        public TextView tvEventName;
        public TextView tvEventVenue;
       // public TextView tvEventDescription;
        public RelativeLayout layout;



        public ViewHolder(View itemView) {
            super(itemView);

            // perform findViewById lookups
            ivEventImage = (ImageView) itemView.findViewById(R.id.ivEventImage);
            tvEventName = (TextView) itemView.findViewById(R.id.tvEventName);
            tvEventVenue = (TextView) itemView.findViewById(R.id.tvEventVenue);
            // tvEventDescription = (TextView) itemView.findViewById(R.id.tvEventDescription);
            layout = (RelativeLayout) itemView.findViewById(R.id.detailView);
            itemView.setOnClickListener(this);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int pos = getAdapterPosition();
                    Event event = mEvents.get(pos);
                    mEvents.remove(pos);
                    deleteEvent(event);
                    curE = event;
                    curI = pos;
                    notifyDataSetChanged();
                    Snackbar.make(pview, R.string.snackbar_text, Snackbar.LENGTH_LONG)
                            .setAction(R.string.snackbar_action, myOnClickListener)
                            .setActionTextColor(Color.WHITE)
                            .show();
                    return true;
                }
            });
        }

        View.OnClickListener myOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEvents.add(curI, curE);
                insertEvent(curE);
                notifyDataSetChanged();
            }
        };

        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                Event event = mEvents.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, EventDetail.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra("event", event);
                // show the activity
                context.startActivity(intent);
            }
        }


        }

    private void insertEvent(Event event) {

        deleteEvent(event);

        String nameString = event.getEventName();
        String tex = Jsoup.parse(event.getEventDescription()).text();
        String descriptionString = tex;
        String urlString = event.getEventUrl();
        String venueString = event.getEventVenue();
        String startString = event.getStartTime();
        String stopString = event.getStopTime();
        int key = event.getId();

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(EventEntry.COLUMN_EVENT_NAME, nameString);
        values.put(EventEntry.COLUMN_EVENT_DESCRIPTION, descriptionString);
        values.put(EventEntry.COLUMN_EVENT_URL, urlString);
        values.put(EventEntry.COLUMN_EVENT_VENUE, venueString);
        values.put(EventEntry.COLUMN_EVENT_START_TIME, startString);
        values.put(EventEntry.COLUMN_EVENT_STOP_TIME, stopString);
        values.put(EventEntry.COLUMN_EVENT_UNIQUE_KEY, key);

        // Insert a new row for event in the database, returning the ID of that new row.
        long newRowId = db.insert(EventEntry.TABLE_NAME, null, values);



    }

    private void deleteEvent(Event event) {


        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + EventEntry.TABLE_NAME +
                " WHERE " + EventEntry.COLUMN_EVENT_VENUE + " = \"" + event.getEventVenue() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }

}
