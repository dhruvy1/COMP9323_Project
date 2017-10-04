package com.comp9323.RestAPI.DataHolder;

import android.content.Context;
import android.util.Log;

import com.comp9323.RestAPI.Beans.EventBean;
import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.Beans.User;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
    private List< WeakReference<User>> userList;
    private List<FoodDeal> foodDealList;
    private Map<Integer, FoodDeal> foodDealMap;
    private List<EventBean> events;
    private boolean eventResponse;

    private SingletonDataHolder() {
        userList = new Vector<>();
        foodDealList = new Vector<>();
        foodDealMap = new HashMap<>();
        events = new Vector<>();
        eventResponse = false;
        //...
    }

    public static SingletonDataHolder getInstance() {
        return DH;
    }

    //self
    public User getUserSelf() {
        return this.userSelf;
    }
    public void setUserSelf(User user) {
        Log.v("tag", "written to DH");
        this.userSelf = user;
    }

    //base comtext
    public Context getContext() {
        return context;
    }
    public void setContext(Context context) {
        this.context = context;
    }

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


    //FOOD DEALS
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
        foodDealMap.put(fd.getId(),fd);
    }
    public void addFoodDeals(List<FoodDeal> fds){
        for (FoodDeal fd: fds) {
            foodDealList.add(fd);
            foodDealMap.put(fd.getId(),fd);
        }
    }
    public void removeFoodDeall(int Index){
        FoodDeal temp = foodDealList.remove(Index);
        foodDealMap.remove(temp);
    }
    public void clearFoodList(){
        foodDealList.clear();
        foodDealMap.clear();
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
