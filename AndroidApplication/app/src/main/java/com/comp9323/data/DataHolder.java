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
    private List<FoodDeal> foodDealList;
    private final Vector<FoodPlace> foodPlaceList;
    private List<Event> events;
    private boolean eventResponse;

    private DataHolder() {
        foodDealList = new Vector<>();
        foodPlaceList = new Vector<>();
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
     * FoodPlace
     */
    public FoodPlace getFoodPlace(int Index) {
        return foodPlaceList.get(Index);
    }

    public FoodPlace getFoodPlaceWithID(int id) {
        for (FoodPlace place : foodPlaceList) {
            if (place.getId() == id)
                return place;
        }
        return null;
    }

    public void findAndReplaceFoodPlace(FoodPlace fp) {
        for (int i = 0; i < foodPlaceList.size(); i++) {
            if (foodPlaceList.get(i).getId() == fp.getId()) {
                foodPlaceList.set(i, fp);
            }
        }
    }

    public void updateFoodPlaceRating(FoodPlace newfp) {
        for (int i = 0; i < foodPlaceList.size(); i++) {
            if (foodPlaceList.get(i).getId() != newfp.getId())
                continue;
            foodPlaceList.get(i).setRating(newfp.getRating());
        }
    }

    public List<FoodPlace> getFoodPlaceList() {
        return foodPlaceList;
    }

    public void addFoodPlace(FoodPlace fp) {
        foodPlaceList.add(fp);
    }

    public void addFoodPlaces(List<FoodPlace> fps) {
        for (FoodPlace fp : fps) {
            addFoodPlace(fp);
        }
    }

    public void removeFoodPlacel(int Index) {
        foodPlaceList.remove(Index);
    }

    public void clearFoodPlaceList() {
        foodPlaceList.clear();
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