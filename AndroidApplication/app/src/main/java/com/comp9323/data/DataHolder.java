package com.comp9323.data;

import android.content.Context;
import android.util.Log;

import com.comp9323.data.beans.FoodDeal;
import com.comp9323.data.beans.FoodPlace;
import com.comp9323.data.beans.User;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Vector;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
 * Created by thomas on 11/9/2017.
 */

public class DataHolder {

    private static final DataHolder DH = new DataHolder();
    private User userSelf;
    private Context context;
    //Map<String, WeakReference<Object>> or Vector<WeakReference<Object>> objectData; //using "weakreference" allow destroy data when quit activities
    //vector<Object> ObjectData;
    //remember to initialize object list/map
    private List<WeakReference<User>> userList;
    private List<FoodDeal> foodDealList;
    //    private Map<Integer, FoodDeal> foodDealMap;
    private final Vector<FoodPlace> foodPlaceList;
//    private final HashMap<Integer, FoodPlace> foodPlaceMap;

    private DataHolder() {
        userList = new Vector<>();
        foodDealList = new Vector<>();
        //   foodDealMap = new HashMap<>();
        foodPlaceList = new Vector<>();
//        foodPlaceMap = new HashMap<>();
        //...
    }

    public static DataHolder getInstance() {
        return DH;
    }

    //self
    public User getUserSelf() {
        return this.userSelf;
    }

    public void setUser(User user) {
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
    public WeakReference<User> getUser(int Index) {
        return userList.get(Index);
    }

    public List<WeakReference<User>> getUserListRef() {
        return userList;
    }

    public List<User> getUserList() {
        List<User> temp = new Vector<>();
        for (WeakReference<User> ref : userList) {
            temp.add(ref.get());
        }
        return temp;
    }

    public void addUser(User user) {
        userList.add(new WeakReference<>(user));
    }

    public void addUsers(List<User> users) {
        for (User user : users) {
            userList.add(new WeakReference<>(user));
        }
    }

    public void removeUser(int Index) {
        userList.remove(Index);
    }

    public void clearUserList() {
        userList.clear();
    }


    //FOOD DEALS
    public FoodDeal getFoodDeal(int Index) {
        return foodDealList.get(Index);
    }

    public FoodDeal getFoodDealwithID(int id) {
        for (FoodDeal ref : foodDealList) {
            if (ref.getId() == id)
                return ref;
        }
        return null;
    }

    public List<FoodDeal> getFoodDealList() {
        return foodDealList;
    }

    public void addFoodDeal(FoodDeal fd) {
        foodDealList.add(fd);
//        foodDealMap.put(fd.getId(),fd);
    }

    public void findAndReplaceFoodDeal(FoodDeal fd) {
        for (int i = 0; i < foodDealList.size(); i++) {
            if (foodDealList.get(i).getId() == fd.getId()) {
                foodDealList.set(i, fd);
//                foodDealMap.remove(i);
//                foodDealMap.put(i,fd);
            }
        }
    }

    public void addFoodDeals(List<FoodDeal> fds) {
        for (FoodDeal fd : fds) {
            addFoodDeal(fd);
        }
    }

    public void removeFoodDeal(int Index) {
        FoodDeal temp = foodDealList.remove(Index);
//        foodDealMap.remove(Index);
    }

    public void clearFoodDealList() {
        foodDealList.clear();
//        foodDealMap.clear();
    }

    public void updateFoodDealRating(FoodDeal newfd) {
        for (int i = 0; i < foodDealList.size(); i++) {
            if (foodDealList.get(i).getId() != newfd.getId())
                continue;
            foodDealList.get(i).setRating(newfd.getRating());
//            foodDealMap.get(i).setRating(newfd.getRating());
        }
    }

    //FoodPlace
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
//                foodPlaceMap.remove(i);
//                foodPlaceMap.put(i,fp);
            }
        }
    }

    public void updateFoodPlaceRating(FoodPlace newfp) {
        for (int i = 0; i < foodPlaceList.size(); i++) {
            if (foodPlaceList.get(i).getId() != newfp.getId())
                continue;
            foodPlaceList.get(i).setRating(newfp.getRating());
//            foodPlaceMap.get(i).setRating(newfp.getRating());
        }
    }

    public List<FoodPlace> getFoodPlaceList() {
        return foodPlaceList;
    }

    public void addFoodPlace(FoodPlace fp) {
        foodPlaceList.add(fp);
//        foodPlaceMap.put(fp.getId(),fp);
    }

    public void addFoodPlaces(List<FoodPlace> fps) {
        for (FoodPlace fp : fps) {
            addFoodPlace(fp);
        }
    }

    public void removeFoodPlacel(int Index) {
        FoodPlace temp = foodPlaceList.remove(Index);
//        foodPlaceMap.remove(Index);
    }

    public void clearFoodPlaceList() {
        foodPlaceList.clear();
//        foodPlaceMap.clear();
    }
}
