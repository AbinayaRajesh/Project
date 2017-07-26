package com.codepath.myapplication.Event;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by arajesh on 7/19/17.
 */


public class Event implements Parcelable {
    public String eventName;
    public String eventDescription;
    public String eventUrl;
    public String eventVenue;
    public String startTime;
    public String stopTime;
    public String [] date;
    byte favourite;
    public int id;


    public float latitude;
    public float longitude;

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public int getId() {
        return id;
    }

    public Event(){}

    public String getEventName() {
        return eventName;
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

    public String getDate(){
        date = startTime.split(" ");
        return date[0];
    }


    public byte isFavourite() {
        return favourite;
    }

    public static Event fromJson(int i, JSONObject jsonObject) throws JSONException {
        Event event = new Event();
        JSONObject object;
        JSONObject temp;
        //extract the values from JSON
        event.id = i;
        event.favourite=0;
        event.eventName = jsonObject.getString("title");
        event.latitude = (float) jsonObject.getDouble("latitude");
        event.longitude = (float) jsonObject.getDouble("longitude");
        event.eventDescription = jsonObject.getString("description");
        event.startTime = jsonObject.getString("start_time");
        event.stopTime = jsonObject.getString("stop_time");
        try{
            object = jsonObject.getJSONObject("image");
            temp = object.getJSONObject("medium");
            event.eventUrl = temp.getString("url");
        } catch (org.json.JSONException exception )
        {
            event.eventUrl = "https://static1.squarespace.com/static/533353a3e4b0429a548a8446/t/54a78bede4b057f9e3964d76/1420266478889/lights_events_1366x768_68503.jpg?format=1500w";
        }

        event.eventVenue = jsonObject.getString("venue_name");
        return event;
    }

    public static JSONObject toJson(Event e) throws JSONException {
        JSONObject object = new JSONObject();
        //extract the values from JSON
//        object.put("title", e.getEventName());
//        event.id = i;
//        event.favourite=0;
//        event.eventName = jsonObject.getString("title");
//        event.latitude = (float) jsonObject.getDouble("latitude");
//        event.longitude = (float) jsonObject.getDouble("longitude");
//        event.eventDescription = jsonObject.getString("description");
//        event.startTime = jsonObject.getString("start_time");
//        event.stopTime = jsonObject.getString("stop_time");
//        try{
//            object = jsonObject.getJSONObject("image");
//            temp = object.getJSONObject("medium");
//            event.eventUrl = temp.getString("url");
//        } catch (org.json.JSONException exception )
//        {
//            event.eventUrl = "http://s3.amazonaws.com/churchplantmedia-cms/grace_community_church_tucson_az/events_medium.jpg";
//        }
//
//        event.eventVenue = jsonObject.getString("venue_name");
        return object;
    }

    public Event (String name, String des, String url, String venue, String startTime, String stopTime, float lat, float lng, byte fav) {
        Event e = new Event();
        e.eventName  = name;
        e.eventDescription = des;
        e.eventUrl = url;
        e.eventVenue = venue;
        e.startTime = startTime;
        e.stopTime = stopTime;
        e.latitude = lat;
        e.longitude = lng;
        e.favourite = fav;
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
        dest.writeString(this.eventVenue);
        dest.writeString(this.startTime);
        dest.writeString(this.stopTime);
        dest.writeFloat(this.latitude);
        dest.writeFloat(this.longitude);
        dest.writeByte(this.favourite);
    }

    protected Event(Parcel in) {
        this.eventName = in.readString();
        this.eventDescription = in.readString();
        this.eventUrl = in.readString();
        this.eventVenue = in.readString();
        this.startTime = in.readString();
        this.stopTime = in.readString();
        this.latitude = in.readFloat();
        this.longitude = in.readFloat();
        this.favourite = in.readByte();
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

}
