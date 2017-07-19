package com.codepath.myapplication.Event;

import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by eyobtefera on 7/11/17.
 */

@Parcel
public class Event implements Parcelable{
    public String eventName;
    public String eventDescription;
    public String eventUrl;
    public String eventVenue;
    public String startTime;
    public String stopTime;


    float latitude;
    float longitude;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(android.os.Parcel parcel, int i) {

    }


    public Event(){

    }


    public float getLatitude() { return latitude; }

    public float getLongitude() { return longitude; }

    public String getEventName() {
        return eventName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public String getEventVenue() {
        return eventVenue;
    }


    public static Event fromJson(JSONObject jsonObject) throws JSONException {
        Event event = new Event();
        JSONObject object;
        JSONObject temp;
        //extract the values from JSON
        event.eventName= jsonObject.getString("title");
        event.latitude= (float) jsonObject.getDouble("latitude");
        event.longitude= (float) jsonObject.getDouble("longitude");
        event.eventDescription = jsonObject.getString("description");
        event.startTime = jsonObject.getString("start_time");
        event.stopTime = jsonObject.getString("stop_time");
        try{
            object = jsonObject.getJSONObject("image");
            temp = object.getJSONObject("medium");
            event.eventUrl = temp.getString("url");
        } catch (org.json.JSONException exception )
        {
            event.eventUrl = "http://s3.amazonaws.com/churchplantmedia-cms/grace_community_church_tucson_az/events_medium.jpg";
        }

        event.eventVenue = jsonObject.getString("venue_name");
        return event;
    }

    private Event(Parcel in) {
        mData = in.readInt();
        mName = in.readString();
        mInfo = in.readParcelable(MySubParcelable.class.getClassLoader());
    }


    public static final Parcelable.Creator<Event> CREATOR
            = new Parcelable.Creator<Event>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
