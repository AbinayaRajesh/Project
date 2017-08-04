package com.codepath.myapplication.Tourism;

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

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.TourismContract.TourismEntry;
import com.codepath.myapplication.Models.Venue;
import com.codepath.myapplication.R;

import java.util.ArrayList;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;

// Provide the underlying view for an individual list item.
public class VenueCardAdapter extends RecyclerView.Adapter<VenueCardAdapter.VH> {
    private Activity mContext;
    private ArrayList<Venue> mVenues;
    // public tempFOOD optionsActivity;

    public VenueCardAdapter(Activity context, ArrayList<Venue> venues) {
        mContext = context;
        if (venues == null) {
            throw new IllegalArgumentException("options must not be null");
        }
        mVenues = venues;
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
        Venue venue = mVenues.get(position);
        holder.rootView.setTag(venue);
        holder.tvTitle.setText(venue.getTitle());
        if (venue.getImageUrl()=="") {
//            int i = (int) ((Math. random() * 50 + 1) % 5);
//            switch (i) {
//                case 0: {holder.ivProfile.setImageResource(R.mipmap.red);break;}
//                case 1: {holder.ivProfile.setImageResource(R.mipmap.blue); break;}
//                case 2: {holder.ivProfile.setImageResource(R.mipmap.green); break;}
//                case 3: {holder.ivProfile.setImageResource(R.mipmap.violet); break;}
//                case 4: {holder.ivProfile.setImageResource(R.mipmap.pink); break;}
//            }
            // holder.ivProfile.setImageResource(R.drawable.ic_music);
        }
        else {
            Glide.with(mContext).load(venue.getImageUrl()).centerCrop().into(holder.ivProfile);
        }
    }

    @Override
    public int getItemCount() {
        return mVenues.size();
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


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Venue ven = (Venue) mVenues.get(position);
                    if (ven.isFavourite()==0) {
                        add.setImageResource(R.drawable.ic_remove);
                        Byte i = 1;
                        ven.setFavourite(i);
                        insertVenue(ven);
                    }
                    else {
                        add.setImageResource(R.drawable.ic_add);
                        Byte i = 0;
                        ven.setFavourite(i);
                        deleteVenue(ven);
                    }
                }
            });

            // Navigate to contact details activity on click of card view.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Venue ven = (Venue) v.getTag();
                    if (v != null) {
                        // Fire an intent when a option is selected
                        // Pass option object in the bundle and populate details activity.
                        // first parameter is the context, second is the class of the activity to launch

                        Intent i = new Intent(context, DetailPlaceActivity.class);
                        Venue venue = mVenues.get(ven.getNum());
                        i.putExtra("location", venue.getLocation());
                        i.putExtra("venue", venue);
                        mContext.startActivityForResult(i, REQUEST_CODE); // brings up the second activity


                    }
                }
            });
        }
    }


    private void insertVenue(Venue venue) {

        deleteVenue(venue);

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        // mNameEditText.getText().toString().trim();


        String nameString = venue.getTitle();
        String urlString = venue.getImageUrl();
        String cityString = venue.getLocation().getCity();
        String stateString = venue.getLocation().getState();
        float lat = (float) venue.getLocation().getLat();
        float lng = (float) venue.getLocation().getLng();
        int dist = venue.getLocation().getDistance();

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(TourismEntry.COLUMN_TOURISM_NAME, nameString);
        values.put(TourismEntry.COLUMN_TOURISM_URL, urlString);
        values.put(TourismEntry.COLUMN_TOURISM_CITY, cityString);
        values.put(TourismEntry.COLUMN_TOURISM_STATE, stateString);
        values.put(TourismEntry.COLUMN_TOURISM_LAT, lat);
        values.put(TourismEntry.COLUMN_TOURISM_LNG, lng);
        values.put(TourismEntry.COLUMN_TOURISM_DISTANCE, dist);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(TourismEntry.TABLE_NAME, null, values);



        // pass back data

//        // Prepare data intent
//        Intent data = new Intent();
//        // Pass relevant data back as a result
//        int t = venue.getNum();
//        data.putExtra("num", t);
//        // Activity finished ok, return the data
//        setResult(RESULT_OK, data); // set result code and bundle data for response
//        finish(); // closes the activity, pass data to parent

    }

    private void deleteVenue(Venue venue) {

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + TourismEntry.TABLE_NAME +
                " WHERE " + TourismEntry.COLUMN_TOURISM_NAME + " = \"" + venue.getTitle() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(mContext);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }


}
