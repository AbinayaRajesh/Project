package com.codepath.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// Container class to hold Contact information.
public class Contact implements Serializable {
    private String mPlace;
    private int mThumbnailDrawable;
    private String mTitle;

    public Contact(String place, int thumbnailDrawable, String title) {
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
    public static List<Contact> getContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("India", R.drawable.sample_events, "EVENTS"));
        contacts.add(new Contact("India", R.drawable.sample_food, "FOOD"));
        contacts.add(new Contact("India", R.drawable.sample_sports, "SPORTS"));
        contacts.add(new Contact("India", R.drawable.sample_tourism, "TOURISM"));
        return contacts;
    }

    // Returns a random contact
    public static Contact getRandomContact(Context context) {

        Resources resources = context.getResources();

        TypedArray contactNames = resources.obtainTypedArray(R.array.contact_countries);
        int name = (int) (Math.random() * contactNames.length());

        TypedArray contactThumbnails = resources.obtainTypedArray(R.array.contact_thumbnails);
        int thumbnail = (int) (Math.random() * contactThumbnails.length());

        TypedArray contactNumbers = resources.obtainTypedArray(R.array.contact_titles);
        int number = (int) (Math.random() * contactNumbers.length());

        return new Contact(contactNames.getString(name), contactThumbnails.getResourceId(thumbnail, R.drawable.sample_events), contactNumbers.getString(number));
    }
}
