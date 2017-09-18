package com.comp9323.RestAPI.DataHolder;

import android.content.Context;
import android.util.Log;

import com.comp9323.RestAPI.Beans.FoodDeal;
import com.comp9323.RestAPI.Beans.User;

import java.lang.ref.WeakReference;
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
    private List<WeakReference<FoodDeal>> foodDealList;

    private SingletonDataHolder() {
        userList = new Vector<>();
        foodDealList = new Vector<>();
        //...
    }

    public static SingletonDataHolder getInstance() {
        return DH;
    }

    public User getUserSelf() {
        return this.userSelf;
    }

    public void setUserSelf(User user) {
        Log.v("tag", "written to DH");
        this.userSelf = user;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public WeakReference<User> getUser(int Index){
        return userList.get(Index);
    }
    public List<WeakReference<User>> getUserList(){
        return userList;
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
    public WeakReference<FoodDeal> getFoodDeal(int Index){
        return foodDealList.get(Index);
    }
    public WeakReference<FoodDeal> getFoodDealwithID(int id){
        for (WeakReference<FoodDeal> ref: foodDealList) {
            if(ref.get().getId() == id)
                return ref;
        }
        return null;
    }
    public List<WeakReference<FoodDeal>> getFoodDealList(){
        return foodDealList;
    }
    public void addFoodDeal(FoodDeal fd){
        foodDealList.add(new WeakReference<>(fd));
    }
    public void addFoodDeals(List<FoodDeal> fds){
        for (FoodDeal fd: fds) {
            foodDealList.add(new WeakReference<>(fd));
        }
    }
    public void removeFoodDeall(int Index){
        foodDealList.remove(Index);
    }
    public void clearFoodList(){
        foodDealList.clear();
    }

}
