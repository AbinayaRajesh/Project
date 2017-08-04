package com.codepath.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Database.EventDbHelper;
import com.codepath.myapplication.Database.FoodContract.FoodEntry;
import com.codepath.myapplication.Database.SavedRecipesActivity;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FoodDetail extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    Food recipe;
    TextView tvDetailRecipeName;
    ImageView tvRecipePic;
    RatingBar rbRating;
    TextView tvIngredients;
    Context context = this;
    int count = 0;
    ImageButton i;

    // private EditText searchInput;
    private ListView videosFound;
    private Handler handler;

    public List<VideoItem> searchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);
        tvDetailRecipeName = (TextView) findViewById(R.id.tvDetailRecipeName);
        tvRecipePic = (ImageView) findViewById(R.id.tvRecipePic);
        rbRating = (RatingBar) findViewById(R.id.rbRating);
        tvIngredients = (TextView) findViewById(R.id.tvIngredients);
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.pvYoutube);


        recipe = (Food) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        tvDetailRecipeName.setText(recipe.getName());
        rbRating.setRating(recipe.getRating());
        tvIngredients.setText(recipe.getIngredients());
        Glide.with(context)
                .load(recipe.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 10, 0))
                .into(tvRecipePic);

        videosFound = (ListView) findViewById(R.id.videos_found);

        handler = new Handler();

        searchOnYoutube(recipe.getName());

        playerView.initialize(YoutubeConnector.KEY, (YouTubePlayer.OnInitializedListener) context);


        // Food database stuff

        i = (ImageButton) findViewById(R.id.add);
        i.setImageResource(R.drawable.add_white);
        if (recipe.isFavourite()==1) {
            Glide.with(context) .load("") .error(R.drawable.remove_white) .into(i);
        }
        else {
            Glide.with(context) .load("") .error(R.drawable.add_white) .into(i);
        }

        i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recipe.favourite==0){
                    i.setImageResource(R.drawable.remove_white);
                    insertRecipe(recipe);
                    Byte y = 1;
                    recipe.setFavourite(y);
                    Intent in = new  Intent(FoodDetail.this, SavedRecipesActivity.class);
                    startActivity(in);
                }
                else {
                    i.setImageResource(R.drawable.add_white);
                    deleteFood(recipe);
                    Byte y = 0;
                    recipe.setFavourite(y);
                    Intent in = new  Intent(FoodDetail.this, SavedRecipesActivity.class);
                    startActivity(in);
                }
            }
        });

        // FACEBOOK SHARING

        new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
                    ShareLinkContent Content = new ShareLinkContent.Builder()
                            .setContentUrl(Uri.parse("https://www.google.com/search?site=&q="+recipe.getName()))
                            .setShareHashtag(new ShareHashtag.Builder()
                                    .setHashtag("#Staycation")
                                    .build())

                            .build();

                    shareButton.setShareContent(Content);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();


    }

    private void searchOnYoutube(final String keywords) {
        new Thread(){
            @Override
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(FoodDetail.this);
                searchResults = yc.search(keywords);
                searchResults=searchResults.subList(0,1);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound() {
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.video_item, searchResults) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.video_item, parent, false);
                }
                TextView description = (TextView) convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                description.setText(searchResult.getDescription());

                return convertView;
            }
        };
        videosFound.setAdapter(adapter);


    }



    private void insertRecipe(Food recipe) {

        deleteFood(recipe);

        String nameString = recipe.getName();
        String urlString = recipe.getImageUrl();
        int ratingInt = recipe.getRating();
        int idInt = recipe.getId();


        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
        ContentValues values = new ContentValues();
        values.put(FoodEntry.COLUMN_FOOD_NAME, nameString);
        values.put(FoodEntry.COLUMN_FOOD_URL, urlString);
        values.put(FoodEntry.COLUMN_FOOD_RATING, ratingInt);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId = db.insert(FoodEntry.TABLE_NAME, null, values);


        // pass back data

        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        int t = recipe.getId();
        data.putExtra("num", t);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent

    }

    private void deleteFood(Food recipe) {


        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_FOOD_TABLE =  "DELETE FROM " + FoodEntry.TABLE_NAME +
                " WHERE " + FoodEntry.COLUMN_FOOD_NAME + " = \"" + recipe.getName() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_FOOD_TABLE);

    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b) {
            for(int j = 0; j<200000000; j++){} //needed to delay to give time for youtube player to load and not crash

         //   youTubePlayer.cueVideo(searchResults.get(0).getId());
            youTubePlayer.loadVideo(searchResults.get(0).getId()); //allows autoplay rather than clicking to play
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();
    }
}
