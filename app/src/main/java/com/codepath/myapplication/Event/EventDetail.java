package com.codepath.myapplication.Event;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.myapplication.Database.EventContract.EventEntry;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.SavedEventsActivity;
import com.codepath.myapplication.R;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jsoup.Jsoup;

import java.text.DateFormatSymbols;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class EventDetail extends AppCompatActivity implements OnMapReadyCallback {
    //instance variables
    Event event;
    TextView tvEventName;
    ImageView ivEventImage;
    TextView tvDescription;
    TextView tvMonth;
    String month;
    ImageView ivCalender;
    ImageButton i;
    GoogleMap googleMap;
    MapFragment mapFragment;
    String [] date;
    Context context = this;
    @Override
    //creates the event detail view and hooks up the layout
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        tvEventName = (TextView) findViewById(R.id.tvEventName);
        ivEventImage = (ImageView) findViewById(R.id.ivEventImage);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        ivCalender = (ImageView) findViewById(R.id.calender);

        i = (ImageButton) findViewById(R.id.add);
        i.setImageResource(R.drawable.add_white);

        event = (Event) getIntent().getParcelableExtra("event");
        //creates the map to display the event
        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //checks to see if the event in favourited, and display the proper icon
        if (event.isFavourite()==1) {
            Glide.with(context) .load("") .error(R.drawable.remove_white) .into(i);
        }
        else {
            Glide.with(context) .load("") .error(R.drawable.add_white) .into(i);
        }

        tvEventName.setText(event.getEventName());
        //sets descriptions and other view properties
        if (event.getEventDescription()==null) {
            tvDescription.setText("No description available");
        }
        else {
            String text = Jsoup.parse(event.getEventDescription()).text();
            tvDescription.setText(text);
        }
        if (event.getStartTime()!=null) {
            date = event.getDate().split("-");
            month = new DateFormatSymbols().getMonths()[(Integer.parseInt(date[1])) - 1];
            tvMonth.setText(month + " " + date[2] + ", " + date[0]);
        }

        Glide.with(context).
                load(event.getEventUrl()).
                bitmapTransform(new RoundedCornersTransformation(context, 20, 0)).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(ivEventImage);

        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCalender(event);

            }
        });
        //allows people to add and remove events from database
        i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(event.isFavourite()==1){

                    i.setImageResource(R.drawable.add_white);
                    deleteEvent(event);
                    Byte y = 0;
                    event.setFavourite(y);
                    Intent in = new  Intent(EventDetail.this, SavedEventsActivity.class);
                    startActivity(in);
                }
                else {
                    i.setImageResource(R.drawable.remove_white);
                    insertEvent(event);
                    Byte y = 1;
                    event.setFavourite(y);
                    Intent in = new  Intent(EventDetail.this, SavedEventsActivity.class);
                    startActivity(in);
                }
            }
        });


        // FACEBOOK SHARING
        // allows user to share event to facebook

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
                    ShareLinkContent Content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse(event.getEventLink()))
                            .setShareHashtag(new ShareHashtag.Builder()
                                    .setHashtag("#Staycation")
                                    .build())

                            .build();

                    shareButton.setShareContent(Content);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    //allows user to add event to calendar
    public void addToCalender (Event event) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, event.getEventName());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        intent.putExtra(CalendarContract.Events.DESCRIPTION,event.getEventDescription());
        startActivity(intent);

    }
    //inserts event into databse
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
        EventDbHelper mDbHelper = new EventDbHelper(this);

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
            Toast.makeText(this, "Error with saving event", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Event saved to favourites", Toast.LENGTH_SHORT).show();
        }
        // pass back data
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        int t = event.getId();
        data.putExtra("num", t);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
    private void deleteEvent(Event event) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + EventEntry.TABLE_NAME +
                " WHERE " + EventEntry.COLUMN_EVENT_VENUE + " = \"" + event.getEventVenue() + "\";";
        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);
    }
    @Override
    //display event on map when it is ready
    public void onMapReady(GoogleMap gMap) {
        googleMap = gMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        try {
            googleMap.setMyLocationEnabled(true);
        } catch (SecurityException se) {

        }

        //Edit the following as per you needs
        googleMap.setTrafficEnabled(true);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //

        LatLng placeLocation = new LatLng(event.getLatitude(), event.getLongitude()); //Make them global
        Marker placeMarker = googleMap.addMarker(new MarkerOptions().position(placeLocation)
                .title(event.getEventName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(placeLocation));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 1000, null);
    }

//    Byte y;
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (CheckIsDataAlreadyInDBorNot("events", "venue", "\""+event.getEventVenue()+ "\"")) {
//                y = 1;
//                event.setFavourite(y);
//            Glide.with(context) .load("") .error(R.drawable.add_white) .into(i);
//            }
//            else {
//                y = 0;
//                event.setFavourite(y);
//            Glide.with(context) .load("") .error(R.drawable.remove_white) .into(i);
//            }
//
//
//
//    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName,
                                               String dbfield, String fieldValue) {

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(context);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        String Query = "Select * from " + TableName + " where " + dbfield + " = " + fieldValue;
        Cursor cursor = db.rawQuery(Query, null);
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;

    }
}
