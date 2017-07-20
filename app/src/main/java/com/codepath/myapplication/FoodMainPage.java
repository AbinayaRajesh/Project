package com.codepath.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.codepath.myapplication.Tourism.tempTourism;

public class FoodMainPage extends AppCompatActivity {

    ImageView recipes;
    ImageView restaurants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main_page);

        recipes = (ImageView) findViewById(R.id.recipesPic);
        restaurants = (ImageView) findViewById(R.id.restaurantsPic);

        recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodMainPage.this, tempFOOD.class);
                startActivity(i);

            }
        });

        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FoodMainPage.this, tempTourism.class);
                startActivity(i);

            }
        });





    }
}
