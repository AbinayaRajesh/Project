package com.codepath.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.parceler.Parcels;

public class FoodDetail extends AppCompatActivity {
    Food recipe;
    TextView tvDetailRecipeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        tvDetailRecipeName = (TextView) findViewById(R.id.tvDetailRecipeName);

        recipe = (Food) Parcels.unwrap(getIntent().getParcelableExtra(Food.class.getName()));

        tvDetailRecipeName.setText(recipe.getName());
    }
}
