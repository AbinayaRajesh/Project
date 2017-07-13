package com.codepath.myapplication.Options;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import com.codepath.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Container class to hold Option information.
public class Option implements Serializable {
    private String mPlace;
    private int mThumbnailDrawable;
    private String mTitle;

    public Option(String place, int thumbnailDrawable, String title) {
        mPlace = place;
        mThumbnailDrawable = thumbnailDrawable;
        mTitle = title;
    }

    public String getPlace() {
        return mPlace;
    }

    public int getThumbnailDrawable() {
        return mThumbnailDrawable;
    }

    public String getTitle() {
        return mTitle;
    }

    // Returns a list of contacts
    public static List<Option> getContacts() {
        List<Option> options = new ArrayList<>();
        options.add(new Option("India", R.drawable.sample_events, "EVENTS"));
        options.add(new Option("India", R.drawable.sample_food, "FOOD"));
        options.add(new Option("India", R.drawable.sample_sports, "SPORTS"));
        options.add(new Option("India", R.drawable.sample_tourism, "TOURISM"));
        return options;
    }

    // Returns a random contact
    public static Option getRandomContact(Context context) {

        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_countries);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactThumbnails = resources.obtainTypedArray(R.array.contact_thumbnails);
        int thumbnail = (int) (Math.random() * contactThumbnails.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_titles);
        int number = (int) (Math.random() * contactNumbers.length());

        return new Option(contactNames.getString(name), contactThumbnails.getResourceId(thumbnail, R.drawable.sample_events), contactNumbers.getString(number));
    }
}
