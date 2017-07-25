package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.Tourism.tempTourism;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FoodMainPage extends AppCompatActivity {

    ImageView recipes;
    ImageView restaurants;

    Context context;

    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_main_page);

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

                Intent i = new Intent(FoodMainPage.this, tempFOOD.class);
                i.putExtra("country", Parcels.wrap(country));

                startActivity(i);


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



    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 12;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

}
