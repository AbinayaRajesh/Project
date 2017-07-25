package com.codepath.myapplication.Options;

import com.codepath.myapplication.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Container class to hold Option information.
public class Option implements Serializable {
    private String mPlace;
    private int mThumbnailDrawable;
    private String mTitle;
    int mId;

    public Option(int id, String place, int thumbnailDrawable) {
        mId = id;
        mPlace = place;
        mThumbnailDrawable = thumbnailDrawable;
    }

    public String getPlace() {
        return mPlace;
    }

    public int getmId() {
        return mId;
    }

    public int getThumbnailDrawable() {
        return mThumbnailDrawable;
    }


    // Returns a list of contacts
    public static List<Option> getContacts() {
        List<Option> options = new ArrayList<>();
        options.add(new Option(1, "India", R.drawable.events1));
        options.add(new Option(2, "India", R.drawable.food1));
        options.add(new Option(3, "India", R.drawable.translate1));
        options.add(new Option(4, "India", R.drawable.tourism1));
        return options;
    }


}
