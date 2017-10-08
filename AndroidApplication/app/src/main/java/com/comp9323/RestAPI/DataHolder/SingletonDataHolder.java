package com.comp9323.RestAPI.DataHolder;

import android.content.Context;
import android.util.Log;

import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.Beans.FoodPlace;
import com.comp9323.RestAPI.Beans.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
 * Created by thomas on 11/9/2017.
 */

public class SingletonDataHolder {

    private static final SingletonDataHolder DH = new SingletonDataHolder();
    private User userSelf;
    private Context context;
    //Map<String, WeakReference<Object>> or Vector<WeakReference<Object>> objectData; //using "weakreference" allow destroy data when quit activities
    //vector<Object> ObjectData;
    //remember to initialize object list/map
    private List<FoodDeal> foodDealList;
    private final Vector<FoodPlace> foodPlaceList;
    private List<EventBean> events;
    private boolean eventResponse;
//    private Map<Integer, FoodDeal> foodDealMap;

    private SingletonDataHolder() {
        foodDealList = new Vector<>();
        foodPlaceList = new Vector<>();
        events = new Vector<>();
        eventResponse = false;
    }

    public static SingletonDataHolder getInstance() {
        return DH;
    }
    /**
     * Self
     */
    public User getUserSelf() {
        return this.userSelf;
    }
    public void setUserSelf(User user) {
        Log.v("tag", "written to DH");
        this.userSelf = user;
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
     * FOOD DEALS
     */
    public FoodDeal getFoodDeal(int Index){
        return foodDealList.get(Index);
    }
    public FoodDeal getFoodDealwithID(int id){
        for (FoodDeal ref: foodDealList) {
            if(ref.getId() == id)
                return ref;
        }
        return null;
    }
    public List<FoodDeal> getFoodDealList(){
        return foodDealList;
    }
    public void addFoodDeal(FoodDeal fd){
        foodDealList.add(fd);
    }
    public void findAndReplaceFoodDeal(FoodDeal fd){
        for(int i =0; i<foodDealList.size();i++){
            if(foodDealList.get(i).getId() == fd.getId()){
                foodDealList.set(i, fd);
            }
        }
    }
    public void addFoodDeals(List<FoodDeal> fds){
        for (FoodDeal fd: fds) {
            addFoodDeal(fd);
        }
    }
    public void removeFoodDeal(int Index){
        foodDealList.remove(Index);
    }
    public void clearFoodDealList(){
        foodDealList.clear();
    }

    public void updateFoodDealRating(FoodDeal newfd){
        for (int i = 0 ; i < foodDealList.size(); i++){
            if(foodDealList.get(i).getId() != newfd.getId())
                continue;
            foodDealList.get(i).setRating(newfd.getRating());
        }
    }
    /**
     * FoodPlace
     */
    public FoodPlace getFoodPlace(int Index){
        return foodPlaceList.get(Index);
    }
    public FoodPlace getFoodPlaceWithID(int id){
        for (FoodPlace place: foodPlaceList) {
            if(place.getId() == id)
                return place;
        }
        return null;
    }
    public void findAndReplaceFoodPlace(FoodPlace fp){
        for(int i =0; i<foodPlaceList.size();i++){
            if(foodPlaceList.get(i).getId() == fp.getId()){
                foodPlaceList.set(i, fp);
            }
        }
    }
    public void updateFoodPlaceRating(FoodPlace newfp){
        for (int i = 0 ; i < foodPlaceList.size(); i++){
            if(foodPlaceList.get(i).getId() != newfp.getId())
                continue;
            foodPlaceList.get(i).setRating(newfp.getRating());
        }
    }
    public List<FoodPlace> getFoodPlaceList(){
        return foodPlaceList;
    }
    public void addFoodPlace(FoodPlace fp){
        foodPlaceList.add(fp);
    }
    public void addFoodPlaces(List<FoodPlace> fps){
        for (FoodPlace fp: fps) {
            addFoodPlace(fp);
        }
    }
    public void removeFoodPlacel(int Index){
        foodPlaceList.remove(Index);
    }
    public void clearFoodPlaceList(){
        foodPlaceList.clear();
    }


    /**
     * Event functions
     */
    public List<EventBean> getEvents() {return events;}

    public EventBean getEvent(int position) {return events.get(position);}

    public void addEvent(EventBean event) {events.add(event);}

    public void addEvents(List<EventBean> events) {events.addAll(events);}

    public void removeEvent(int position) {events.remove(position);}

    public void clearEvents() {events.clear();}

    public void saveEventResponse(boolean success) {eventResponse = success;}

    public boolean checkEventResponse(boolean success) {return eventResponse = success;}
}


/**
 //User List
 public WeakReference<User> getUser(int Index){
 return userList.get(Index);
 }
 public List<WeakReference<User>> getUserListRef(){
 return userList;
 }
 public List<User> getUserList(){
 List<User> temp = new Vector<>();
 for(WeakReference<User> ref : userList){
 temp.add(ref.get());
 }
 return temp;
 }
 public void addUser(User user){
 userList.add(new WeakReference<>(user));
 }
 public void addUsers(List<User> users){
 for (User user : users) {
 userList.add(new WeakReference<>(user));
 }
 }
 public void removeUser(int Index){
 userList.remove(Index);
 }
 public void clearUserList(){
 userList.clear();
 }
 */