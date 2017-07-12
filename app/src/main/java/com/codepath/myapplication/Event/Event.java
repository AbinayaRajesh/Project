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
