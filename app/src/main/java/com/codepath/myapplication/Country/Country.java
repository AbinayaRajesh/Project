package com.codepath.myapplication.Country;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by emilylroth on 7/11/17.
 */


public class Country implements Parcelable {
    String name;
    String continent;
    String language;
    String flagUrl;
    String nativeName;
    public Country(){}

    public static Country fromJSON(JSONObject object) throws JSONException{
        Country country = new Country();
        country.name = object.getString("Name");
        country.continent = object.getString("Region");
        country.language = object.getString("NativeLanguage");
        country.flagUrl = object.getString("FlagPng");
        country.nativeName = object.getString("NativeName");
        return country;
    }

    public static Country consCountry () {
        Country country = new Country();
        country.name = "China";
        return country;
    }

    public String getName() {
        return name;
    }

    public String getContinent() {
        return continent;
    }

    public String getLanguage() {
        return language;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public String getNativeName() {
        return nativeName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.continent);
        dest.writeString(this.language);
        dest.writeString(this.flagUrl);
        dest.writeString(this.nativeName);
    }

    protected Country(Parcel in) {
        this.name = in.readString();
        this.continent = in.readString();
        this.language = in.readString();
        this.flagUrl = in.readString();
        this.nativeName = in.readString();
    }

    public static final Parcelable.Creator<Country> CREATOR = new Parcelable.Creator<Country>() {
        @Override
        public Country createFromParcel(Parcel source) {
            return new Country(source);
        }

        @Override
        public Country[] newArray(int size) {
            return new Country[size];
        }
    };
}
