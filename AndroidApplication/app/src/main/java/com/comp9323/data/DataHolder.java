package com.comp9323.data;

import com.comp9323.data.beans.User;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
 * A Global variable that store current user detail which be accessed by all parts(ie. fragment,activity) of the application
 * Created by thomas on 11/9/2017.
 */

public class DataHolder {

    private static final DataHolder DH = new DataHolder();

    private User user;

    private DataHolder() {
    }

    public static DataHolder getInstance() {
        return DH;
    }

    /**
     * User
     */
    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}