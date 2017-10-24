package com.comp9323.data;

import com.comp9323.data.beans.User;

/**
 * NOTE: Everything store in this class  WILL BE GONE when the application is kill
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