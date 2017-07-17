package com.codepath.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FoodDetail extends AppCompatActivity {
    Food recipe;
    TextView tvDetailRecipeName;
    ImageView tvRecipePic;
    RatingBar rbRating;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        tvDetailRecipeName = (TextView) findViewById(R.id.tvDetailRecipeName);
        tvRecipePic = (ImageView) findViewById(R.id.tvRecipePic);
        rbRating = (RatingBar) findViewById(R.id.rbRating);

        recipe = (Food) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        tvDetailRecipeName.setText(recipe.getName());
        rbRating.setRating(recipe.getRating());
        Glide.with(context)
                .load(recipe.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 35, 0))
                .into(tvRecipePic);
    }
}
