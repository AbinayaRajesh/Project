package com.codepath.myapplication.Event;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

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
    public static String [] alternateUrls = {"http://www.scpevents.com/images/creative_event_design-01.jpg","http://2.bp.blogspot.com/-CfoVwTOBp2Y/VI_PGcOsI_I/AAAAAAAAAHM/rYrDssJr5Qk/s1600/image.jpg", "https://creativelightingsf.com/img/carousel/car-01.jpg", "https://mindfuldesignconsulting.com/wp-content/uploads/2011/10/creative-lighting-design-outdoor-party-2.jpg", "http://kennedycreativeevents.com/wp-content/uploads/2014/01/green-people.jpg", "https://static1.squarespace.com/static/533353a3e4b0429a548a8446/t/54a78bede4b057f9e3964d76/1420266478889/lights_events_1366x768_68503.jpg?format=1500w", "https://www.mobilecause.com/wp-content/uploads/2015/12/event-fundraising-idea_50-50-raffle.png","https://www.edmonton.ca/homepage-background_canada-150.jpg", "http://www.photolakedistrict.co.uk/wp-content/uploads/events-FIREWORKS.jpg", "https://www.visit-hannover.com/var/storage/images/media/01-data-neu/bilder/hmtg/feuerwerkswettbewerb/2017/feuerwerkswettbewerb-titelbild-2017/14278480-1-ger-DE/Feuerwerkswettbewerb-Titelbild-2017_alias_300x225px.jpg"};
    public String eventLink;
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

    public String getEventLink() {
        return eventLink;
    }

    public String getDate(){
        date = startTime.split(" ");
        return date[0];
    }

    public void setFavourite(byte favourite) {
        this.favourite = favourite;
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
        event.eventLink = jsonObject.getString("url");
        try{
            object = jsonObject.getJSONObject("image");
            temp = object.getJSONObject("medium");
            event.eventUrl = temp.getString("url");
        } catch (org.json.JSONException exception )
        {
            int rnd = new Random().nextInt(alternateUrls.length);
            event.eventUrl = alternateUrls[rnd];
        //    event.eventUrl = "https://static1.squarespace.com/static/533353a3e4b0429a548a8446/t/54a78bede4b057f9e3964d76/1420266478889/lights_events_1366x768_68503.jpg?format=1500w";
        }

        event.eventVenue = jsonObject.getString("venue_name");
        return event;
    }



    public static Event consEvent (String name, String des, String url, String venue,
                                   String startTime, String stopTime, float lat, float lng,
                                   byte fav, int id) {
        Event e = new Event();
        e.id = id;
        e.eventName  = name;
        e.eventDescription = des;
        e.eventUrl = url;
        e.eventVenue = venue;
        e.startTime = startTime;
        e.stopTime = stopTime;
        e.latitude = lat;
        e.longitude = lng;
        e.favourite = fav;
        return e;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.eventLink);
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
        this.id = in.readInt();
        this.eventLink = in.readString();
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
