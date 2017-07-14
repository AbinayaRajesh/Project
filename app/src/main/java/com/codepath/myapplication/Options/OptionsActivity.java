package com.codepath.myapplication.Options;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

import java.util.List;


public class OptionsActivity extends AppCompatActivity {
    private RecyclerView rvOptions;
    private OptionsAdapter mAdapter;
    private List<Option> options;
    public Country country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        // get data
        options = Option.getContacts();



        // Create an adapter
        mAdapter = new OptionsAdapter(OptionsActivity.this, options);
        mAdapter.optionsActivity = this;

        // Bind adapter to list
        rvOptions.setAdapter(mAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

}
