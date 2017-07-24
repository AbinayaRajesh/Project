package com.codepath.myapplication.Event;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.myapplication.CatalogActivity;
import com.codepath.myapplication.R;
import com.codepath.myapplication.data.EventContract.EventEntry;
import com.codepath.myapplication.data.EventDbHelper;

import org.jsoup.Jsoup;

import java.text.DateFormatSymbols;
import java.util.logging.Handler;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class EventDetail extends AppCompatActivity {
    Event event;
    TextView tvEventName;
    ImageView ivEventImage;
    TextView tvDescription;
    TextView tvMonth;
    TextView tvDay;
    String month;
    ImageView ivCalender;

    String [] date;

    Context context = this;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        tvEventName = (TextView) findViewById(R.id.tvEventName);
        ivEventImage = (ImageView) findViewById(R.id.ivEventImage);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        tvMonth = (TextView) findViewById(R.id.tvMonth);
        ivCalender = (ImageView) findViewById(R.id.calender);

        event = (Event) getIntent().getParcelableExtra("event");

        tvEventName.setText(event.getEventName());
        String text = Jsoup.parse(event.getEventDescription()).text();
        if (text==null) {
            tvDescription.setText("No description available");
        }
        else {
            tvDescription.setText(text);
        }
        date = event.getDate().split("-");
        month = new DateFormatSymbols().getMonths()[Integer.parseInt(date[1])-1];
        tvMonth.setText(month + " " + date[2] + ", " + date[0]);


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

        final ToggleButton tB = (ToggleButton) findViewById(R.id.toggleButton);
        tB.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(tB.isChecked()){
                    insertEvent(event);
                    Intent i = new  Intent(EventDetail.this, CatalogActivity.class);
                    startActivity(i);
                }
                else {
                    deleteEvent(event);
                    Intent i = new  Intent(EventDetail.this, CatalogActivity.class);
                    startActivity(i);
                }
            }
        });



    }

    public void addToCalender (Event event) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, event.getEventName());
//        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
//                event.getStartTime());
//        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
//                event.getStopTime());
        intent.putExtra(CalendarContract.Events.ALL_DAY, false);// periodicity
        intent.putExtra(CalendarContract.Events.DESCRIPTION,event.getEventDescription());
        startActivity(intent);

    }


    private void insertEvent(Event event) {

        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        // mNameEditText.getText().toString().trim();
        String nameString = event.getEventName();
        String descriptionString = event.getEventDescription();
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
            Toast.makeText(this, "Event saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteEvent(Event event) {

        String num = String.valueOf(event.getId());
        String mun = EventEntry._ID;

        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_EVENTS_TABLE =  "DELETE FROM " + EventEntry.TABLE_NAME +
                " WHERE " + EventEntry.COLUMN_EVENT_UNIQUE_KEY + " = " + String.valueOf(event.getId()) + ";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_EVENTS_TABLE);

    }


}
