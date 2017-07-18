package com.codepath.myapplication.Event;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by eyobtefera on 7/11/17.
 */
@org.parceler.Parcel
public class Event  {
    public String eventName;
    public String eventDescription;
    public String eventUrl;
    public String eventVenue;

    public Event(){

    }

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


    public static Event fromJson(JSONObject jsonObject) throws JSONException {
        Event event = new Event();
        JSONObject object;
        JSONObject temp;
        //extract the values from JSON
        event.eventName= jsonObject.getString("title");
        event.eventDescription = jsonObject.getString("description");
        object = jsonObject.getJSONObject("image");
        temp = object.getJSONObject("medium");
        event.eventUrl = temp.getString("url");
        //if ()
        event.eventVenue = jsonObject.getString("venue_name");
        return event;
    }
}
