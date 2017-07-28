package com.codepath.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class FoodDetail extends AppCompatActivity {
    Food recipe;
    TextView tvDetailRecipeName;
    ImageView tvRecipePic;
    RatingBar rbRating;
    Context context = this;
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

        recipe = (Food) Parcels.unwrap(getIntent().getParcelableExtra("recipe"));

        tvDetailRecipeName.setText(recipe.getName());
        rbRating.setRating(recipe.getRating());
        Glide.with(context)
                .load(recipe.getImageUrl())
                .bitmapTransform(new RoundedCornersTransformation(context, 35, 0))
                .into(tvRecipePic);




        // searchInput = (EditText) findViewById(R.id.search_input);
        videosFound = (ListView) findViewById(R.id.videos_found);

        handler = new Handler();

//        searchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                if(actionId == EditorInfo.IME_ACTION_DONE){
//                    searchOnYoutube(v.getText().toString());
//                    return false;
//                }
//                return true;
//            }
//        });
        searchOnYoutube(recipe.getName());

        addClickListener();



        // Food database stuff

        i = (ImageButton) findViewById(R.id.add);
        i.setImageResource(R.drawable.add_white);
        if (recipe.isFavourite()==1) {
            Glide.with(context) .load("") .error(R.drawable.add_white) .into(i);
        }
        else {
            Glide.with(context) .load("") .error(R.drawable.remove_white) .into(i);
        }

        i.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if(recipe.favourite==1){

                    i.setImageResource(R.drawable.remove_white);
                    insertRecipe(recipe);
                    Byte y = 0;
                    recipe.setFavourite(y);
                    Intent in = new  Intent(FoodDetail.this, SavedRecipesActivity.class);
                    startActivity(in);
                }
                else {
                    i.setImageResource(R.drawable.add_white);
                    deleteFood(recipe);
                    Byte y = 1;
                    recipe.setFavourite(y);
                    Intent in = new  Intent(FoodDetail.this, SavedRecipesActivity.class);
                    startActivity(in);
                }
            }
        });


    }

    private void searchOnYoutube(final String keywords) {
        new Thread(){
            @Override
            public void run() {
                YoutubeConnector yc = new YoutubeConnector(FoodDetail.this);
                searchResults = yc.search(keywords);
                searchResults=searchResults.subList(0,3);
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

                ImageView thumbnail = (ImageView) convertView.findViewById(R.id.video_thumbnail);
                TextView title = (TextView) convertView.findViewById(R.id.video_title);
                TextView description = (TextView) convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getDescription());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };
        videosFound.setAdapter(adapter);
    }

    private void addClickListener() {
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplication(), PlayerActivity.class);
                intent.putExtra("VIDEO_ID", searchResults.get(position).getId());
                startActivity(intent);
            }
        });
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

        // Show a toast message depending on whether or not the insertion was successful
        if (newRowId == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving event", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Event saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }

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
                " WHERE " + FoodEntry.COLUMN_FOOD_URL + " = \"" + recipe.getImageUrl() + "\";";

        // Create database helper
        EventDbHelper mDbHelper = new EventDbHelper(this);

        // Gets the database in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase();


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_FOOD_TABLE);

    }



}
