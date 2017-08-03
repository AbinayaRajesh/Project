package com.codepath.myapplication.Options;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Database.SavedEventsActivity;
import com.codepath.myapplication.Database.SavedRecipesActivity;
import com.codepath.myapplication.Database.SavedTourismActivity;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.R;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FavouriteActivity extends AppCompatActivity {

    Country country;
    ImageView events;
    ImageView food;
    ImageView tourism;
    String ll;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_to_home, menu);

        return true;
    }


    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        events = (ImageView) findViewById(R.id.eventsPic);
        food = (ImageView) findViewById(R.id.foodPic);
        tourism = (ImageView) findViewById(R.id.TourismPic);

        ll = getIntent().getStringExtra("ll");


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
                i.putExtra("ll", ll);
                startActivity(i);
            }
        });
        food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FavouriteActivity.this, SavedRecipesActivity.class);
                i.putExtra("ll", ll);
                startActivity(i);
            }
        });

        tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FavouriteActivity.this, SavedTourismActivity.class);
                i.putExtra("ll", ll);
                startActivity(i);
            }
        });




    }
}
