package com.codepath.myapplication.FoodFolder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.FoodFragments.FoodPagerAdapter;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import static com.spotify.sdk.android.authentication.LoginActivity.REQUEST_CODE;


public class FoodActivity extends AppCompatActivity {
    Context context;
    String ll;
    ViewPager vpPager;
    FoodPagerAdapter pageAdapter;
    public Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

//        // CHANGE TAB BACKGROUND COLOR
//
//        View someView = findViewById(R.id.viewpagerFood);
//        View root = someView.getRootView();
//        root.setBackgroundColor(getResources().getColor(R.color.food_tab));

        ll = getIntent().getStringExtra("ll");
        country = (Country) Parcels.unwrap(getIntent().getParcelableExtra("country"));

        pageAdapter = new FoodPagerAdapter(getSupportFragmentManager(), this);
        context = this;
        // get the view pager
        vpPager = (ViewPager) findViewById(R.id.viewpagerFood);
        // set the adapter for the pager
        vpPager.setAdapter(pageAdapter);

        // setup the TabLayout to use the view pager
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabsFood);
        tabLayout.setupWithViewPager(vpPager);
        vpPager.setOffscreenPageLimit(2);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menusearch, menu);
        MenuItem searchItem = menu.findItem(R.id.searchBar);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // perform query here
                //Intent i = new Intent(context, SearchActivity.class);

                searchView.clearFocus();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }
    public void onMaps(MenuItem item) {
        Intent i = new Intent(this, tempDemoActivity.class);
        i.putExtra("country", Parcels.wrap(country));
        i.putExtra("ll", ll);
        startActivity(i);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above

        Intent p = new Intent(FoodActivity.this, FoodActivity.class );
        p.putExtra("country", Parcels.wrap(country));
        startActivity(p);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras\
            int i = data.getExtras().getInt("num", -1);

            // Event event = data.getExtras().getParcelable("event", event);
            // Toast the name to display temporarily on screen
            Toast.makeText(this, String.valueOf(i), Toast.LENGTH_SHORT).show();

        }
    }


}
