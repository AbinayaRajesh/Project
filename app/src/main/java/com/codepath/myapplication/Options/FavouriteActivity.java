package com.codepath.myapplication.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Database.SavedEventsActivity;
import com.codepath.myapplication.Database.SavedRecipesActivity;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FavouriteActivity extends AppCompatActivity {

    Country country;
    ImageView events;
    ImageView food;
    ImageView tourism;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));

        events = (ImageView) findViewById(R.id.eventsPic);
        food = (ImageView) findViewById(R.id.foodPic);
        tourism = (ImageView) findViewById(R.id.TourismPic);

        Glide.with(this)
                .load(R.drawable.events_tab)
                .bitmapTransform(new RoundedCornersTransformation(this, 3, 3))
                .into(events);
        Glide.with(this)
                .load(R.drawable.food_tab)
                .bitmapTransform(new RoundedCornersTransformation(this, 3, 3))
                .into(food);
        Glide.with(this)
                .load(R.drawable.tourism_tab)
                .bitmapTransform(new RoundedCornersTransformation(this, 3, 3))
                .into(tourism);


        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FavouriteActivity.this, SavedEventsActivity.class);
                i.putExtra("country", Parcels.wrap(country));
                startActivity(i);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FavouriteActivity.this, SavedRecipesActivity.class);
                i.putExtra("country", Parcels.wrap(country));
                startActivity(i);
            }
        });

        tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FavouriteActivity.this, SavedEventsActivity.class);
                i.putExtra("country", Parcels.wrap(country));
                startActivity(i);
            }
        });




    }
}
