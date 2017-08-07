package com.codepath.myapplication.FoodFolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.Options.OptionsActivity;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.codepath.myapplication.Options.OptionsActivity.setMenuVolume;

public class FoodMainPage extends AppCompatActivity {

    ImageView recipes;
    ImageView restaurants;
    String ll;
    Context context;
    Country country;
    @Override
    //food main page allows users to either look at recipes or restaurants and be taken there on click
    //also includes menu bar actions
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main_page);
        Bundle bundleB = getIntent().getExtras();
        ll = bundleB.getString("ll");
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        recipes = (ImageView) findViewById(R.id.recipesPic);
        restaurants = (ImageView) findViewById(R.id.restaurantsPic);
        Glide.with(this)
                .load(R.drawable.recipes)
                .bitmapTransform(new RoundedCornersTransformation(context, 3, 3))
                .into(recipes);
        Glide.with(this)
                .load(R.drawable.restaurants)
                .bitmapTransform(new RoundedCornersTransformation(context, 3, 3))
                .into(restaurants);
        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodMainPage.this, Recipes.class);
                i.putExtra("country", Parcels.wrap(country));
                i.putExtra("ll", ll);
                startActivity(i);
            }
        });
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(FoodMainPage.this, Restaurants.class);
                i.putExtra("country", Parcels.wrap(country));
                i.putExtra("ll", ll);
                startActivity(i);
            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menumain, menu);
        setMenuVolume(menu,3);
        return true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, OptionsActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        startActivity(i);
    }
    public void onVolume (MenuItem  mi) {
        OptionsActivity.onVolume(mi);
    }
}
