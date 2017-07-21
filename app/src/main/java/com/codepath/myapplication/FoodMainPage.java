package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.codepath.myapplication.Tourism.tempTourism;

import org.parceler.Parcels;

public class FoodMainPage extends AppCompatActivity {

    ImageView recipes;
    ImageView restaurants;
    String country;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main_page);
        country = Parcels.unwrap(getIntent().getParcelableExtra("country"));

        recipes = (ImageView) findViewById(R.id.recipesPic);
        restaurants = (ImageView) findViewById(R.id.restaurantsPic);

        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodMainPage.this, tempFOOD.class);
                i.putExtra("country", Parcels.wrap(country));
                context.startActivity(i);

            }
        });

        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodMainPage.this, tempTourism.class);
                i.putExtra("country", Parcels.wrap(country));
                startActivity(i);

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, NearbyActivity.class);
        startActivity(i);
    }
}
