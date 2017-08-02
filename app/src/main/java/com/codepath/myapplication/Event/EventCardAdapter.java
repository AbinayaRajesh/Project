package com.codepath.myapplication.Event;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventContract;
import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.R;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new VH(itemView, mContext);
    }

    // Display data at the specified position
    @Override
    public void onBindViewHolder(VH holder, int position) {
        Event event = mEvents.get(position);
        holder.rootView.setTag(event);
        holder.tvTitle.setText(event.getEventName());
        Glide.with(mContext).load(event.getEventUrl()).centerCrop().into(holder.ivProfile);

        if (event.isFavourite()==1) {
            Glide.with(mContext) .load("") .error(R.drawable.ic_add) .into(holder.add);
        }
        else {
            Glide.with(mContext) .load("") .error(R.drawable.ic_remove) .into(holder.add);
        }
    }

    @Override
    public int getItemCount() {
        return mEvents.size();
    }

    private void insertEvent(Event event) {

        deleteEvent(event);

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        // mNameEditText.getText().toString().trim();
        String nameString = event.getEventName();
        String tex = Jsoup.parse(event.getEventDescription()).text();
        String descriptionString = tex;
        String urlString = event.getEventUrl();
        String venueString = event.getEventVenue();
        String startString = event.getStartTime();
        String stopString = event.getStopTime();
        int key = event.getId();

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

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

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(EventEntry.TABLE_NAME, null, values);

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(mContext, "Error with saving event", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(mContext, "Event saved to favourites", Toast.LENGTH_SHORT).show();
        }

//        // pass back data
//
//        // Prepare data intent
//        Intent data = new Intent();
//        // Pass relevant data back as a result
//        int t = event.getId();
//        data.putExtra("num", t);
//        // Activity finished ok, return the data
//        setResult(RESULT_OK, data); // set result code and bundle data for response
//        finish(); // closes the activity, pass data to parent

    }

    private void deleteEvent(Event event) {


        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + EventContract.EventEntry.TABLE_NAME +
                " WHERE " + EventContract.EventEntry.COLUMN_EVENT_VENUE + " = \"" + event.getEventVenue() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }


    // Provide a reference to the views for each contact item
    public class VH extends RecyclerView.ViewHolder {
        final View rootView;
        final ImageView ivProfile;
        final TextView tvTitle;
        final View vPalette;
        final ImageButton add;

        public VH(View itemView, final Context context) {
            super(itemView);
            rootView = itemView;
            ivProfile = (ImageView)itemView.findViewById(R.id.ivProfile);
            tvTitle = (TextView)itemView.findViewById(R.id.tvTitle);
            vPalette = itemView.findViewById(R.id.vPalette);
            add = (ImageButton) itemView.findViewById(R.id.add);

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Event e = (Event) mEvents.get(position);

                        if (e != null) {
                            // Fire an intent when a option is selected
                            // Pass option object in the bundle and populate details activity.
                            // first parameter is the context, second is the class of the activity to launch
                            Intent i = new Intent(context, EventDetail.class);
                            Event event = mEvents.get(e.getId());
                            i.putExtra("event", event);
                            mContext.startActivityForResult(i, REQUEST_CODE);
                            // context.startActivity(i); // brings up the second activity
                        }

                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Event e = (Event) mEvents.get(position);
                    if (e.isFavourite()==0) {
                        add.setImageResource(R.drawable.ic_remove);
                        Byte i = 1;
                        e.setFavourite(i);
                        insertEvent(e);
                    }
                    else {
                        add.setImageResource(R.drawable.ic_add);
                        Byte i = 0;
                        e.setFavourite(i);
                        deleteEvent(e);
                    }
                }
            });
        }
    }
}
