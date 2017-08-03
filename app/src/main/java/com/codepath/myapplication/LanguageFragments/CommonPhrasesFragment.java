package com.codepath.myapplication.LanguageFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.codepath.myapplication.Country.Country;
import com.codepath.myapplication.LanguageActivity;
import com.codepath.myapplication.MainActivity;
import com.codepath.myapplication.Maps.tempDemoActivity;
import com.codepath.myapplication.Options.FavouriteActivity;
import com.codepath.myapplication.R;

import org.parceler.Parcels;

public class CommonPhrasesFragment extends LanguageMainActivity {

    int option;
    String ll;
    Country country;


    public CommonPhrasesFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ll = LanguageActivity.ll;
        country = LanguageActivity.country;
        //setContentView(R.layout.activity_common_phrases);
    }




    public void onMaps(MenuItem item) {
        Intent i = new Intent(getContext(), tempDemoActivity.class);
        i.putExtra("country", Parcels.wrap(LanguageActivity.country));
        i.putExtra("ll", ll);
        startActivity(i);
    }


    public void onEvents(MenuItem item) {
        Intent i = new Intent(getContext(), FavouriteActivity.class);
        startActivity(i);
    }

    public void onHome(MenuItem item) {
        Intent i = new Intent(getContext(), MainActivity.class);
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
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }

            case R.id.action_dropdown2: {
                option = 2;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown3: {
                option = 3;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown4: {
                option = 4;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown5: {
                option = 5;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown6: {
                option = 6;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown7: {
                option = 7;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown8: {
                option = 8;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            case R.id.action_dropdown9: {
                option = 9;
                Intent i = new Intent(getContext(), LanguageActivity.class);
                i.putExtra("option", option);
                i.putExtra("country", Parcels.wrap(LanguageActivity.country));
                i.putExtra("ll", ll);
                startActivity(i);
                LanguageMainActivity.adapter.notifyDataSetChanged();
                break;
            }
            default:
                option=1;
                return super.onOptionsItemSelected(item);

        }
        return true;
    }






}
