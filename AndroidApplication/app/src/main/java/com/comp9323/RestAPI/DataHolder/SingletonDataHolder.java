package com.comp9323.RestAPI.DataHolder;

import android.util.Log;

import com.comp9323.RestAPI.Beans.User;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
 * Created by thomas on 11/9/2017.
 */

public class SingletonDataHolder {

    private static final SingletonDataHolder DH = new SingletonDataHolder();

    private User userSelf;
    //Map<String, WeakReference<Object>> or Vector<WeakReference<Object>> objectData; //using "weakreference" allow destroy data when quit activities
    //vector<Object> ObjectData;
    //remember to initialize object list/map

    private SingletonDataHolder(){
        //...
    }

    public static SingletonDataHolder getInstance(){
        return DH;
    }

    public User getUserSelf(){
        return this.userSelf;
    }

    public void setUserSelf(User user){
        Log.v("tag", "written to DH");
        this.userSelf = user;
    }
}
