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
}