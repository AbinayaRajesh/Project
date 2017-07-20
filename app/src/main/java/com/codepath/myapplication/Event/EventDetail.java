package com.codepath.myapplication.Event;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import java.util.logging.Handler;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class EventDetail extends AppCompatActivity {
    Event event;
    TextView tvEventName;
    ImageView ivEventImage;
    Context context = this;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        tvEventName = (TextView) findViewById(R.id.tvEventName);
        ivEventImage = (ImageView) findViewById(R.id.ivEventImage);
        event = (Event) Parcels.unwrap(getIntent().getParcelableExtra("event"));

        tvEventName.setText(event.getEventName());

        Glide.with(context).
                load(event.getEventUrl()).
                bitmapTransform(new RoundedCornersTransformation(context, 15, 0)).
                diskCacheStrategy(DiskCacheStrategy.ALL).
                into(ivEventImage);

    }
}
