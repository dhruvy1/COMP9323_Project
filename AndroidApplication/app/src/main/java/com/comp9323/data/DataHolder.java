package com.comp9323.data;

import android.content.Context;

import com.comp9323.data.beans.Event;
import com.comp9323.data.beans.FoodDeal;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.data.beans.User;

import java.util.List;
import java.util.Vector;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
 * Created by thomas on 11/9/2017.
 */

public class DataHolder {

    private static final DataHolder DH = new DataHolder();

    private User user;
    private Context context;
    private List<Event> events;
    private boolean eventResponse;

    private DataHolder() {
        events = new Vector<>();
        eventResponse = false;
    }

    public static DataHolder getInstance() {
        return DH;
    }

    /**
     * Self
     */
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * base context
     */
    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Event functions
     */
    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent(int position) {
        return events.get(position);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void addEvents(List<Event> events) {
        events.addAll(events);
    }

    public void removeEvent(int position) {
        events.remove(position);
    }

    public void clearEvents() {
        events.clear();
    }

    public void saveEventResponse(boolean success) {
        eventResponse = success;
    }

    public boolean checkEventResponse(boolean success) {
        return eventResponse = success;
    }
}