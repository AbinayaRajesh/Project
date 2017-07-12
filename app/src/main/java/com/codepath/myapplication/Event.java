package com.codepath.myapplication;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eyobtefera on 7/11/17.
 */

public class Event implements Parcelable {
    public String eventName;
    public String eventDescription;
    public String eventUrl;
    public Integer eventCapacity;

    public Event(){

    }
    public static Event fromJSON(JSONObject jsonObject) throws JSONException {
        Event event = new Event();

        //extract the values from JSON
        event.eventName= jsonObject.getString("name");
        event.eventDescription = jsonObject.getString("description");
        event.eventUrl = jsonObject.getString("url");
        event.eventCapacity = jsonObject.getInt("capacity");
        return event;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.eventName);
        dest.writeString(this.eventDescription);
        dest.writeString(this.eventUrl);
        dest.writeValue(this.eventCapacity);
    }

    protected Event(Parcel in) {
        this.eventName = in.readString();
        this.eventDescription = in.readString();
        this.eventUrl = in.readString();
        this.eventCapacity = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getEventName() {
        return eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public Integer getEventCapacity() {
        return eventCapacity;
    }


}
