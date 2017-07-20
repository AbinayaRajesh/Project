package com.codepath.myapplication.Event;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.myapplication.R;

import org.jsoup.Jsoup;
import org.parceler.Parcels;

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

        event = (Event) Parcels.unwrap(getIntent().getParcelableExtra("event"));

        tvEventName.setText(event.getEventName());
        tvDescription.setText(Jsoup.parse(event.getEventDescription()).text());
        date = event.getDate().split("-");
        month = new DateFormatSymbols().getMonths()[Integer.parseInt(date[1])];
        tvMonth.setText(month + " " + date[2] + ", " + date[0]);


        Glide.with(context).
                load(event.getEventUrl()).
                bitmapTransform(new RoundedCornersTransformation(context, 15, 0)).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(ivEventImage);

        ivCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCalender(event);

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
}
