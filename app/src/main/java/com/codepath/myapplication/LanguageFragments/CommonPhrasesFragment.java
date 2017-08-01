package com.codepath.myapplication.LanguageFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.LanguageActivity;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

public class CommonPhrasesFragment extends LanguageMainActivity {

    int option;


    public CommonPhrasesFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_common_phrases);
    }




    public void onMaps(MenuItem item) {
        Intent i = new Intent(getContext(), tempDemoActivity.class);
        startActivity(i);
    }


    public void onEvents(MenuItem item) {
        Intent i = new Intent(getContext(), FavouriteActivity.class);
        startActivity(i);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_translate, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_dropdown1: {
                option = 1;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }

            case R.id.action_dropdown2: {
                option = 2;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown3: {
                option = 3;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown4: {
                option = 4;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            default:
                option=5;
                // Arriving at Your Destination


                return super.onOptionsItemSelected(item);

        }
        return true;
    }






}
