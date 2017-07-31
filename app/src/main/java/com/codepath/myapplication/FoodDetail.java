package com.codepath.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
    Context context = this;
    int count = 0;
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
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.pvYoutube);


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
        playerView.initialize(YoutubeConnector.KEY, (YouTubePlayer.OnInitializedListener) context);

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

                TextView title = (TextView) convertView.findViewById(R.id.video_title);
                TextView description = (TextView) convertView.findViewById(R.id.video_description);

                VideoItem searchResult = searchResults.get(position);

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


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if(!b) {
            youTubePlayer.cueVideo(searchResults.get(0).getId());
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this, "Initialization Failed", Toast.LENGTH_LONG).show();
    }
}
