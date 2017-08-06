package com.codepath.myapplication.Options;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.ImageAdapterSwipe;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Maps.MapActivity;
import com.codepath.myapplication.Photo;
import com.codepath.myapplication.PhotoClient;
import com.codepath.myapplication.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.spotify.sdk.android.authentication.AuthenticationClient;
import com.spotify.sdk.android.authentication.AuthenticationRequest;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.player.Config;
import com.spotify.sdk.android.player.ConnectionStateCallback;
import com.spotify.sdk.android.player.Error;
import com.spotify.sdk.android.player.Player;
import com.spotify.sdk.android.player.PlayerEvent;
import com.spotify.sdk.android.player.Spotify;
import com.spotify.sdk.android.player.SpotifyPlayer;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class OptionsActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener, SpotifyPlayer.NotificationCallback,
        ConnectionStateCallback {
    private RecyclerView rvOptions;
    private OptionsAdapter mAdapter;
    private List<Option> options;
    public Country country;
    public ArrayList<Photo> photos;
    public static List<String> urls;
    Context context;
    ImageAdapterSwipe sAdapter;
    GoogleApiClient mGoogleApiClient;
    Location mLocation;
    LocationManager mLocationManager;
    LocationRequest mLocationRequest;
    CarouselView carouselView;
    Double longitude;
    Double latitude;
    String ll;
    boolean volume;
    public final static String TAG = "CountryList";


    public static Player mPlayer;
    //  public PhotoClient photoClient;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photos = new ArrayList<>();
        urls = new ArrayList<>();

        context = this;
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        setContentView(R.layout.activity_options);
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));
        setTitle(country.getName());
        // Find RecyclerView and bind to adapter
        rvOptions = (RecyclerView) findViewById(R.id.rvOptions);

        // allows for optimizations
        rvOptions.setHasFixedSize(true);

        // Define 2 column grid layout
        final GridLayoutManager layout = new GridLayoutManager(OptionsActivity.this, 2);

        // Unlike ListView, you have to explicitly give a LayoutManager to the RecyclerView to position items on the screen.
        // There are three LayoutManager provided at the moment: GridLayoutManager, StaggeredGridLayoutManager and LinearLayoutManager.
        rvOptions.setLayoutManager(layout);

        // geAt data
        options = Option.getContacts();
        this.client = new AsyncHttpClient();
        url = PhotoClient.createURL(country.getName());
     //   getPhoto(url);

        // Create an adapter
        mAdapter = new OptionsAdapter(OptionsActivity.this, options);
        mAdapter.optionsActivity = this;

        // Bind adapter to list
        rvOptions.setAdapter(mAdapter);
        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(5);
        carouselView.setImageListener(imageListener);
        AuthenticationRequest.Builder builder = new AuthenticationRequest.Builder(CLIENT_ID,
                AuthenticationResponse.Type.TOKEN,
                REDIRECT_URI);
        builder.setScopes(new String[]{"user-read-private", "streaming"});
        AuthenticationRequest request = builder.build();

        AuthenticationClient.openLoginActivity(this, REQUEST_CODE, request);


    }

    ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(final int position, final ImageView imageView) {
            client.get(url, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        JSONObject results = response.getJSONObject("photos");
                        for (int i=0; i<5; i++) {
                            JSONArray photoStream = results.getJSONArray("photo");
                            Photo photo = Photo.fromJson(photoStream.getJSONObject(i));

                            photos.add(photo);
                            urls.add("https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg");
                            //notify adapter
                            // adapter.notifyItemInserted(countries.size()-1);
                            //      Glide.with(context)
                            //              .load("https://farm" + photo.getFarm() + ".staticflickr.com/" + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() + ".jpg")
                            //              .into((ImageView) findViewById(R.id.ivOptionsImage));
                        }
                        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

                        sAdapter = new ImageAdapterSwipe(OptionsActivity.this);
                        viewPager.setAdapter(sAdapter);
                        Glide.with(context)
                                .load(OptionsActivity.urls.get(position))
                                .into(imageView);

                    } catch (JSONException e) {
                        // logError("Failed to parse now playing movies", e, true);
                    }
                }


            });

        }
    };




    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, MapActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onEvents(MenuItem item) {
        Intent i = new Intent(this, FavouriteActivity.class);
        i.putExtra("ll", ll);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }



    // public static boolean canAddItem = false;
    public static int isPlaying () {
        if (mPlayer != null && mPlayer.getPlaybackState().isPlaying) return 1;
        else return 0;
    }



    public static void pausePlayer () {
        if (mPlayer != null && mPlayer.getPlaybackState().isPlaying) {
            mPlayer.pause(new Player.OperationCallback() {
                @Override
                public void onSuccess() { }
                @Override
                public void onError(Error error) { }
            });
        }

    }

    public static void playPlayer () {
        if (mPlayer != null && !mPlayer.getPlaybackState().isPlaying) {
            mPlayer.resume(new Player.OperationCallback() {
                @Override
                public void onSuccess() { }
                @Override
                public void onError(Error error) { }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        setMenuVolume(menu,3);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            longitude = mLocation.getLongitude();
            latitude = mLocation.getLatitude();
            String lat = String.valueOf(latitude);
            String lng = String.valueOf(longitude);
            ll = lat + "," + lng;
            mAdapter.changeLL(ll);
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)
                .setInterval(2*1000)
                .setFastestInterval(2000);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
        String lat = String.valueOf(latitude);
        String lng = String.valueOf(longitude);
        ll = lat + "," + lng;
    }
    public String getLL() {
        return ll;
    }

    // TODO: Replace with your client ID
    private static final String CLIENT_ID = "f369e0b40d2941f585239fae425f7ec5";
    // TODO: Replace with your redirect URI
    private static final String REDIRECT_URI = "http://localhost:8888/callback";
    String ID;
    public static final int AUTH_TOKEN_REQUEST_CODE = 0x10;
    public static final int AUTH_CODE_REQUEST_CODE = 0x11;

    // Request code that will be used to verify if the result comes from correct activity
    // Can be any integer
    private static final int REQUEST_CODE = 1337;

    // public static Player mPlayer;
    private final OkHttpClient mOkHttpClient = new OkHttpClient();
    private String mAccessToken;
    private String mAccessCode;
    private Call mCall;
    AsyncHttpClient client;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);




        // Check if result comes from the correct activity
        if (requestCode == REQUEST_CODE) {
            AuthenticationResponse response = AuthenticationClient.getResponse(resultCode, intent);
            if (response.getType() == AuthenticationResponse.Type.TOKEN) {
                mAccessToken = response.getAccessToken();
                mAccessCode = response.getCode();
                Config playerConfig = new Config(this, response.getAccessToken(), CLIENT_ID);
                Spotify.getPlayer(playerConfig, this, new SpotifyPlayer.InitializationObserver() {
                    @Override
                    public void onInitialized(SpotifyPlayer spotifyPlayer) {
                        mPlayer = spotifyPlayer;
                        mPlayer.addConnectionStateCallback(OptionsActivity.this);
                        mPlayer.addNotificationCallback(OptionsActivity.this);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e("MainActivity", "Could not initialize player: " + throwable.getMessage());
                    }
                });
            }
        }


//
//        if (AUTH_TOKEN_REQUEST_CODE == requestCode) {
//            mAccessToken = response.getAccessToken();
//            // updateTokenView();
//        } else if (AUTH_CODE_REQUEST_CODE == requestCode) {
//            mAccessCode = response.getCode();
//            // updateCodeView();
//        }

    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }

    @Override
    public void onPlaybackEvent(PlayerEvent playerEvent) {
        Log.d("MainActivity", "Playback event received: " + playerEvent.name());
        switch (playerEvent) {
            // Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    public void onPlaybackError(Error error) {
        Log.d("MainActivity", "Playback error received: " + error.name());
        switch (error) {
            // Handle error type as necessary
            default:
                break;
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("MainActivity", "User logged in");
        //final AuthenticationRequest request = getAuthenticationRequest(AuthenticationResponse.Type.TOKEN);

        String q = country.getAdjective();
        if (q==null) q = country.getName();

        final Request request = new Request.Builder()
                .url("https://api.spotify.com/v1/search?q=" + q + "%20national%20anthem&type=track")
                .addHeader("Authorization","Bearer " + mAccessToken)
                .build();

        // AuthenticationClient.openLoginActivity(this, AUTH_TOKEN_REQUEST_CODE, request);

        cancelCall();
        mCall = mOkHttpClient.newCall(request);

        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    final JSONObject jsonObject = new JSONObject(response.body().string());
                    setResponse(jsonObject);
                } catch (JSONException e) {

                }
            }
        });

    }

    @Override
    public void onLoggedOut() {
        Log.d("MainActivity", "User logged out");
    }

    @Override
    public void onLoginFailed(Error error) {

    }


    @Override
    public void onTemporaryError() {
        Log.d("MainActivity", "Temporary error occurred");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("MainActivity", "Received connection message: " + message);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void setResponse(final JSONObject text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final TextView responseView = (TextView) findViewById(R.id.textView);
                try {
                    JSONObject o = text.getJSONObject("tracks");
                    JSONObject itemArray = (JSONObject) o.getJSONArray("items").get(0);
                    ID = itemArray.getString("id");
                    mPlayer.setRepeat(new Player.OperationCallback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Error error) {

                        }
                    }, true);
                    mPlayer.playUri(null, "spotify:track:" + ID, 0, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // JSONArray eventArray = eventsonline.getJSONArray("event");
                //responseView.setText(text);
            }
        });
    }

    private void cancelCall() {
        if (mCall != null) {
            mCall.cancel();
        }
    }

    private AuthenticationRequest getAuthenticationRequest(AuthenticationResponse.Type type) {
        return new AuthenticationRequest.Builder(CLIENT_ID, type, REDIRECT_URI)
                .setShowDialog(false)
                .setScopes(new String[]{"user-read-email"})
                .setCampaign("your-campaign-token")
                .build();
    }

    public static void onVolume (MenuItem  mi) {
        if (isPlaying()==1) {
            mi.setIcon(R.drawable.ic_volume_off_white);
            pausePlayer();

        } else {
            mi.setIcon(R.drawable.ic_volume_up_white);
            playPlayer();
        }
    }

    public static void setMenuVolume (Menu menu, int pos) {
        int i = isPlaying();
        if (i==1) menu.getItem(pos).setIcon(R.drawable.ic_volume_up_white);
        else menu.getItem(pos).setIcon(R.drawable.ic_volume_off_white);
        if (mPlayer==null) menu.getItem(pos).setIcon(R.drawable.ic_volume_up_white);
    }

}




